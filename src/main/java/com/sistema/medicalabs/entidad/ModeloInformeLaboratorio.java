package com.sistema.medicalabs.entidad;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "modelo_informelab")
public class ModeloInformeLaboratorio implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "modeloid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer modeloid;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Getter(value=AccessLevel.NONE)
	@Setter(value=AccessLevel.NONE)
	@Lob 
	@Column(name = "detalle_modelo",length=100000)
	private byte[]  detalleModelo;
	
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;
	
	@Column(name = "fecha_edicion")
	private Date fechaEdicion;
	
	 
	public String getDetalleModelo() {
			if(detalleModelo!=null) {
				return new String(detalleModelo);
			}
		 
		return null;
	}

	public void setDetalleModelo(String detalleString) {
		try {
			detalleModelo = detalleString.getBytes("UTF-8");
			 
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
