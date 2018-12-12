package com.stefanini.uhubank.busines.impl;
import static com.stefanini.uhubank.util.AssertUtils.isEmpty;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.stefanini.uhubank.busines.BusinesException;
import com.stefanini.uhubank.busines.BusinessGeneric;
import com.stefanini.uhubank.dto.PerfilDTO;
import com.stefanini.uhubank.mesages.KeyMesages;
import com.stefanini.uhubank.models.Perfil;
import com.stefanini.uhubank.repository.RepositoryException;


@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PerfilBusines extends BusinessGeneric<Perfil,PerfilDTO>{
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Perfil salvar(PerfilDTO perfil) throws BusinesException{
		try{
			Perfil p = converter.toEntidade(perfil);
			p.setId(null);
			return repository.save(p);
		}catch (RepositoryException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e),e);
			throw new BusinesException(e);
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Perfil atualizar(Long codigo, PerfilDTO perfil) throws BusinesException {
		Perfil perfilEncontrado = repository.findId(Perfil.class, codigo);
		if (isEmpty(perfilEncontrado)) {
			String arg[] = {codigo.toString()};
			throw new BusinesException(KeyMesages.USUARIO_NAO_ENCONTRADO,arg);
		}
		try {
			BeanUtils.copyProperties(perfilEncontrado, perfil);
			perfilEncontrado.setId(codigo);
			return repository.update(perfilEncontrado);
		} catch (IllegalAccessException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e),e);
			throw new BusinesException(KeyMesages.ERRO_GENERICO,ExceptionUtils.getRootCauseMessage(e));
		} catch (InvocationTargetException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e),e);
			throw new BusinesException(KeyMesages.ERRO_GENERICO,ExceptionUtils.getRootCauseMessage(e));
		} catch (RepositoryException e) {
			throw new BusinesException(KeyMesages.ERRO_GENERICO,ExceptionUtils.getRootCauseMessage(e));
		}
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remover(Long codigo) throws BusinesException {
		Perfil p = repository.findId(Perfil.class, codigo);
		try {
			repository.delete(p);
		} catch (RepositoryException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e),e);
			throw new BusinesException(KeyMesages.ERRO_GENERICO,ExceptionUtils.getRootCauseMessage(e));
		}
	}

	@Override
	public List<Perfil> listar(Class<Perfil> clazz){
		return repository.findAll(clazz);
	}
	
	@Override
	public Perfil obterPorId(Long codigo) {
		return repository.findId(Perfil.class, codigo);
	}
}
