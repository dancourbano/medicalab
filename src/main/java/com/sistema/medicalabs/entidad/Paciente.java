package com.sistema.medicalabs.entidad;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "paciente")
@Getter @Setter @NoArgsConstructor
public class Paciente implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer pacienteid;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "apellidos")
	private String apellidos;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "sexo")
	private String sexo;
	
	@Column(name = "grupo_sanguineo")
	private String grupoSanguineo;
	
	@Column(name = "fecha_nacimiento")
	private String fechaNacimiento;
	
	@Column(name = "telefono1")
	private String telefono1;
	
	@Column(name = "telefono2")
	private String telefono2;
	
	@Column(name = "foto")
	private String foto;

	@Column(name = "dni")
	private String dni;
	
	@CreationTimestamp
	@Column(name = "fecha_creacion",updatable=false)
	private LocalDateTime  fechaCreacion;
	
	@UpdateTimestamp
	@Column(name = "fecha_edicion")
	private LocalDateTime  fechaEdicion;
	
	@Column(name = "ciudad")
	private String ciudad;
	
	@Column(name = "estado")
	private String estado;
	 
}
