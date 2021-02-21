package com.sistema.medicalabs.entidad;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "informe_laboratorio")
public class InformeLaboratorio implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer informeid;
	
	@Column(name = "fecha_reporte")
	private String fechaReporte;
	
	@Getter(value=AccessLevel.NONE)
	@Setter(value=AccessLevel.NONE)
	@Lob
	@Column(name = "detalle",length=100000)
	private byte[]  detalle;
	
	@UpdateTimestamp
	@Column(name = "fechaEdicion")
	private LocalDateTime fechaEdicion;
	
	@ManyToOne(optional = false, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "pacienteid")
    private Paciente paciente;
	
	@ManyToOne(optional = false, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "modeloid")
	private ModeloInformeLaboratorio modeloInformeLaboratorio;
	
	@ManyToOne(optional = true, cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "doctorid")
    private Doctor doctor;
	
	public String getDetalle() {
				return new String(detalle);
		 
	}

	public void setDetalle(String detalleString) {
		try {
			detalle = detalleString.getBytes("UTF-8");
			 
		} catch (  UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
