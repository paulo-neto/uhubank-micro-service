package com.stefanini.uhubank.converter.impl;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.stefanini.uhubank.converter.IApiConverter;
import com.stefanini.uhubank.models.Usuario;
import com.stefanini.uhubank.rest.dto.UsuarioDTO;

public class UsuarioConverter implements IApiConverter<Usuario, UsuarioDTO> {

	@Inject
	private Logger logger;
	
	@Override
	public Usuario toEntidade(UsuarioDTO dto) {
		Usuario u = new Usuario();
		try {
			BeanUtils.copyProperties(u, dto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e),e);
		}
		return u;
	}

	@Override
	public UsuarioDTO toDto(Usuario entidade) {
		UsuarioDTO d = new UsuarioDTO();
		try {
			BeanUtils.copyProperties(d, entidade);
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e),e);
		}
		return d;
	}

}
