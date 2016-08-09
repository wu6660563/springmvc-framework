package com.geeku.common.web;

/**
 * 错误信息标识对象,对返回给页面的异常信息类型进行统一标识
 * 
 * @Author ji.jiang
 */
public final class MsgCode {
	//INPUT表示校验失败，ERROR表示执行过程中出现异常或错误，SUCCESS表示整个过程成功，ABNORMAL表示不正常
	public static final int INPUT = 406, ERROR = 503,ABNORMAL = 400, SUCCESS = 200;
	
}
