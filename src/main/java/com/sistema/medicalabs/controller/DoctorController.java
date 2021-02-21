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
import com.sistema.medicalabs.entidad.Doctor;
import com.sistema.medicalabs.entidad.Paciente;
import com.sistema.medicalabs.exception.ModeloNotFoundException;
import com.sistema.medicalabs.service.DoctorService;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/doctor")
public class DoctorController {
	@Autowired
	private DoctorService doctorService;
	
	 
	@RequestMapping("/")
	public String main() {
		return MedicalAppView.DOCTOR;
	}
	@GetMapping(value = MedicalAppUrl.GET_DOCTOR, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getById(@PathVariable Integer id) {
		Optional<Doctor> optional = doctorService.findOne(id);
		if(optional.isPresent()) {
			return new ResponseEntity<Object>(optional.get(), HttpStatus.OK);
		}else {
			AjaxResponseBody resultAjax = new AjaxResponseBody();
			resultAjax.setMessage("No se encontró resultados");
			return new ResponseEntity<Object>(resultAjax, HttpStatus.OK);
		}
	}
	@Secured("ROLE_ADMIN")
	@GetMapping(value = MedicalAppUrl.LIST_ALL_DOCTOR, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Doctor>> getAll() {
		List<Doctor> pacientes = new ArrayList<>();
		pacientes = doctorService.findAll();
		return new ResponseEntity<List<Doctor>>(pacientes, HttpStatus.OK);
	}
	@Secured("ROLE_ADMIN")
	@GetMapping(value = MedicalAppUrl.LIST_ALL_DOCTOR_ACTIVOS, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Doctor>> getAllActivos() {
		List<Doctor> pacientes = new ArrayList<>();
		pacientes = doctorService.findAllActivos();
		return new ResponseEntity<List<Doctor>>(pacientes, HttpStatus.OK);
	}
	 
	@PostMapping(path = MedicalAppUrl.DOCTOR_SAVE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AjaxResponseBody> save(@RequestBody Doctor Doctor, Errors errores) {
		doctorService.save(Doctor);

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
	@PutMapping(path = MedicalAppUrl.DOCTOR_SAVE,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AjaxResponseBody> update(@RequestBody Doctor Doctor) {
		doctorService.save(Doctor);
		AjaxResponseBody resultAjax = new AjaxResponseBody();
		resultAjax.setMessage("Operacion Exitosa");
		return new ResponseEntity<AjaxResponseBody>(resultAjax, HttpStatus.OK);
	}
	@RequestMapping(value = MedicalAppUrl.DELETE_DOCTOR, method = RequestMethod.POST)
	public ResponseEntity<AjaxResponseBody> delete(@PathVariable Integer id) {
		Doctor doctor = doctorService.findOne(id).get();
		AjaxResponseBody resultAjax = new AjaxResponseBody();
		if (doctor == null) {
			throw new ModeloNotFoundException("ID: " + id);
		} else {
			doctorService.delete(doctor);

		}
		resultAjax.setMessage("Se Eliminó con éxito");
		return ResponseEntity.status(HttpStatus.OK).body(resultAjax);
	} 
}
