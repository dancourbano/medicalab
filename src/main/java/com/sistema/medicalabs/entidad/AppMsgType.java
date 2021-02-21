package com.sistema.medicalabs.entidad;

public enum AppMsgType {
	ERROR ("E"),
    INFORMATION ("I"),
    WARNING ("W"),
    SUCCESSFUL("S");
	private final String value;
    private AppMsgType(String value){
        this.value = value;
    }
    public String getValue(){
    	return value;
    }
}
