package com.sistema.medicalabs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sistema.medicalabs.entidad.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer>{
	public Users findByUsername(String username);
}
