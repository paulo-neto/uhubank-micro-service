package com.stefanini.uhubank.busines;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.stefanini.uhubank.converter.IApiConverter;
import com.stefanini.uhubank.repository.GenericRepositoryImpl;


public abstract class BusinessGeneric<ENTITY,DTO> {

	@Inject
	protected Logger logger;
	
	@Inject
	protected GenericRepositoryImpl<ENTITY> repository;
	
	@Inject
	protected IApiConverter<ENTITY, DTO> converter;

	public abstract ENTITY atualizar(Long codigo, DTO dto) throws BusinesException;

	public abstract ENTITY salvar(DTO dto)throws BusinesException;

	public abstract void remover(Long codigo) throws BusinesException;

	public abstract List<ENTITY> listar(Class<ENTITY> clazz);

	public abstract ENTITY obterPorId(Long codigo);
	
}
