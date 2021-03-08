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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
import com.sistema.medicalabs.entidad.AjaxResponseBody;
import com.sistema.medicalabs.entidad.Paciente;
import com.sistema.medicalabs.entidad.Users;
import com.sistema.medicalabs.exception.ModeloNotFoundException;
import com.sistema.medicalabs.service.PacienteService;

@Controller
@RequestMapping("/paciente")
public class PacienteController {
	@Autowired
	private PacienteService pacienteService;
	
	@Secured({"ROLE_ADMIN", "ROLE_PACIENTE"})
	@RequestMapping("/")
	public String main() {
		return MedicalAppView.PACIENTE;
	}
	@Secured({"ROLE_ADMIN", "ROLE_PACIENTE"})
	@GetMapping(value = MedicalAppUrl.GET_PACIENTE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getById(@PathVariable Integer id,Authentication authentication) {
		Optional<Paciente> optional = pacienteService.findOne(id);
		if(optional.isPresent()) {
			return new ResponseEntity<Object>(optional.get(), HttpStatus.OK);
		}else {
			AjaxResponseBody resultAjax = new AjaxResponseBody();
			resultAjax.setMessage("No se encontró resultados");
			return new ResponseEntity<Object>(resultAjax, HttpStatus.OK);
		}
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_PACIENTE"})
	@GetMapping(value = MedicalAppUrl.LIST_ALL_PACIENTE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Paciente>> getAll() {
		List<Paciente> pacientes = new ArrayList<>();
		pacientes = pacienteService.findAll();
		return new ResponseEntity<List<Paciente>>(pacientes, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_PACIENTE"})
	@GetMapping(value = MedicalAppUrl.LIST_ALL_PACIENTE_ACTIVOS, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Paciente>> getAllActivos() {
		List<Paciente> pacientes = new ArrayList<>();
		pacientes = pacienteService.findAllActivos();
		return new ResponseEntity<List<Paciente>>(pacientes, HttpStatus.OK);
	}
	@Secured("ROLE_ADMIN")
	@PostMapping(path = MedicalAppUrl.PACIENTE_SAVE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AjaxResponseBody> save(@RequestBody Paciente Paciente, Errors errores) {
		pacienteService.save(Paciente);

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
	@Secured("ROLE_ADMIN")
	@PutMapping(path = MedicalAppUrl.PACIENTE_SAVE,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AjaxResponseBody> update(@RequestBody Paciente Paciente) {
		pacienteService.save(Paciente);
		AjaxResponseBody resultAjax = new AjaxResponseBody();
		resultAjax.setMessage("Operacion Exitosa");
		return new ResponseEntity<AjaxResponseBody>(resultAjax, HttpStatus.OK);
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = MedicalAppUrl.DELETE_PACIENTE, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponseBody> delete(@PathVariable Integer id) {
		Paciente paciente = pacienteService.findOne(id).get();
		AjaxResponseBody resultAjax = new AjaxResponseBody();
		if (paciente == null) {
			throw new ModeloNotFoundException("ID: " + id);
		} else {
			pacienteService.delete(paciente);

		}
		resultAjax.setMessage("Se Eliminó con éxito");
		return ResponseEntity.status(HttpStatus.OK).body(resultAjax);
	}
	@Secured("ROLE_ADMIN")
	@PostMapping(path= "/getByDNI",headers = "Accept=application/json",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> reniecCliente(@RequestBody Paciente paciente) {
		Paciente pacienteIdentificado=pacienteService.getPersonaByDNI(paciente);
		return new ResponseEntity<Object>(pacienteIdentificado, HttpStatus.OK);
	}
}
