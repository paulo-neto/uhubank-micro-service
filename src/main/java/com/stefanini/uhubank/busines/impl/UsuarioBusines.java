package com.stefanini.uhubank.busines.impl;
import static com.stefanini.uhubank.util.AssertUtils.isEmpty;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.stefanini.uhubank.busines.BusinesException;
import com.stefanini.uhubank.busines.BusinessGeneric;
import com.stefanini.uhubank.converter.impl.ArquivoConverter;
import com.stefanini.uhubank.dto.UsuarioDTO;
import com.stefanini.uhubank.mesages.KeyMesages;
import com.stefanini.uhubank.models.Perfil;
import com.stefanini.uhubank.models.Usuario;
import com.stefanini.uhubank.repository.RepositoryException;
import com.stefanini.uhubank.repository.impl.PerfilRepository;
import com.stefanini.uhubank.util.HashPasswordUtil;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UsuarioBusines extends BusinessGeneric<Usuario,UsuarioDTO >{
	
	@Inject
	private ArquivoConverter arquivoConverter;
	
	@Inject
	private PerfilRepository perfilRepository;
	
	@Inject
	private HashPasswordUtil hashPasswordUtil;
	
	public List<Usuario> listar(){
		return repository.findAll(Usuario.class);
	}
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario salvar(UsuarioDTO usuario) throws BusinesException{
		String hashedPwd = hashPasswordUtil.generateHash(usuario.getSenha());
		usuario.setSenha(hashedPwd);
		try{
			Usuario u = converter.toEntidade(usuario);
			u.setId(null);
			return repository.save(u);
		}catch (RepositoryException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e),e);
			throw new BusinesException(e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remover(Long codigo) throws BusinesException {
		Usuario u = repository.findId(Usuario.class, codigo);
		try {
			repository.delete(u);
		} catch (RepositoryException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e),e);
			throw new BusinesException(KeyMesages.ERRO_GENERICO,ExceptionUtils.getRootCauseMessage(e));
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario atualizar(Long codigo, UsuarioDTO usuario) throws BusinesException {
		Usuario usuarioEncontrado = repository.findId(Usuario.class, codigo);
		if (isEmpty(usuarioEncontrado)) {
			String arg[] = {codigo.toString()};
			throw new BusinesException(KeyMesages.USUARIO_NAO_ENCONTRADO,arg);
		}
		try {
			BeanUtils.copyProperties(usuarioEncontrado, usuario);
			usuarioEncontrado.setId(codigo);
			return repository.update(usuarioEncontrado);
		} catch (IllegalAccessException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e),e);
			throw new BusinesException(KeyMesages.ERRO_GENERICO,ExceptionUtils.getRootCauseMessage(e));
		} catch (InvocationTargetException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e),e);
			throw new BusinesException(KeyMesages.ERRO_GENERICO,ExceptionUtils.getRootCauseMessage(e));
		} catch (RepositoryException e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e),e);
			throw new BusinesException(KeyMesages.ERRO_GENERICO,ExceptionUtils.getRootCauseMessage(e));
		}
	}

	@Override
	public List<Usuario> listar(Class<Usuario> clazz){
		return repository.findAll(clazz);
	}

	@Override
	public Usuario obterPorId(Long codigo) {
		return repository.findId(Usuario.class, codigo);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void adicionaPerfilaUsuario(Long codigoPerfil, Long codigoUsuario) throws BusinesException {
		Perfil perfilEncontrado = perfilRepository.findId(Perfil.class, codigoPerfil);
		if(isEmpty(perfilEncontrado)){
			String arg[] = {codigoPerfil.toString()};
			throw new BusinesException(KeyMesages.PERFIL_NAO_ENCONTRADO,arg);
		}
		Usuario usuarioEncontrado = repository.findId(Usuario.class, codigoUsuario);
		if(isEmpty(usuarioEncontrado)){
			String arg[] = {codigoUsuario.toString()};
			throw new BusinesException(KeyMesages.USUARIO_NAO_ENCONTRADO,arg);
		}
		usuarioEncontrado.getPerfis().add(perfilEncontrado);
		try {
			repository.update(usuarioEncontrado);
		} catch (RepositoryException e) {
			throw new BusinesException(KeyMesages.ERRO_GENERICO,ExceptionUtils.getRootCauseMessage(e));
		}
	}

	public List<Perfil> consultarPerfisDoUsuario(Long codigoUsuario){
		return perfilRepository.consultarPerfisDoUsuario(codigoUsuario);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void adicionaImagemUsuario(Long idUsuario, MultipartFormDataInput upload)throws BusinesException {
		Usuario usuarioEncontrado = repository.findId(Usuario.class, idUsuario);
		if(isEmpty(usuarioEncontrado)){
			String arg[] = {idUsuario.toString()};
			throw new BusinesException(KeyMesages.USUARIO_NAO_ENCONTRADO,arg);
		}
		usuarioEncontrado.setImagem(arquivoConverter.toEntidade(upload));
	}
	
}
