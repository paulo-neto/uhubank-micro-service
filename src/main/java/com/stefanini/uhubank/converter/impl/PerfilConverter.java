package com.stefanini.uhubank.converter.impl;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.stefanini.uhubank.converter.IApiConverter;
import com.stefanini.uhubank.models.Perfil;
import com.stefanini.uhubank.rest.dto.PerfilDTO;

public class PerfilConverter implements IApiConverter<Perfil, PerfilDTO> {
	
	@Inject
	private Logger logger;
	
	@Override
	public Perfil toEntidade(PerfilDTO dto) {
		Perfil p = new Perfil();
		try {
			BeanUtils.copyProperties(p, dto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e),e);
		}
		return p;
	}

	@Override
	public PerfilDTO toDto(Perfil entidade) {
		PerfilDTO d = new PerfilDTO();
		try {
			BeanUtils.copyProperties(d, entidade);
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e),e);
		}
		return d;
	}

}
