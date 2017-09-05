package com.hippo.common.bean;

public enum Client {
	IOS(1,1), ANDROID(2,1), WAP(3,2), PC(4,3);

	public final int type;
	public final int deviceCategory;	//用于区别单点登录

	private Client(int type, int deviceCategory) {
		this.type = type;
		this.deviceCategory = deviceCategory;
	}

	public static Client getClient(int type) {
		for (Client c : Client.values()) {
			if (c.type == type)
				return c;
		}
		return null;
	}
	
	public static String getDeviceCategory(int type){
		for (Client c : Client.values()) {
			if (c.type == type)
				return Integer.toString(c.deviceCategory);
		}
		return null;
	}
}