package com.sistema.medicalabs.AppProperties;

public class MedicalAppUrl {
	// paciente
	public static final String PACIENTE_ROOT = "/paciente";
	public static final String PACIENTEE_VIEW_ADD = "/addPaciente";
	public static final String PACIENTE_SAVE = "/save";
	public static final String DELETE_PACIENTE = "/delete/{id}";
	public static final String GET_PACIENTE = "/getById/{id}";
	public static final String LIST_ALL_PACIENTE = "/listAll";
	public static final String LIST_ALL_PACIENTE_ACTIVOS = "/listAllActivos";
	//modelo informe lab
	public static final String MODELO_INFORME_LAB_ROOT = "/modeloInformeLab";
	public static final String MODELO_INFORME_LAB_VIEW_ADD = "/addModelo";
	public static final String MODELO_INFORME_LAB_SAVE = "/save";
	public static final String MODELO_INFORME_LAB_DELETE = "/delete/{id}";
	public static final String GET_MODELO_INFORME_LAB = "/getById/{id}";
	public static final String LIST_ALL_MODELO_INFORME_LAB = "/listAll";
	
	//informe lab
	public static final String INFORME_LAB_ROOT = "/informeLab";
	public static final String INFORME_LAB_VIEW_ADD = "/addModelo";
	public static final String INFORME_LAB_SAVE = "/save";
	public static final String INFORME_LAB_DELETE = "/delete/{id}";
	public static final String GET_INFORME_LAB = "/getById/{id}";
	public static final String LIST_ALL_INFORME_LAB = "/listAll";
	public static final String LIST_ALL_ACTIVOS_INFORME_LAB = "/listAllActivos";
	
	//historial paciente
	public static final String LIST_ALL_HISTORIA_BY_PACIENTE = "/listAllByPaciente/{pacienteid}";
	public static final String INFORME_DOWNLOAD_PDF = "/descargaInforme/{id}";
	
	// doctor
		public static final String DOCTOR_ROOT = "/paciente";
		public static final String DOCTORE_VIEW_ADD = "/addPaciente";
		public static final String DOCTOR_SAVE = "/save";
		public static final String GET_DOCTOR = "/getById/{id}";
		public static final String LIST_ALL_DOCTOR = "/listAll";
		public static final String LIST_ALL_DOCTOR_ACTIVOS="/listAllActivos";
		public static final String DELETE_DOCTOR = "/delete/{id}";
}
