package com.sistema.medicalabs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sistema.medicalabs.entidad.ModeloInformeLaboratorio;

@Repository
public interface ModeloInformeLabRepository extends CrudRepository<ModeloInformeLaboratorio, Integer> {

}
