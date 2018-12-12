package com.stefanini.uhubank.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.stefanini.uhubank.busines.BusinesException;
import com.stefanini.uhubank.dto.DTO;

public interface IRest<T extends DTO<T>, ENTITY> {

	@POST
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response criar(@Valid @NotNull T t) throws BusinesException;

	@PUT
	@Path("{codigo}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response atualizar(@PathParam("codigo")Long codigo, @Valid @NotNull T t) throws BusinesException;

	@DELETE
	@Path("{codigo}")
	public Response remover(@PathParam("codigo")Long codigo) throws BusinesException;
	
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response listar() throws BusinesException;

	@GET
	@Path("{codigo}")
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response buscarPorCodigo(@PathParam("codigo")Long codigo) throws BusinesException;

}