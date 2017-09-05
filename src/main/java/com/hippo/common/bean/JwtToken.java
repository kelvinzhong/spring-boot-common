package com.hippo.common.bean;

import com.hippo.common.config.properties.Configuration;

import java.util.Date;

public class JwtToken {

	public static final int EXPIRE_TIME = 1000 * 60 * 30; // 默认session有效期为30分钟
	public static final int REFRESH_TIME = 1000 * 60 * 20; // 剩余20分钟时刷新token
	
	public static final String BEARER = "Bearer ";

	public JwtToken(String userId, int clientType, String imei) {
		this.userId = userId;
		this.refreshTime = REFRESH_TIME;
		this.expirationTime = EXPIRE_TIME;
		this.expirationDate = new Date(new Date().getTime() + EXPIRE_TIME);
		this.refreshDate = new Date(this.expirationDate.getTime() - REFRESH_TIME);
		this.secret = getUserTokenSecret(userId, clientType);
		// clientType
		// +
		// "user:secret:XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
		// + userId;
		this.salt = "salt";
		this.clientType = clientType;
		this.subject = imei;
	}

	public static String getUserTokenSecret(String userId, int clientType) {
		return clientType + Configuration.getProperty("token.base.secret") + userId;
	}

	public JwtToken() {

	}

	private int clientType;
	private String id;
	private String userId;
	private String subject;
	private String salt;
	private String secret;
	private long expirationTime; // 有效时间
	private Date expirationDate; // 过期时间
	private long refreshTime; // 距离过期前刷新时间
	private Date refreshDate; // 刷新时间时长
	private int status;

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static int getExpireTime() {
		return EXPIRE_TIME;
	}

	public long getExpirationTime() {
		return expirationTime;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getRefreshDate() {
		return refreshDate;
	}

	public void setRefreshDate(Date refreshDate) {
		this.refreshDate = refreshDate;
	}

	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(long refreshTime) {
		this.refreshTime = refreshTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

}
