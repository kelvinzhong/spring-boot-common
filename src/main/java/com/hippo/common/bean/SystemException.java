package com.hippo.common.bean;

import com.hippo.common.util.general.Constants;
import com.hippo.common.util.general.Utils;

public class SystemException extends RuntimeException {

	/**
	 * @Comment
	 * 
	 * @Description
	 * 
	 * @Author ZHONG WEN BO
	 * @Date:2016年12月8日下午4:59:58
	 * @Copyright (c) 2016, kelvin.zhong@qq.com All Rights Reserved.
	 */
	private static final long serialVersionUID = -8386107034480551234L;

	public SystemException() {
		this(ErrorCode.BAD_REQUEST);
	}

	public SystemException(String msg) {
		this(ErrorCode.BAD_REQUEST, msg);
	}

	public SystemException(ErrorCode errorCode) {
		this(errorCode, errorCode.msg);
	}

	public SystemException(ErrorCode errorCode, String msg) {
		super("code:" + codeFormat(errorCode.code) + ";msg:" + msg + ";\n");
		this.code = codeFormat(errorCode.code);
		this.msg = msg;

	}

	private String msg;
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static String codeFormat(int code) {
		return Constants.SYSTEM_ERROR_SYMBOL + Utils.getResultCodeFormat(code);
	}
}
