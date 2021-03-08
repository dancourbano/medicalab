package com.sistema.medicalabs.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sistema.medicalabs.entidad.Users;
import com.sistema.medicalabs.utilidades.Utilidades;

@ControllerAdvice("com.sistema.medicalabs.controller")
public class AdviceController {
	 
	@Autowired
    private HttpSession session;
	
	@ModelAttribute("sessionAttribute")
	    public String getIdPaciente() {
			String pacienteId=(String) session.getAttribute("userSession");
			if(!Utilidades.isNullOrEmpty(pacienteId)){
				return pacienteId;
			}
			return "";
	 }
}
