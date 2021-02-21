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
import com.sistema.medicalabs.entidad.ModeloInformeLaboratorio;
import com.sistema.medicalabs.exception.ModeloNotFoundException;
import com.sistema.medicalabs.service.ModeloInformeLabService;

@Controller
@RequestMapping("/modeloinformelab")
public class ModeloInformeLabController {
	
	
		@Autowired
		private ModeloInformeLabService modeloLabService;

		@RequestMapping("/")
		public String main() {
			return MedicalAppView.MODELO_INFORME_LAB;
		}
		
		@GetMapping(value = MedicalAppUrl.GET_MODELO_INFORME_LAB, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Object> getById(@PathVariable Integer id) {
			Optional<ModeloInformeLaboratorio> optional = modeloLabService.findOne(id);
			if(optional.isPresent()) {
				return new ResponseEntity<Object>(optional.get(), HttpStatus.OK);
			}else {
				AjaxResponseBody resultAjax = new AjaxResponseBody();
				resultAjax.setMessage("No se encontró resultados");
				return new ResponseEntity<Object>(resultAjax, HttpStatus.OK);
			}
		}
		
		@GetMapping(value = MedicalAppUrl.LIST_ALL_MODELO_INFORME_LAB, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<ModeloInformeLaboratorio>> getAll() {
			List<ModeloInformeLaboratorio> modeloInformeLabs = new ArrayList<>();
			modeloInformeLabs = modeloLabService.findAll();
			return new ResponseEntity<List<ModeloInformeLaboratorio>>(modeloInformeLabs, HttpStatus.OK);
		}

		@PostMapping(path = MedicalAppUrl.MODELO_INFORME_LAB_SAVE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<AjaxResponseBody> save(@RequestBody ModeloInformeLaboratorio modeloInformeLab, Errors errores) {
			modeloLabService.save(modeloInformeLab);
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

		@PutMapping(path = MedicalAppUrl.MODELO_INFORME_LAB_SAVE,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<AjaxResponseBody> update(@RequestBody ModeloInformeLaboratorio ModeloInformeLaboratorio) {
			modeloLabService.save(ModeloInformeLaboratorio);
			AjaxResponseBody resultAjax = new AjaxResponseBody();
			resultAjax.setMessage("Operacion Exitosa");
			return new ResponseEntity<AjaxResponseBody>(resultAjax, HttpStatus.OK);
		}

		@RequestMapping(value = MedicalAppUrl.MODELO_INFORME_LAB_DELETE, method = RequestMethod.DELETE)
		public ResponseEntity<AjaxResponseBody> delete(@PathVariable Integer id) {
			ModeloInformeLaboratorio modeloInformeLab = modeloLabService.findOne(id).get();
			AjaxResponseBody resultAjax = new AjaxResponseBody();
			if (modeloInformeLab == null) {
				throw new ModeloNotFoundException("ID: " + id);
			} else {
				modeloLabService.delete(modeloInformeLab);
			}
			resultAjax.setMessage("Se Eliminó con éxito");
			return ResponseEntity.status(HttpStatus.OK).body(resultAjax);
		}
		
		 
	}
