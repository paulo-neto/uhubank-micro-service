package com.stefanini.uhubank.repository.impl;

import java.util.HashMap;
import java.util.List;

import com.stefanini.uhubank.models.Perfil;
import com.stefanini.uhubank.repository.GenericRepositoryImpl;

public class PerfilRepository extends GenericRepositoryImpl<Perfil> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<Perfil> consultarPerfisDoUsuario(Long codigoUsuario) {
		StringBuilder consulta = new StringBuilder("select u.perfis from Usuario u where u.id = :cdUsuario");
		return executarQuery(consulta.toString(), new HashMap<String, Object>(), Perfil.class);
	}
}
