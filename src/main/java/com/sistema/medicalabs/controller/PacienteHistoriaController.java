package com.sistema.medicalabs.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sistema.medicalabs.AppProperties.MedicalAppUrl;
import com.sistema.medicalabs.AppProperties.MedicalAppView;
import com.sistema.medicalabs.entidad.InformeLaboratorio;
import com.sistema.medicalabs.service.InformeLaboratorioService;
import com.sistema.medicalabs.utilidades.Utilidades;
@Secured({"ROLE_ADMIN", "ROLE_PACIENTE"})
@Controller
@RequestMapping("/paciente/historia")
public class PacienteHistoriaController {
	
	@Autowired
	private InformeLaboratorioService informeLaboService;
	
	@Autowired
    private HttpSession session;
	
	@RequestMapping("/{id}")
	public String main(Model model, @PathVariable Integer id) {
		if(hasRole("ROLE_PACIENTE")) {
			String pacienteid=(String)session.getAttribute("userSession");
			if(!Utilidades.isNullOrEmpty(pacienteid) && id!=Integer.valueOf(pacienteid)) {
				return "/error_403";
			}
			
		}
		model.addAttribute("pacienteid", id);
		return MedicalAppView.PACIENTE_HISTORIA;
	}
	
	@GetMapping(value = MedicalAppUrl.LIST_ALL_HISTORIA_BY_PACIENTE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<InformeLaboratorio>> getAllByPaciente(@PathVariable String pacienteid) {
		List<InformeLaboratorio> modeloInformeLabs = new ArrayList<>();
		modeloInformeLabs = informeLaboService.findAllByPaciente(pacienteid);
		return new ResponseEntity<List<InformeLaboratorio>>(modeloInformeLabs, HttpStatus.OK);
	}
	
	private boolean hasRole(String role) {
		SecurityContext context =SecurityContextHolder.getContext();
		if(context==null) {
			return false;
		}
		Authentication auth=context.getAuthentication();
		if(auth==null) {
			return false;
		}
		Collection<? extends GrantedAuthority> authorities =auth.getAuthorities();
		return authorities.contains(new SimpleGrantedAuthority(role));
	}
}
