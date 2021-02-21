package com.sistema.medicalabs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.medicalabs.dto.PersonaDto;
import com.sistema.medicalabs.entidad.Paciente;
import com.sistema.medicalabs.repository.PacienteRepository;
import com.sistema.medicalabs.utilidades.Utilidades;

@Service
@Transactional(readOnly = true)
public class PacienteService {
	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	private PersonaService personaService;
	
	public List<Paciente> findAll() {
		List<Paciente> Pacientes=new ArrayList<Paciente>();
		pacienteRepository.findAll().forEach(Pacientes::add);
		return Pacientes;
	}

	public Optional<Paciente> findOne(Integer id) {
		return pacienteRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Paciente save(Paciente entity) {
		if(entity.getPacienteid()==0) {
			entity.setEstado("1");
		}
		return pacienteRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Paciente entity) {
		entity.setEstado("0");
		pacienteRepository.save(entity);
	}
	public List<Paciente> findAllActivos() {
		List<Paciente> pacientes=new ArrayList<Paciente>();
		pacienteRepository.findByEstadoIs("1").forEach(pacientes::add);
		return pacientes;
	}
	public Paciente getPersonaByDNI(Paciente paciente) {
		 
		PersonaDto personaDto =null;
		String identificador=paciente.getDni();
		if(identificador.length()==8) {
			personaDto=personaService.getClienteByDni(identificador);
			if(!Utilidades.isNullOrEmpty(personaDto)) {
				paciente.setApellidos(personaDto.getApellidoPaterno()+" "+personaDto.getApellidoMaterno());
				paciente.setNombre(personaDto.getNombres());
				paciente.setDni(personaDto.getDni());
			}
		}else if(identificador.length()==14) {
			personaDto=personaService.getClienteByRUC(identificador);
			if(!Utilidades.isNullOrEmpty(personaDto)) {
				paciente.setApellidos("");
				paciente.setNombre(personaDto.getNombreComercial());
				paciente.setDni(personaDto.getRuc());
			}
		} 
		return paciente;
	}
	
}
