package com.hippo.common.config.properties;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ConfZkClient implements Watcher {
	private static Logger logger = LoggerFactory.getLogger(ConfZkClient.class);

	public static final String CONF_DATA_PATH = "/xxl-conf";
	
	// ------------------------------ zookeeper client
	// ------------------------------
	private static ZooKeeper zooKeeper;
	private static ReentrantLock INSTANCE_INIT_LOCK = new ReentrantLock(true);

	private static ZooKeeper getInstance() {
		if (zooKeeper == null) {
			try {
				if (INSTANCE_INIT_LOCK.tryLock(2, TimeUnit.SECONDS)) {
					try {
						zooKeeper = new ZooKeeper(Configuration.getProperty("configuration.zk.address"), 20000, new Watcher() {
							@Override
							public void process(WatchedEvent watchedEvent) {
								try {
									logger.info(">>>>>>>>>> xxl-conf: watcher:{}", watchedEvent);

									// session expire, close old and create new
									if (watchedEvent.getState() == Event.KeeperState.Expired) {
										zooKeeper.close();
										zooKeeper = null;
										getInstance();
									}

									String path = watchedEvent.getPath();
									String key = pathToKey(path);
									if (key != null) {
										// add One-time trigger
										zooKeeper.exists(path, true);
										if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
											Configuration.remove(key);
										} else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
											String data = getPathDataByKey(key);
											Configuration.update(key, data);
										}
									}
								} catch (KeeperException e) {
									e.printStackTrace();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						});
						ConfZkClient.createWithParent(CONF_DATA_PATH); // init
																						// cfg
																						// root
																						// path
					} finally {
						INSTANCE_INIT_LOCK.unlock();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (zooKeeper == null) {
			throw new NullPointerException(">>>>>>>>>>> xxl-cache, XxlConfZkClient.zooKeeper is null.");
		}
		return zooKeeper;
	}

	/**
	 * 监控所有被触发的事件(One-time trigger)
	 */
	@Override
	public void process(WatchedEvent event) {

	}

	// ------------------------------ util ------------------------------
	/**
	 * path 2 key
	 * 
	 * @param nodePath
	 * @return ZnodeKey
	 */
	private static String pathToKey(String nodePath) {
		if (nodePath == null || nodePath.length() <= CONF_DATA_PATH.length() || !nodePath.startsWith(CONF_DATA_PATH)) {
			return null;
		}
		return nodePath.substring(CONF_DATA_PATH.length() + 1, nodePath.length());
	}

	/**
	 * key 2 path
	 * 
	 * @param nodeKey
	 * @return znodePath
	 */
	private static String keyToPath(String nodeKey) {
		return CONF_DATA_PATH + "/" + nodeKey;
	}

	public static String generateGroupKey(String nodeGroup, String nodeKey) {
		return nodeGroup + "." + nodeKey;
	}

	/**
	 * create node path with parent path (如果父节点不存在,循环创建父节点,
	 * 因为父节点不存在zookeeper会抛异常)
	 * 
	 * @param path
	 *            ()
	 */
	private static Stat createWithParent(String path) {
		// valid
		if (path == null || path.trim().length() == 0) {
			return null;
		}

		try {
			Stat stat = getInstance().exists(path, true);
			if (stat == null) {
				// valid parent, createWithParent if not exists
				if (path.lastIndexOf("/") > 0) {
					String parentPath = path.substring(0, path.lastIndexOf("/"));
					Stat parentStat = getInstance().exists(parentPath, true);
					if (parentStat == null) {
						createWithParent(parentPath);
					}
				}
				// create desc node path
				zooKeeper.create(path, new byte[] {}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			return getInstance().exists(path, true);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * delete path by key
	 * 
	 * @param key
	 */
	public static void deletePathByKey(String key) {
		String path = keyToPath(key);
		try {
			Stat stat = getInstance().exists(path, true);
			if (stat != null) {
				getInstance().delete(path, stat.getVersion());
			} else {
				logger.info(">>>>>>>>>> zookeeper node path not found :{}", key);
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * set data to node
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public static Stat setPathDataByKey(String key, String data) {
		String path = keyToPath(key);
		try {
			Stat stat = getInstance().exists(path, true);
			if (stat == null) {
				createWithParent(path);
				stat = getInstance().exists(path, true);
			}
			return zooKeeper.setData(path, data.getBytes(), stat.getVersion());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get data from node
	 * 
	 * @param key
	 * @return
	 */
	public static String getPathDataByKey(String key) {
		String path = keyToPath(key);
		try {
			Stat stat = getInstance().exists(path, true);
			if (stat != null) {
				String znodeValue = null;
				byte[] resultData = getInstance().getData(path, true, null);
				if (resultData != null) {
					znodeValue = new String(resultData);
				}
				return znodeValue;
			} else {
				logger.info(">>>>>>>>>> znodeKey[{}] not found.", key);
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取配置目录下所有配置
	 * 
	 * @return
	 */
	private static Map<String, String> getAllData() {
		Map<String, String> allData = new HashMap<String, String>();
		try {
			List<String> childKeys = getInstance().getChildren(CONF_DATA_PATH, true);
			if (childKeys != null && childKeys.size() > 0) {
				for (String key : childKeys) {
					String data = getPathDataByKey(key);
					allData.put(key, data);
				}
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return allData;
	}

	public static void main(String[] args) throws InterruptedException, KeeperException {
		setPathDataByKey("key02", "666");
		System.out.println(getPathDataByKey("key02"));

		System.out.println(getAllData());
		getInstance().delete(CONF_DATA_PATH + "/key02", -1);
		getInstance().delete(CONF_DATA_PATH, -1);

	}

}