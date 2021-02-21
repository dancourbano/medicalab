package com.sistema.medicalabs.entidad;

 

public class AjaxResponseBody {

	private int code;
	private String message;
	private String messageType;
	public AjaxResponseBody() {
		code = 0;
		message = "";
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
		this.messageType = AppMsgType.SUCCESSFUL.getValue();
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(AppMsgType messageType) {
		this.messageType = messageType.getValue();
	}
	public void setErrorMessage(String message){
		this.message = message;
		this.messageType = AppMsgType.ERROR.getValue();;
	}
	public void setWarningMessage(String message){
		this.message = message;
		this.messageType = AppMsgType.WARNING.getValue();;
	}

}
