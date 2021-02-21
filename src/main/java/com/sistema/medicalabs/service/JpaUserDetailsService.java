package com.sistema.medicalabs.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.medicalabs.entidad.Role;
import com.sistema.medicalabs.entidad.Users;
import com.sistema.medicalabs.repository.UserRepository;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	private Logger logger=LoggerFactory.getLogger(JpaUserDetailsService.class);
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user=userRepository.findByUsername(username);
		if(user==null) {
			logger.error("Error Login: No existe el usuario "+ username);
			throw new UsernameNotFoundException("username "+ username+" No existe");
		}
		List<GrantedAuthority> authorities=new ArrayList<>();
		for(Role role:user.getRoles() ){
			logger.info("Role: ".concat(role.getAuthority()));
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		if(authorities.isEmpty()) {
			logger.error("Error Login:No tiene roles asignados ");
			throw new UsernameNotFoundException("No tiene roles asignados ");
		}
		return new User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
	}
}
