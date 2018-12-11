package com.stefanini.uhubank.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

/**
 * Interface que define os métodos de mínimos de manipulação com o
 * Banco de Dados. 
 * @author Paulo Antonio
 */
public interface IGenericRepository<T> {

	/**
	 * Salva uma Entidade.
	 * @param entity
	 * @return
	 * @throws RepositoryException
	 */
	public T save(T entity) throws RepositoryException;
	
	/**
	 * Salva uma Lista de Entidade.
	 * @param entity
	 * @return
	 * @throws RepositoryException
	 */
	public List<T> saveList(List<T> lista)throws RepositoryException;

	/**
	 * Deleta uma Entidade.
	 * @param entity
	 * @return
	 * @throws RepositoryException
	 */
	public void delete(T entity)throws RepositoryException;

	/**
	 * Altera uma Entidade.
	 * @param entity
	 * @return
	 * @throws RepositoryException
	 */
	public T update(T entity)throws RepositoryException;

	/**
	 * Consulta uma Entidade pelo id.
	 * @param entity
	 * @return
	 * @throws RepositoryException
	 */
	public T findId(Class<T> clazz,Long entityID);

	/**
	 * Executa uma {@link Query} chamando o método getSingleResult()
	 * @param consulta
	 * @param parametros
	 * @param clazz
	 * @return
	 */
	public T executarQueryUnique(String consulta, Map<String, Object> parametros, Class<T> clazz);

	/**
	 * Executa uma {@link Query} chamando o método getResultList()
	 * @param consulta
	 * @param parametros
	 * @param clazz
	 * @return
	 */
	public List<T> executarQuery(String consulta, Map<String, Object> parametros, Class<T> clazz);
}
