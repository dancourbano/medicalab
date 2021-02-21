package com.sistema.medicalabs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sistema.medicalabs.entidad.Paciente;

@Repository
public interface PacienteRepository extends CrudRepository<Paciente, Integer> {
	List<Paciente> findByEstadoIs(String name);
}
