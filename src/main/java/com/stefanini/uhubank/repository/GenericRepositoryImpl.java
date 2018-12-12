package com.stefanini.uhubank.repository;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.stefanini.uhubank.mesages.KeyMesages;

public abstract class GenericRepositoryImpl<T> implements IGenericRepository<T>, Serializable {

	private static final long serialVersionUID = 6163696283184654122L;
	
	@PersistenceContext
	protected EntityManager entityManager;

	@Inject
	protected Logger logger;
	
	
	/* (non-Javadoc)
	 * @see com.apirest.repository.IGenericRepository#save(java.lang.Object)
	 */
	@Override
	public T save(T entity) throws RepositoryException {
		try {
			entityManager.persist(entity);
			return entity;
		} catch (Exception e) {
			throw new RepositoryException(KeyMesages.ERRO_SALVAR, ExceptionUtils.getRootCauseMessage(e));
		}
	}
	
	/* (non-Javadoc)
	 * @see com.apirest.repository.IGenericRepository#saveList(java.util.List)
	 */
	@Override
	public List<T> saveList(List<T> lista)throws RepositoryException{
		int contador = 1;
		List<T> persistedList = new ArrayList<T>();
		for(T t: lista){
			entityManager.persist(t);
			//a cada 50 Entidades, sincroniza e limpa o cache
			if (contador % 50 == 0) {
				entityManager.flush();
				entityManager.clear();
	        }
			contador++;
			persistedList.add(t);
		}
		return persistedList;
	}

	
	/* (non-Javadoc)
	 * @see com.apirest.repository.IGenericRepository#delete(java.lang.Object)
	 */
	@Override
	public void delete(T entity) throws RepositoryException {
		try {
			entityManager.remove(entity);
		} catch (Exception e) {
			throw new RepositoryException(KeyMesages.ERRO_DELETAR, ExceptionUtils.getRootCauseMessage(e));
		}
	}

	
	/* (non-Javadoc)
	 * @see com.apirest.repository.IGenericRepository#update(java.lang.Object)
	 */
	@Override
	public T update(T entity) throws RepositoryException {
		try {
			entity = entityManager.merge(entity);
			return entity;
		} catch (Exception e) {
			throw new RepositoryException(KeyMesages.ERRO_ATUAIZAR, ExceptionUtils.getRootCauseMessage(e));
		}
	}
	
	public List<T> updateList(List<T> lista)throws RepositoryException{
		int contador = 1;
		List<T> mergedList = new ArrayList<T>();
		for(T t: lista){
			entityManager.merge(t);
			//a cada 50 Entidades, sincroniza e limpa o cache
			if (contador % 50 == 0) {
				entityManager.flush();
				entityManager.clear();
	        }
			contador++;
			mergedList.add(t);
		}
		return mergedList;
	}

	
	/* (non-Javadoc)
	 * @see com.apirest.repository.IGenericRepository#findId(java.lang.Class, java.lang.Long)
	 */
	@Override
	public T findId(Class<T> clazz, Long entityID){
		return entityManager.find(clazz, entityID);
	}
	
	public List<T> findAll(Class<T> t) {
		List<T> retorno = new ArrayList<>();
		StringBuilder consulta = new StringBuilder("from "+t.getName()+" t");
		try{
			TypedQuery<T> query = entityManager.createQuery(consulta.toString(), t);
			retorno = query.getResultList();
		}catch (Exception e) {
			logger.error(ExceptionUtils.getRootCauseMessage(e),e);
		}
		return retorno;
	}
	
	@Override
	public T executarQueryUnique(String consulta, Map<String, Object> parametros, Class<T> clazz) {
		try {
			TypedQuery<T> query = entityManager.createQuery(consulta, clazz);
			for (String key : parametros.keySet()) {
				if (parametros.get(key) instanceof Set) {
					query.setParameter(key, (Set) parametros.get(key));
				} else if (parametros.get(key) instanceof List) {
					query.setParameter(key, (List) parametros.get(key));
				} else {
					query.setParameter(key, parametros.get(key));
				}
			}			
			return query.getSingleResult();
		}catch (Exception e) {
			logger.log(Level.ERROR,ExceptionUtils.getRootCauseMessage(e), e);
			return null;
		}
	}

	@Override
	public List<T> executarQuery(String consulta, Map<String, Object> parametros, Class<T> clazz) {
		try {
			TypedQuery<T> query = entityManager.createQuery(consulta, clazz);
			for (String key : parametros.keySet()) {
				if (parametros.get(key) instanceof Set) {
					query.setParameter(key, (Set) parametros.get(key));
				} else if (parametros.get(key) instanceof List) {
					query.setParameter(key, (List) parametros.get(key));
				} else {
					query.setParameter(key, parametros.get(key));
				}
			}
			return query.getResultList();
		} catch (Exception e) {
			logger.log(Level.ERROR,ExceptionUtils.getRootCauseMessage(e), e);
			return Collections.emptyList();
		}
	}

	protected Long getId(T entidade) throws Exception {
		Field[] fields = entidade.getClass().getDeclaredFields();
		for (Field field : fields) {
			Id id = field.getAnnotation(Id.class);
			if (id != null) {
				field.setAccessible(true);
				return field.getLong(entidade);
			}
		}
		return null;
	}
	
	public T getRefencia(Class<T> clazz, Long entityID){
		return this.entityManager.getReference(clazz, entityID);
	}
	
	public EntityManager geEntityManager(){
		return this.entityManager;
	}
}
