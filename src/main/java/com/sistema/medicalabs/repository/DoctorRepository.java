package com.sistema.medicalabs.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sistema.medicalabs.entidad.Doctor;
import com.sistema.medicalabs.entidad.InformeLaboratorio;
import java.lang.Boolean;
import java.util.List;

@Repository
public interface DoctorRepository  extends CrudRepository<Doctor, Integer> {
	List<Doctor> findByEstado(Boolean estado);
	
}
