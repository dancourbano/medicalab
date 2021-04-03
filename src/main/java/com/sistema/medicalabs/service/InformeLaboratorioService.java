package com.sistema.medicalabs.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
import com.sistema.medicalabs.utilidades.Utilidades;

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
		 
		Optional<Paciente> optioPaciente=pacienteService.findOne(Integer.parseInt(entity.getPacienteid()));
		if(optioPaciente.isPresent()) {
			informeLab.setPaciente(optioPaciente.get());
		}else {
			
		}
		if(!Utilidades.isNullOrEmpty(entity.getDoctorid())){
			Optional<Doctor> optionDoctor=doctorService.findOne(Integer.parseInt(entity.getDoctorid()));
			if(optionDoctor.isPresent()) {
				informeLab.setDoctor(optionDoctor.get());
			}
		}
		informeLab.setDetalle(entity.getDetalle().replace("\"", "'"));
		informeLab.setInformeid(entity.getInformeid());
		informeLab.setFechaReporte(entity.getFechaReporte());
		informeLab.setHoraReporte(entity.getHoraReporte());
		String[] arrayModelos=entity.getModeloid().split(",");
		informeLab.setModeloid(String.join(",", arrayModelos));
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
	       		"<tr style=\"height: 122px;\">" + 
	       		
	       		"<td style=\"width: 23%; height: 32px;\"></td>" + 
	       		"</tr>" + 
	       		"</tbody>" + 
	       		"</table>" + 
	       		 
	       		"<p>&nbsp;</p>" + 
	       		"<p></p>";
	       
	    String htmlFooter="<table border=\"0\" style=\"border-collapse: collapse; width: 100%;\">" + 
		       		"<tbody>" + 
		       		"<tr>" + 
		       		"<td style=\"width: 25%;\"></td>" + 
		       		"<td style=\"width: 25%;\"></td>" + 
		       		"<td style=\"width: 25%;\"></td>" + 
		       		"<td style=\"width: 25%;\"></td>"+
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
		String contenido="<table style=\"border-collapse: collapse; width: 100%; height: 10px; font-size: 10px;\" border=\"0 \">" + 
				"<tbody>" + 
				"<tr style=\"height: 8px;\">" + 
				"<td style=\"width: 50%; height: 2px;\">" + 
				"Paciente: "+ informe.getPaciente().getNombre()+" "+informe.getPaciente().getApellidos() +"" + 
				"</td>" + 
				"<td style=\"width: 50%; height: 2px;\">" +				 
				"</td>" + 
				"</tr>" + 
				"<tr style=\"height: 8px;\">" + 
				"<td style=\"width: 50%; height: 2px;\">" + 
				"Referencia: "+ validacionDoctor(informe.getDoctor())  + 
				"</td>" + 
				"<td style=\"width: 50%; height: 2px;\">" + 
				"Fecha: " + informe.getFechaReporte()+
				"</td>" + 
				"</tr>" + 
				"<tr style=\"height: 8px;\">" + 
				"<td style=\"width: 50%; height: 2px;\">" + 
				"Edad: " + calculateAge(informe.getPaciente().getFechaNacimiento())+
				"</td>" + 
				"<td style=\"width: 50%; height: 2px;\">" + 
				"Hora toma de Muestra: " + informe.getHoraReporte()+ 
				"</td>" + 
				"</tr>" + 
				"</tbody>" + 
				"</table><hr>";
		return contenido;
	}

	private String calculateAge(String fechaNacimiento) {
		if(fechaNacimiento!=null) {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
		 LocalDate birthDate = LocalDate.parse(fechaNacimiento, formatter);
		 int age=Period.between(birthDate, LocalDate.now()).getYears();
		 return String.valueOf(age);
		}else {
			return "";
		}
	}

	private String validacionDoctor(Doctor doctor) {
		String nombreApellidoDoctor="Particular";
		if(doctor!=null) {
			nombreApellidoDoctor=doctor.getNombre()+" "+doctor.getApellidos();
		}
		return nombreApellidoDoctor;
	}
	
}
