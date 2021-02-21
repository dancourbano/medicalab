package com.sistema.medicalabs.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class InformeLaboratorioDto {
	private Integer informeid;
	
	private String fechaReporte;
	
	private String detalle;
	
	private String fechaEdicion;
	
    private String pacienteid;
	
	private String modeloid;
	
	private String doctorid;
}
