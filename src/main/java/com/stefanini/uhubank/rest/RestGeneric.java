package com.stefanini.uhubank.rest;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.stefanini.uhubank.busines.BusinessGeneric;
import com.stefanini.uhubank.event.RecursoCriadoEvent;
import com.stefanini.uhubank.rest.dto.DTO;

public abstract class RestGeneric<T extends DTO<T>,ENTITY> {
	
	@Inject
	protected Logger logger;
	
	@Inject
	protected Event<RecursoCriadoEvent> eventRecursoCriado;
	
	@Context
	protected HttpServletResponse response;
	
	@Context
	protected UriInfo uriInfo;
	
	@Inject
	protected BusinessGeneric<ENTITY,T> business;

}
