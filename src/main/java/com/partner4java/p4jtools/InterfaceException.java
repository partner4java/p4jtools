package com.partner4java.p4jtools;

/**
 * 服务接口异常
 *
 */
public class InterfaceException extends Exception {

	private static final long serialVersionUID = 1L;

	/** 错误代码 */
	private String errCode;

	/** 错误提示 */
	private String errMsg;

	public InterfaceException() {
		super();
	}

	public InterfaceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InterfaceException(String message) {
		super(message);
		this.errMsg = message;
	}

	public InterfaceException(Throwable cause) {
		super(cause);
	}

	public InterfaceException(String errCode, String errMsg) {
		super(errCode + ":" + errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return this.errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

}