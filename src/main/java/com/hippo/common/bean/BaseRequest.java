package com.hippo.common.bean;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class BaseRequest implements Serializable {

	private static final long serialVersionUID = -6938146170053667265L;

	public enum SignKey {
		timestamp, loginUserId, imei, clientType, deviceInfo, clientVersion, deviceType, netType
	}

	private long timestamp;

	@NotNull(message = "loginUserId.null")
	private String loginUserId;

	@NotNull(message = "sign.null")
	private String sign;

	private String imei; // 唯一设备编码

	private int clientType; // 客户端类型

	private String deviceInfo; // 版本号-分辨率 1.0.0-1280X900

	@NotNull(message = "clientVersion.null")
	private String clientVersion;

	private String deviceType; // 手机型号 X9077/iPhone7,1/Redmi Note

	private String netType; // 3G, WIFI, UNKNOWN

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		if (this.timestamp != 0)
			throw new UnsupportedOperationException("timestamp cannot be change");
		this.timestamp = timestamp;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		if (this.loginUserId != null)
			throw new UnsupportedOperationException("loginUserId cannot be change");
		this.loginUserId = loginUserId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		if (this.sign != null)
			throw new UnsupportedOperationException("sign cannot be change");
		this.sign = sign;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		if (this.imei != null)
			throw new UnsupportedOperationException("imei cannot be change");
		this.imei = imei;
	}

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		if (this.clientType != 0)
			throw new UnsupportedOperationException("clientType cannot be change");
		this.clientType = clientType;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		if (this.clientVersion != null)
			throw new UnsupportedOperationException("clientVersion cannot be change");
		this.clientVersion = clientVersion;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		if (this.deviceInfo != null)
			throw new UnsupportedOperationException("deviceInfo cannot be change");
		this.deviceInfo = deviceInfo;
	}

}
