package com.stefanini.uhubank.rest.impl;
import static com.stefanini.uhubank.util.AssertUtils.isEmpty;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.stefanini.uhubank.busines.BusinesException;
import com.stefanini.uhubank.dto.PerfilDTO;
import com.stefanini.uhubank.event.RecursoCriadoEvent;
import com.stefanini.uhubank.models.Perfil;
import com.stefanini.uhubank.rest.RestGeneric;
import com.stefanini.uhubank.rest.interfaces.IPerfilRest;

public class PerfilRest extends RestGeneric<PerfilDTO,Perfil> implements IPerfilRest<PerfilDTO, Perfil>{
	
	@Override
	public Response criar(PerfilDTO perfil) throws BusinesException {
		Perfil perfilSalvo = business.salvar(perfil);
		eventRecursoCriado.fire(new RecursoCriadoEvent(response,uriInfo, perfilSalvo.getId()));
		return Response.status(Status.CREATED).entity(perfilSalvo).build();
	}
	
	@Override
	public Response atualizar(Long codigo, PerfilDTO perfil) throws BusinesException {
		Perfil perfilAtualizado = business.atualizar(codigo,perfil);
		return Response.ok().entity(perfilAtualizado).build();
	}

	@Override
	public Response remover(Long codigo) throws BusinesException {
		business.remover(codigo);
		return Response.status(Status.OK).build();
	}

	@Override
	public Response listar() throws BusinesException {
		List<Perfil> retorno = business.listar(Perfil.class);
		return isEmpty(retorno) ? Response.status(Status.NO_CONTENT).build()
				: Response.status(Status.OK).entity(retorno).build();
	}

	@Override
	public Response buscarPorCodigo(Long codigo) throws BusinesException {
		Perfil perfilEncontrado = business.obterPorId(codigo);
		return isEmpty(perfilEncontrado) ? Response.status(Status.NO_CONTENT).build()
				: Response.status(Status.OK).entity(perfilEncontrado).build();
	}

}
