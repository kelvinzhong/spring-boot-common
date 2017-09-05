package com.hippo.common.bean;

import com.hippo.common.util.general.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author
 * @date:
 * @category @version :
 * 
 */ 
public class PageBean extends BaseResult {
	private static final Logger log = LoggerFactory.getLogger(PageBean.class);

	private static final int DEFAULT_PAGE_SIZE = 10;

	public long curruntPage;// 当前页数(>0) 状态
	private long totalRecord; // 总记录数(>=0) 读写
	private long totalPage; // 总页数(>0) 只读
	private int pageSize; // 每页的记录数(>0) 初始化

	public PageBean(int pageSize) {
		this.curruntPage = 1L;
		this.pageSize = pageSize;
	}

	public PageBean(int pageNum, int pageSize) {
		this.curruntPage = pageNum;
		this.pageSize = pageSize;
	}

	public PageBean() {
		this(DEFAULT_PAGE_SIZE);
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 * 
	 * @param pageNo
	 *            从1开始的页号
	 * @param pageSize
	 *            每页记录条数
	 * @return 该页第一条数据
	 */
	public long getOffSet() {
		return (this.curruntPage - 1) * pageSize < 0 ? 0 : (this.curruntPage - 1) * pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public long getCurruntPage() {
		return this.curruntPage <= 0 ? 1 : this.curruntPage;
	}

	public void setCurruntPage(Object curruntPage) {
		if (curruntPage instanceof String[]) {
			String[] pageStr = (String[]) curruntPage;
			if (StringUtils.isEmpty(pageStr[0])) {
				this.curruntPage = 1L;
				return;
			}
			try {
				this.curruntPage = Integer.parseInt(pageStr[0]);
				this.curruntPage = this.curruntPage <= 0 ? 1 : this.curruntPage;
			} catch (Exception e) {
				log.error(e.toString());
				this.curruntPage = 1L;
			}
		}
		if (curruntPage instanceof String) {
			String pageStr = (String) curruntPage;
			if (StringUtils.isEmpty(pageStr)) {
				this.curruntPage = 1L;
				return;
			}
			try {
				this.curruntPage = Integer.parseInt(pageStr);
				this.curruntPage = this.curruntPage <= 0 ? 1 : this.curruntPage;
			} catch (Exception e) {
				log.error(e.toString());
				this.curruntPage = 1L;
			}
		}

		if (curruntPage instanceof Integer) {
			if (curruntPage == null) {
				this.curruntPage = 1L;
				return;
			}
			try {
				this.curruntPage = (Integer) curruntPage;
				this.curruntPage = this.curruntPage <= 0 ? 1 : this.curruntPage;
			} catch (Exception e) {
				log.error(e.toString());
				this.curruntPage = 1;
			}
		}
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	/**
	 * return true : 可以继续查询 return false: 没有记录
	 * 
	 * @param totalRecord
	 * @return
	 */
	public boolean setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;

		if (this.totalRecord == 0) {
			this.totalPage = 0;
			return false;
		} else {
			this.totalPage = this.totalRecord / this.pageSize;
			if (this.totalRecord % this.pageSize > 0) {
				this.totalPage++;
			}
		}
		this.curruntPage = this.curruntPage > this.totalPage ? this.totalPage : this.curruntPage;
		return true;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
