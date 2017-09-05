package com.hippo.common.util.general;

public class Constants {

	// 该参数等配置中心设置好则转移到配置中心
	public static final int PAGE_SIZE = 15;

	public static final String NOT_LOGGED_IN = "-1";

	public static final int STATUS_DELETE = -1;

	public static final int STATUS_NORMAL = 0;
	
	public static final int STATUS_DONE = 1;
	
	public static final int STATUS_INVALID = -1;

	public static final String SYSTEM_ERROR_SYMBOL = "01";

	/** UTF-8字符集 **/
	public static final String CHARSET_UTF8 = "UTF-8";

	/** 用于校验是否为暴力请求 **/
	public static final String USER_REQUEST_LIMIT = "user:request:limit:";

	/** 用于校验是否为重发请求 **/
	public static final String USER_REQUEST_CATEGORY = ":user:request:category";

	/** 请求头字段用于token验证 **/
	public static final String HEADER_AUTHORIZATION = "Authorization";

	/** 返回值基础字段名 **/
	public static final String RESULT_CODE = "resultCode";
	public static final String RESULT_MSG = "resultMsg";

	/** 日志跟踪track id **/
	public static final String NO_TRACKING_ID = "NoTrackingId";
	public static final String TRACKING_ID = "TrackingId";

	public static final String LIST_SIZE = "ListSize";
}
