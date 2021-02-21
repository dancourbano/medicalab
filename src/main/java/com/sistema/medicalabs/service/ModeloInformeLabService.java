package com.sistema.medicalabs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.medicalabs.entidad.ModeloInformeLaboratorio;
import com.sistema.medicalabs.repository.ModeloInformeLabRepository;

@Service
@Transactional(readOnly = true)
public class ModeloInformeLabService {
	@Autowired
	private ModeloInformeLabRepository modeloInformeLabRepository;
	
	public List<ModeloInformeLaboratorio> findAll() {
		List<ModeloInformeLaboratorio> modeloInformeLaboratorios=new ArrayList<ModeloInformeLaboratorio>();
		modeloInformeLabRepository.findAll().forEach(modeloInformeLaboratorios::add);
		return modeloInformeLaboratorios;
	}

	public Optional<ModeloInformeLaboratorio> findOne(Integer id) {
		return modeloInformeLabRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public ModeloInformeLaboratorio save(ModeloInformeLaboratorio entity) {
		entity.setDetalleModelo(entity.getDetalleModelo().replace("\"", "'"));
		return modeloInformeLabRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(ModeloInformeLaboratorio entity) {
		modeloInformeLabRepository.delete(entity);
	}
}
