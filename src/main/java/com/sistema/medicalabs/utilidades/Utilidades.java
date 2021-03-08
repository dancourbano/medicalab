package com.sistema.medicalabs.utilidades;

public class Utilidades {
	public static boolean isNullOrEmpty(Object obj) {
		return obj == null;
	}
	public static boolean isNullOrEmpty(String obj) {
		return obj == null || obj.trim().length() == 0;
	}
}
