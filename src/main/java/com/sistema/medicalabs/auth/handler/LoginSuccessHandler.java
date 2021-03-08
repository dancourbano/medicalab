package com.sistema.medicalabs.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import com.sistema.medicalabs.entidad.Users;
import com.sistema.medicalabs.repository.UserRepository;
import com.sistema.medicalabs.utilidades.Utilidades;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private UserRepository userRepository;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		FlashMap flashMap=new FlashMap();
		flashMap.put("exito", "Ha Iniciado Sesión con éxito");
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		String username=((User)authentication.getPrincipal()).getUsername();
		Users user=userRepository.findByUsername(username);
		if(!Utilidades.isNullOrEmpty(user.getPaciente())) {
			session.setAttribute("userSession",user.getPaciente().getPacienteid().toString() );
		}else {
			session.setAttribute("userSession","");
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

}
