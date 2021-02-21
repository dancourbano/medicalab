package com.sistema.medicalabs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.medicalabs.AppProperties.ConstantesMedical;
import com.sistema.medicalabs.dto.InformeLaboratorioDto;
import com.sistema.medicalabs.entidad.Doctor;
import com.sistema.medicalabs.entidad.InformeLaboratorio;
import com.sistema.medicalabs.entidad.ModeloInformeLaboratorio;
import com.sistema.medicalabs.entidad.Paciente;
import com.sistema.medicalabs.repository.InformeLaboratorioRepository;

@Service
public class InformeLaboratorioService {
	@Autowired
	private InformeLaboratorioRepository informeLabRepository;
	
	@Autowired
	private ModeloInformeLabService modeloInformeLabService;
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private DoctorService doctorService;
	
	public List<InformeLaboratorio> findAll() {
		List<InformeLaboratorio> informeLaboratorios=new ArrayList<InformeLaboratorio>();
		informeLabRepository.findAll().forEach(informeLaboratorios::add);
		return informeLaboratorios;
	}
	
	public List<InformeLaboratorio> findAllActivos() {
		List<InformeLaboratorio> informeLaboratorios=new ArrayList<InformeLaboratorio>();
		informeLabRepository.findByPacienteEstado(ConstantesMedical.UNO_STR).forEach(informeLaboratorios::add);
		return informeLaboratorios;
	}

	public List<InformeLaboratorio> findAllByPaciente(String pacienteid) {
		List<InformeLaboratorio> informeLaboratorios=new ArrayList<InformeLaboratorio>();
		informeLabRepository.findByPacientePacienteid(Integer.parseInt(pacienteid)).forEach(informeLaboratorios::add);
		return informeLaboratorios;
	}
	
	public Optional<InformeLaboratorio> findOne(Integer id) {
		return informeLabRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public InformeLaboratorio save(InformeLaboratorioDto entity) {
		InformeLaboratorio informeLab=new InformeLaboratorio();
		Optional<ModeloInformeLaboratorio> optionModelo= 
				modeloInformeLabService.findOne(Integer.parseInt(entity.getModeloid()));
		
		if(optionModelo.isPresent()) {
			 informeLab.setModeloInformeLaboratorio(optionModelo.get());
		}else {
			
		}
		Optional<Paciente> optioPaciente=pacienteService.findOne(Integer.parseInt(entity.getPacienteid()));
		if(optioPaciente.isPresent()) {
			informeLab.setPaciente(optioPaciente.get());
		}else {
			
		}
		Optional<Doctor> optionDoctor=doctorService.findOne(Integer.parseInt(entity.getDoctorid()));
		if(optionDoctor.isPresent()) {
			informeLab.setDoctor(optionDoctor.get());
		}
		
		informeLab.setDetalle(entity.getDetalle().replace("\"", "'"));
		informeLab.setInformeid(entity.getInformeid());
		informeLab.setFechaReporte(entity.getFechaReporte());
		return informeLabRepository.save(informeLab);
	}

	@Transactional(readOnly = false)
	public void delete(InformeLaboratorio entity) {
		informeLabRepository.delete(entity);
	}
	
	public String mostrarHtmlInforme(Integer id) {
		String contenidoHtml="";
		String contenidoHtmlInforme="";
		String htmlHead="<html><body>" + 
	       		"<table border=\"0\" style=\"border-collapse: collapse; width: 100.549%; border-color: red; height: 32px;\">" + 
	       		"<tbody>" + 
	       		"<tr style=\"height: 32px;\">" + 
	       		"<td style=\"width: 23%; height: 32px;\"></td>" + 
	       		"<td style=\"width: 25%; height: 32px;\"><img src=\"https://c0.klipartz.com/pngpicture/278/710/sticker-png-aperture-laboratories-logo-laboratory-graphy-newton-game-leaf-text-photography-logo.png\" alt=\"\" width=\"185\" height=\"115\" /></td>" + 
	       		"<td style=\"width: 28%; height: 32px;\"><b>Centro Medico MediLab</b>" + 
	       		"<h5><span>Av. Rosales 145 II etapa</span></h5>" + 
	       		"<span>contact@medilab.com</span><br /><span>telf. 987897767 - 044 656565</span><br /><span>Trujillo, Per&uacute;</span></td>" + 
	       		"<td style=\"width: 23%; height: 32px;\"></td>" + 
	       		"</tr>" + 
	       		"</tbody>" + 
	       		"</table>" + 
	       		"<hr />" + 
	       		"<p>&nbsp;</p>" + 
	       		"<p></p>";
	       
	    String htmlFooter="<table border=\"0\" style=\"border-collapse: collapse; width: 100%;\">" + 
		       		"<tbody>" + 
		       		"<tr>" + 
		       		"<td style=\"width: 25%;\"></td>" + 
		       		"<td style=\"width: 25%;\"></td>" + 
		       		"<td style=\"width: 25%;\"></td>" + 
		       		"<td style=\"width: 25%;\"><img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Miguel_D%C3%ADaz-Canel_firma.png/800px-Miguel_D%C3%ADaz-Canel_firma.png\" alt=\"\" width=\"168\" height=\"99\" /></td>" + 
		       		"</tr>" + 
		       		"<tr>" + 
		       		"<td style=\"width: 25%;\"></td>" + 
		       		"<td style=\"width: 25%;\"></td>" + 
		       		"<td style=\"width: 25%;\"></td>" + 
		       		"<td style=\"width: 25%;\">Lc. Daniel Perez Gomez</td>" + 
		       		"</tr>" + 
		       		"</tbody>" + 
		       		"</table></body></html>";
	    Optional<InformeLaboratorio> informe=findOne(id);
        if(informe.isPresent()) {
        	contenidoHtmlInforme=informe.get().getDetalle();
        	contenidoHtml=htmlHead+crearHtmlDatosPaciente(informe.get())+contenidoHtmlInforme+htmlFooter;
        }
		return contenidoHtml;
	}
	private String crearHtmlDatosPaciente(InformeLaboratorio informe) {
		String contenido="<table style=\"border-collapse: collapse; width: 100%; height: 30px; font-size: 10px;\" border=\"0 \">" + 
				"<tbody>" + 
				"<tr style=\"height: 8px;\">" + 
				"<td style=\"width: 50%; height: 8px;\">" + 
				"<p>Paciente:"+ informe.getPaciente().getNombre()+" "+informe.getPaciente().getApellidos() +"</p>" + 
				"</td>" + 
				"<td style=\"width: 50%; height: 8px;\">" +				 
				"</td>" + 
				"</tr>" + 
				"<tr style=\"height: 8px;\">" + 
				"<td style=\"width: 50%; height: 8px;\">" + 
				"<p>Referencia:"+ validacionDoctor(informe.getDoctor()) +"</p>" + 
				"</td>" + 
				"<td style=\"width: 50%; height: 8px;\">" + 
				"<p>Fecha</p>" + 
				"</td>" + 
				"</tr>" + 
				"<tr style=\"height: 8px;\">" + 
				"<td style=\"width: 50%; height: 10px;\">" + 
				"<p>Edad</p>" + 
				"</td>" + 
				"<td style=\"width: 50%; height: 10px;\">" + 
				"<p>Hora toma de Muestra</p>" + 
				"</td>" + 
				"</tr>" + 
				"</tbody>" + 
				"</table>";
		return contenido;
	}

	private String validacionDoctor(Doctor doctor) {
		String nombreApellidoDoctor="Particular";
		if(doctor!=null) {
			nombreApellidoDoctor=doctor.getNombre()+" "+doctor.getApellidos();
		}
		return nombreApellidoDoctor;
	}
	
}
