package com.sistema.medicalabs.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
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
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.kernel.utils.PdfMerger;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
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
		        
		       
		        PdfWriter writer = new PdfWriter(target);
		        PdfDocument pdf = new PdfDocument(writer);
		        Document doc = new Document(pdf);  
		       

		       // writer.write(bytes);
		        PdfMerger merger = new PdfMerger(pdf);
		      
		            ByteArrayOutputStream baos = new ByteArrayOutputStream();
		            PdfDocument temp = new PdfDocument(new PdfWriter(baos));
		            HtmlConverter.convertToPdf(pdfHtml, temp, converterProperties);
		            temp = new PdfDocument(
		                new PdfReader(new ByteArrayInputStream(baos.toByteArray())));
		            merger.merge(temp, 1, temp.getNumberOfPages());
		            temp.close();
		            
		            doc.add( getWatermarkedImage(doc,pdf,new Image(ImageDataFactory.create("C:/Users/USER/Documents/logo2.png"))));
		            doc.add( getWatermarkedImage(doc,pdf,new Image(ImageDataFactory.create("C:/Users/USER/Documents/logo2.png"))));
			     doc.close();  
		        String encoded = Base64.getEncoder().encodeToString(target.toByteArray());
				resultAjax.setMessage(encoded);
		        return new ResponseEntity<Object>(resultAjax, HttpStatus.OK);
	        
	}
	
	 private static Image getWatermarkedImage(Document doc,PdfDocument pdfdocument, Image img) {
		 img.scaleToFit(100, 100);
		 
		 img.setFixedPosition(40,  10);
		 PdfCanvas pdfCanvas = new PdfCanvas(pdfdocument.getLastPage());
		 PdfFormXObject template = new PdfFormXObject( 
		            new Rectangle(img.getImageScaledWidth(), 
		            		img.getImageScaledHeight())); 
		 Canvas canvas 
         = new Canvas(pdfCanvas, pdfdocument,doc.getPageEffectiveArea(pdfdocument.getDefaultPageSize())).add(img); 
       

     // adding template to document 
	     return new Image(template);
	 }
}
