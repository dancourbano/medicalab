package com.sistema.medicalabs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sistema.medicalabs.AppProperties.MedicalAppUrl;
import com.sistema.medicalabs.AppProperties.MedicalAppView;
import com.sistema.medicalabs.entidad.InformeLaboratorio;
import com.sistema.medicalabs.service.InformeLaboratorioService;

@Controller
@RequestMapping("/paciente/historia")
public class PacienteHistoriaController {
	
	@Autowired
	private InformeLaboratorioService informeLaboService;

	@RequestMapping("/{id}")
	public String main(Model model, @PathVariable Integer id) {
		model.addAttribute("pacienteid", id); 
		return MedicalAppView.PACIENTE_HISTORIA;
	}
	@GetMapping(value = MedicalAppUrl.LIST_ALL_HISTORIA_BY_PACIENTE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<InformeLaboratorio>> getAllByPaciente(@PathVariable String pacienteid) {
		List<InformeLaboratorio> modeloInformeLabs = new ArrayList<>();
		modeloInformeLabs = informeLaboService.findAllByPaciente(pacienteid);
		return new ResponseEntity<List<InformeLaboratorio>>(modeloInformeLabs, HttpStatus.OK);
	}
}
