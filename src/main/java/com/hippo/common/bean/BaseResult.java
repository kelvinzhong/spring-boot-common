package com.hippo.common.bean;

import java.io.Serializable;

public class BaseResult implements Serializable {

	private static final long serialVersionUID = -4662126757299374128L;

	private String token;

	private String resultMsg;

	private Integer resultCode;

	public BaseResult() {

	}

	public BaseResult(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public BaseResult(Integer resultCode, String resultMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	public BaseResult(ErrorCode errorCode, String resultMsg) {
		this.resultCode = errorCode.code;
		this.resultMsg = resultMsg;
	}

	public BaseResult(ErrorCode errorCode) {
		this.resultCode = errorCode.code;
		this.resultMsg = errorCode.msg;
	}

	public static BaseResult buildBadRequest() {
		return new BaseResult(ErrorCode.BAD_REQUEST);
	}

	public static BaseResult buildBadRequest(String resultMsg) {
		return new BaseResult(ErrorCode.BAD_REQUEST, resultMsg);
	}

	public static BaseResult unAuthorizeRequset() {
		return new BaseResult(ErrorCode.UNAUTHORIZED);
	}

	public static BaseResult unAuthorizeRequset(String resultMsg) {
		return new BaseResult(ErrorCode.UNAUTHORIZED, resultMsg);
	}

	public static BaseResult conflictRequest() {
		return new BaseResult(ErrorCode.CONFLICT);
	}

	public static BaseResult conflictRequest(String resultDesc) {
		return new BaseResult(ErrorCode.CONFLICT, resultDesc);
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public BaseResult setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
		return this;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

}
