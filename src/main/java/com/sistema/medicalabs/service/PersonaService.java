package com.sistema.medicalabs.service;

import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import com.sistema.medicalabs.dto.PersonaDto;

@Service
public class PersonaService {
	
	public PersonaDto getClienteByDni(String dni) {
		String uri = "https://dniruc.apisperu.com/api/v1/dni/";
		String token = "?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImRhbmllbGNhbS5pbmZAZ21haWwuY29tIn0.VzM5FN1-zMinfqMTmwPY-2J1I9qCft-MyasuYO5LuJ8";
		ResponseEntity<PersonaDto> restTemplate1 = null;
		String concatenadoURL = uri.concat(dni).concat(token);


		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext;
		try {
			sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
					.build();
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

			requestFactory.setHttpClient(httpClient);

			RestTemplate restTemplate = new RestTemplate(requestFactory);
			restTemplate1 = restTemplate.exchange(concatenadoURL, HttpMethod.GET, null,
					PersonaDto.class);

		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return restTemplate1.getBody();

	}

	public PersonaDto getClienteByRUC(String ruc) {
		String uriConsulta = "https://dniruc.apisperu.com/api/v1/ruc/";
		RestTemplate restTemplate = new RestTemplate();
		// Obtener numero RUC
		String token = "?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImRhbmllbGNhbS5pbmZAZ21haWwuY29tIn0.VzM5FN1-zMinfqMTmwPY-2J1I9qCft-MyasuYO5LuJ8";
		String consultaFormada = uriConsulta.concat(ruc).concat(token);
		ResponseEntity<PersonaDto> restTemplate1 = null;
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext;
		try {
			sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
					.build();
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

			requestFactory.setHttpClient(httpClient);

			restTemplate = new RestTemplate(requestFactory);
			restTemplate1 = restTemplate.exchange(consultaFormada, HttpMethod.GET, null,
					PersonaDto.class);

			 
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restTemplate1.getBody();
	}
	
	 
}
