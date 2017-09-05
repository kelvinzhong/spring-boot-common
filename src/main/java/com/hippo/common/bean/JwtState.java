package com.hippo.common.bean;

public enum JwtState {

	/**
	 * 冲突，其他设备登录了账号
	 */
	CONFLICT(4, "Other device has login account!"),
	/**
	 * 刷新token
	 */
	REFRESH(3, "Token vaild, but refresh token"),

	/**
	 * 过期
	 */
	EXPIRED(2, "Access expired, please login again!"),

	/**
	 * token为空
	 */
	EMPTY(1, "Token is empty, please login first!"),

	/**
	 * 无效(token不合法)
	 */
	INVALID(-1, "Invalid access"),

	/**
	 * 有效的
	 */
	VALID(0, "VALID");

	public final String msg;
	public final int code;

	private JwtState(int code, String msg) {
		this.msg = msg;
		this.code = code;
	}

	public static JwtState getState(String msg) {
		for (JwtState s : JwtState.values()) {
			if (s.msg.equals(msg))
				return s;
		}
		return null;
	}

	public static JwtState getState(int code) {
		for (JwtState s : JwtState.values()) {
			if (s.code == code)
				return s;
		}
		return null;
	}

}