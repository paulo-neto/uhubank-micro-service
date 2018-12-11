package com.stefanini.uhubank.rest.handler;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.stefanini.uhubank.busines.BusinesException;


@Provider
public class APIExceptionHandler implements ExceptionMapper<Exception>, ConfiguraResponseExceptionHandler {

	@Inject
	private Logger logger;
	
	@Override
	public Response toResponse(Exception ex) {
		String causa = ex instanceof BusinesException ? ((BusinesException) ex).getMessage() : "Erro no servidor";
		ErroMessage erro = null;
		logger.error(ExceptionUtils.getRootCauseMessage(ex),ex);
		erro = new ErroMessage(causa, ExceptionUtils.getStackTrace(ex));
		List<ErroMessage> erros = Arrays.asList(erro);
		return configurar(MediaType.APPLICATION_JSON_TYPE,erros, Status.INTERNAL_SERVER_ERROR);
	}
	
	
	/* (non-Javadoc)
	 * @see com.apirest.resources.handler.ConfiguraResponseExceptionHandler#configurar(javax.ws.rs.core.MediaType, java.lang.Object, javax.ws.rs.core.Response.Status)
	 */
	@Override
	public Response configurar(MediaType mediaType, List<ErroMessage> obj, Status statusHttp){
		return Response.status(statusHttp).type(mediaType).entity(obj).build();
	}
}
