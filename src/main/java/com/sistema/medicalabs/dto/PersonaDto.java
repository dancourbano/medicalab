package com.sistema.medicalabs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonaDto {
	private String CODDNI;
	private String Data;
	private String razon_social;
	private String ruc;
	private String nombre_comercial;
	private String domicilio_fiscal;
	private String dni;
	@JsonProperty
	private String nombreComercial;
	@JsonProperty	
	private String nombres;
	@JsonProperty
	private String apellidoPaterno;
	@JsonProperty
	private String apellidoMaterno;
	public String getCODDNI() {
		return CODDNI;
	}
	public void setCODDNI(String cODDNI) {
		CODDNI = cODDNI;
	}
	
	 
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		Data = data;
	}
	public String getRazon_social() {
		return razon_social;
	}
	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getNombre_comercial() {
		return nombre_comercial;
	}
	public void setNombre_comercial(String nombre_comercial) {
		this.nombre_comercial = nombre_comercial;
	}
	public String getDomicilio_fiscal() {
		return domicilio_fiscal;
	}
	public void setDomicilio_fiscal(String domicilio_fiscal) {
		this.domicilio_fiscal = domicilio_fiscal;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getNombreComercial() {
		return nombreComercial;
	}
	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
}
