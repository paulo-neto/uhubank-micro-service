package com.stefanini;

import java.util.List;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefanini.uhubank.client.PerfilRestClient;
import com.stefanini.uhubank.rest.dto.PerfilDTO;

public class ClientRest {

	public static void main(String[] args) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/api-rest/api/perfis");
		PerfilRestClient perfilClient = target.proxy(PerfilRestClient.class);
		
		try {
//			PerfilDTO dto = new PerfilDTO(null,"DBA");
//			Response criar = perfilClient.criar(dto);
//			System.out.println(criar.toString());
			
			Response listar = perfilClient.listar();
			List readEntity = listar.readEntity(List.class);
			for (Object object : readEntity) {
				ObjectMapper mapper = new ObjectMapper();
				PerfilDTO p = mapper.convertValue(object, PerfilDTO.class);
				System.out.println(p.getPerfil());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
