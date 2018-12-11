package com.stefanini.uhubank.rest.handler;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public interface ConfiguraResponseExceptionHandler {

	Response configurar(MediaType mediaType, List<ErroMessage> erros, Status statusHttp);

}