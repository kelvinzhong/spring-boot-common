package com.hippo.common.bean;

public enum ErrorCode {

	/**
	 * 
	 * @Comment System Level Error Record
	 * 
	 * @Description Error code constituted by five digits. First two digits
	 *              represent for PROJCET code which start from 02. 01 stand for
	 *              System Level Error which will specify in this document. The
	 *              rest three digits start from 001 and represented as "code"
	 *              for each error. e.g. 01001 ,01002, 01003 etc...
	 * 
	 * @Author ZHONG WEN BO
	 * @Date:2016年12月7日下午3:38:02
	 * @Copyright (c) 2016, kelvin.zhong@qq.com All Rights Reserved.
	 */

	UNAUTHORIZED(1, "Request without authorization! Please login first or contact us for help!"),

	BAD_REQUEST(2, "Bad Request! Please check your paramters!"),

	CONFLICT(3, "Multiple requests received! Please check your server whehter has a network issue!"),

	FORBIDDEN(4,
			"This error would occurs when your request is out of the rate limits! Please try to slow down your access!"),

	INTERNAL_SERVER_ERROR(5, "");

	ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static ErrorCode getErrorCode(int code) {
		for (ErrorCode e : ErrorCode.values()) {
			if (e.code == code)
				return e;
		}
		return null;
	}

	public final int code;
	public final String msg;

}
