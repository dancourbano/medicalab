package com.sistema.medicalabs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sistema.medicalabs.entidad.InformeLaboratorio;

@Repository
public interface InformeLaboratorioRepository extends CrudRepository<InformeLaboratorio, Integer> {
	 
	public List<InformeLaboratorio> findByPacienteEstado(String estado);
	
	public List<InformeLaboratorio> findByPacientePacienteid(Integer pacienteid);
}
