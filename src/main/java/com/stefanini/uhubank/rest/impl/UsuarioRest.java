package com.stefanini.uhubank.rest.impl;
import static com.stefanini.uhubank.util.AssertUtils.isEmpty;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.stefanini.uhubank.busines.BusinesException;
import com.stefanini.uhubank.busines.impl.UsuarioBusines;
import com.stefanini.uhubank.dto.UsuarioDTO;
import com.stefanini.uhubank.event.RecursoCriadoEvent;
import com.stefanini.uhubank.models.Perfil;
import com.stefanini.uhubank.models.Usuario;
import com.stefanini.uhubank.rest.RestGeneric;
import com.stefanini.uhubank.rest.interfaces.IUsuarioRest;

public class UsuarioRest extends RestGeneric<UsuarioDTO,Usuario> implements IUsuarioRest<UsuarioDTO,Usuario> {

	@Override
	public Response criar(UsuarioDTO usuario) throws BusinesException {
		Usuario usuarioSalvo = business.salvar(usuario);
		eventRecursoCriado.fire(new RecursoCriadoEvent(response,uriInfo, usuarioSalvo.getId()));
		return Response.status(Status.CREATED).entity(usuarioSalvo).build();
	}

	@Override
	public Response atualizar(Long codigo, UsuarioDTO usuario) throws BusinesException {
		Usuario usuAtualizado = business.atualizar(codigo,usuario);
		return Response.ok().entity(usuAtualizado).build();
	}

	@Override
	public Response remover(Long codigo) throws BusinesException {
		business.remover(codigo);
		return Response.status(Status.OK).build();
	}

	@Override
	public Response listar() throws BusinesException {
		List<Usuario> retorno = business.listar(Usuario.class);
		return isEmpty(retorno) ? Response.status(Status.NO_CONTENT).build()
				: Response.status(Status.OK).entity(retorno).build();
	}

	@Override
	public Response buscarPorCodigo(Long codigo) throws BusinesException {
		Usuario usuarioEncontrado = business.obterPorId(codigo);
		return isEmpty(usuarioEncontrado) ? Response.status(Status.NO_CONTENT).build()
				: Response.status(Status.OK).entity(usuarioEncontrado).build();
	}

	@Override
	public Response adicionaPerfilaUsuario(Long codigoPerfil, Long codigoUsuario) throws BusinesException {
		((UsuarioBusines) business).adicionaPerfilaUsuario(codigoPerfil,codigoUsuario);
		return Response.status(Status.OK).build();
	}

	@Override
	public Response buscarPerfisUsuario(Long codigoUsuario) throws BusinesException {
		List<Perfil> perfisDoUsuario = ((UsuarioBusines) business).consultarPerfisDoUsuario(codigoUsuario);
		return isEmpty(perfisDoUsuario) ? Response.status(Status.NO_CONTENT).build()
				: Response.status(Status.OK).entity(perfisDoUsuario).build();
	}

	@Override
	public Response adicionaImagemusuario(Long idUsuario,
			MultipartFormDataInput upload) throws BusinesException {
		((UsuarioBusines) business).adicionaImagemUsuario(idUsuario,upload);
		return Response.status(Status.OK).build();
	}
}
