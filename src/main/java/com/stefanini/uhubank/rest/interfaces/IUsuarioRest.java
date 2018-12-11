package com.stefanini.uhubank.rest.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.stefanini.uhubank.busines.BusinesException;
import com.stefanini.uhubank.models.Usuario;
import com.stefanini.uhubank.rest.IRest;
import com.stefanini.uhubank.rest.dto.DTO;
import com.stefanini.uhubank.rest.dto.UsuarioDTO;

@Path("usuarios")
public interface IUsuarioRest<T extends DTO<T>,ENTITY> extends IRest<UsuarioDTO, Usuario> {

	@PUT
	@Path("/adicona-perfil/{codigoPerfil}/usuario/{codigoUsuario}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response adicionaPerfilaUsuario(@PathParam("codigoPerfil") Long codigoPerfil,
			@PathParam("codigoUsuario") Long codigoUsuario) throws BusinesException;
	
	@GET
	@Path("/consultar-perfis-usuario/{codigoUsuario}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response buscarPerfisUsuario(@PathParam("codigoUsuario") Long codigoUsuario) throws BusinesException;
	
	@POST
	@Path("/adiciona-imagem-usuario/{idUsuario}")
	@Consumes(value = { MediaType.MULTIPART_FORM_DATA })
	public Response adicionaImagemusuario(@PathParam("idUsuario") Long idUsuario,
			MultipartFormDataInput upload )throws BusinesException;
	
}
