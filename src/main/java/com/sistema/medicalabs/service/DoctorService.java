package com.sistema.medicalabs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.medicalabs.entidad.Doctor;
import com.sistema.medicalabs.repository.DoctorRepository;

@Service
@Transactional(readOnly = true)
public class DoctorService {
	@Autowired
	private DoctorRepository doctorRepository;
	
	public List<Doctor> findAll() {
		List<Doctor> doctor=new ArrayList<Doctor>();
		doctorRepository.findAll().forEach(doctor::add);
		return doctor;
	}
	public List<Doctor> findAllActivos() {
		List<Doctor> doctor=new ArrayList<Doctor>();
		doctorRepository.findByEstado(true).forEach(doctor::add);
		return doctor;
	}
	public Optional<Doctor> findOne(Integer id) {
		return doctorRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Doctor save(Doctor entity) {	
		if(entity.getDoctorid()==0) {
			entity.setEstado(true);
		}
		return doctorRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Doctor entity) {
		entity.setEstado(false); 
		doctorRepository.save(entity);
	}
	 
}
