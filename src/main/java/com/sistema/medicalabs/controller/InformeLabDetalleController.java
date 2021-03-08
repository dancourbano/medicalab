package com.sistema.medicalabs.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.sistema.medicalabs.AppProperties.MedicalAppUrl;
import com.sistema.medicalabs.AppProperties.MedicalAppView;
import com.sistema.medicalabs.entidad.AjaxResponseBody;
import com.sistema.medicalabs.entidad.InformeLaboratorio;
import com.sistema.medicalabs.service.InformeLaboratorioService;

@Controller
@RequestMapping("/informeLab/detalle")
public class InformeLabDetalleController {
	@Autowired
	private InformeLaboratorioService informeLaboService;

	@RequestMapping("/{id}")
	public String main(Model model, @PathVariable Integer id) {
		model.addAttribute("informeid", id); 
		return MedicalAppView.INFORME_LAB_DETALLE;
	}
	
	@GetMapping(value = MedicalAppUrl.INFORME_DOWNLOAD_PDF)
	public ResponseEntity<Object> getById(@PathVariable String id) throws FileNotFoundException, IOException {
	        String contenidoHtml;
	        AjaxResponseBody resultAjax = new AjaxResponseBody();
	        ByteArrayOutputStream target = new ByteArrayOutputStream();
	         // pdfHTML specific code
	       
	       
	        	String pdfHtml=informeLaboService.mostrarHtmlInforme(Integer.parseInt(id));
		        ConverterProperties converterProperties = new ConverterProperties();
		        HtmlConverter.convertToPdf(pdfHtml,target,converterProperties);
		        byte[] bytes = target.toByteArray();	
		        String encoded = Base64.getEncoder().encodeToString(bytes);
				resultAjax.setMessage(encoded);
		        return new ResponseEntity<Object>(resultAjax, HttpStatus.OK);
	        
	}
}
