package com.sistema.medicalabs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sistema.medicalabs.AppProperties.MedicalAppUrl;
import com.sistema.medicalabs.AppProperties.MedicalAppView;
import com.sistema.medicalabs.dto.InformeLaboratorioDto;
import com.sistema.medicalabs.entidad.AjaxResponseBody;
import com.sistema.medicalabs.entidad.InformeLaboratorio;
import com.sistema.medicalabs.exception.ModeloNotFoundException;
import com.sistema.medicalabs.service.InformeLaboratorioService;
@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/informeLab")
public class InformeLabController {
	@Autowired
	private InformeLaboratorioService informeLaboService;

	@RequestMapping("/{id}")
	public String main(Model model, @PathVariable Integer id) {
		model.addAttribute("informeid", id); 
		return MedicalAppView.INFORME_LAB;
	}
	
	@RequestMapping("/")
	public String main() {
		return MedicalAppView.INFORME_LAB;
	}
	
	@GetMapping(value = MedicalAppUrl.GET_INFORME_LAB, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getById(@PathVariable Integer id) {
		Optional<InformeLaboratorio> optional = informeLaboService.findOne(id);
		if(optional.isPresent()) {
			return new ResponseEntity<Object>(optional.get(), HttpStatus.OK);
		}else {
			AjaxResponseBody resultAjax = new AjaxResponseBody();
			resultAjax.setMessage("No se encontró resultados");
			return new ResponseEntity<Object>(resultAjax, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = MedicalAppUrl.LIST_ALL_INFORME_LAB, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<InformeLaboratorio>> getAll() {
		List<InformeLaboratorio> modeloInformeLabs = new ArrayList<>();
		modeloInformeLabs = informeLaboService.findAll();
		return new ResponseEntity<List<InformeLaboratorio>>(modeloInformeLabs, HttpStatus.OK);
	}
	
	@GetMapping(value = MedicalAppUrl.LIST_ALL_ACTIVOS_INFORME_LAB, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<InformeLaboratorio>> getAllActivos() {
		List<InformeLaboratorio> modeloInformeLabs = new ArrayList<>();
		modeloInformeLabs = informeLaboService.findAllActivos();
		return new ResponseEntity<List<InformeLaboratorio>>(modeloInformeLabs, HttpStatus.OK);
	}

	
	@PostMapping(path = MedicalAppUrl.INFORME_LAB_SAVE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AjaxResponseBody> save(@RequestBody InformeLaboratorioDto modeloInformeLab, Errors errores) {
		
		
		informeLaboService.save(modeloInformeLab);
		AjaxResponseBody resultAjax = new AjaxResponseBody();
		try {
			resultAjax.setMessage("Operacion Exitosa");
			return new ResponseEntity<AjaxResponseBody>(resultAjax, HttpStatus.OK);
		} catch (Exception e) {
			resultAjax.setErrorMessage(e.getMessage());

			return new ResponseEntity<AjaxResponseBody>(resultAjax, HttpStatus.OK);
		}

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	@PutMapping(path = MedicalAppUrl.INFORME_LAB_SAVE,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AjaxResponseBody> update(@RequestBody InformeLaboratorioDto informeLaboratorio) {
		informeLaboService.save(informeLaboratorio);
		AjaxResponseBody resultAjax = new AjaxResponseBody();
		resultAjax.setMessage("Operacion Exitosa");
		return new ResponseEntity<AjaxResponseBody>(resultAjax, HttpStatus.OK);
	}

	@RequestMapping(value = MedicalAppUrl.INFORME_LAB_DELETE, method = RequestMethod.DELETE)
	public ResponseEntity<AjaxResponseBody> delete(@PathVariable Integer id) {
		InformeLaboratorio modeloInformeLab = informeLaboService.findOne(id).get();
		AjaxResponseBody resultAjax = new AjaxResponseBody();
		if (modeloInformeLab == null) {
			throw new ModeloNotFoundException("ID: " + id);
		} else {
			informeLaboService.delete(modeloInformeLab);
		}
		resultAjax.setMessage("Se Eliminó con éxito");
		return ResponseEntity.status(HttpStatus.OK).body(resultAjax);
	}
	
	 
}

