/**
 * Product  : Hiperium Project
 * Architect: Andres Solorzano.
 * Created  : 08-05-2009 - 23:30:00
 * 
 * The contents of this file are copyrighted by Andres Solorzano 
 * and it is protected by the license: "GPL V3." You can find a copy of this 
 * license at: http://www.hiperium.com/about/licence.html
 * 
 * Copyright 2014 Andres Solorzano. All rights reserved.
 * 
 */
package com.hiperium.identity.dao;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import com.hiperium.common.services.logger.HiperiumLogger;

/**
 * Implements the main methods to access data in an Enterprise Application.
 * 
 * @author Andres Solorzano.
 * 
 * @param <T>
 *            Specified the JPA Entity which is used to perform data query.
 * @param <ID>
 *            The primary key type of the JPA entity
 */
public class GenericDAO<T extends Serializable, ID extends Serializable> {
	
	/** The generic primary key object field name. */
	protected static final String PK_OBJECT_NAME = "pk";
	/** The property RETRIEVE_MODE. */
	protected static final String RETRIEVE_MODE = "javax.persistence.cache.retrieveMode";
	/** The property STORE_MODE. */
	protected static final String STORE_MODE = "javax.persistence.cache.storeMode";
	
	/** The property logger. */
	@Inject
	private HiperiumLogger logger;
	
	/** The property entityManager. */
	private EntityManager entityManager;
	
	/** The object class type. */
	protected Class<T> entityClass;
	
	/**
	 * Class constructor.
	 */
	@SuppressWarnings("unchecked")
	protected GenericDAO() {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**
	 * 
	 * @param entity
	 */
	protected void create(T entity) {
		this.logger.debug("create - START");
		this.entityManager.persist(entity);
		this.logger.debug("create - END");
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	protected T update(T entity) {
		this.logger.debug("update - START");
		return this.entityManager.merge(entity);
	}
	
	/**
	 * 
	 * @param entity
	 */
	protected void delete(T entity) {
		this.logger.debug("remove - START");
		this.entityManager.remove(this.entityManager.merge(entity));
		this.logger.debug("remove - END");
	}
	
	/**
	 * 
	 * @param id
	 * @param lock
	 * @param bypassCache
	 * @return
	 */
	protected T findById(ID id, boolean lock, boolean bypassCache) {
		this.logger.debug("findById - START");
		T entity = null;
		if(bypassCache) {
			this.entityManager.setProperty(RETRIEVE_MODE, CacheRetrieveMode.BYPASS);
			this.entityManager.setProperty(STORE_MODE, CacheStoreMode.REFRESH);
			entity = this.entityManager.find(this.entityClass, id);
		} else {
			entity = this.entityManager.find(this.entityClass, id);
		}
		if(lock) {
			this.entityManager.lock(entity, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
		}
		this.logger.debug("findById - END");
		return entity;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	protected T getReference(ID id) {
		return this.entityManager.getReference(this.entityClass, id);
	}
	
	/**
	 * 
	 * @param entity
	 * @param bypassCache
	 * @return
	 */
	protected Long count(T entity, boolean bypassCache) {
		this.logger.debug("count - START");
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<T> root = criteriaQuery.from(this.entityClass);
		criteriaQuery.select(criteriaBuilder.count(root));
		// Create JPA Query
		Query query = this.entityManager.createQuery(criteriaQuery);
		if(bypassCache) {
			this.setBypassCahe(query); 
		}
		// get the query results
		Long result = (Long) query.getSingleResult();
		this.logger.debug("count - END: " + result);
		return result;
	}
	
	/**
	 * 
	 * @param entity
	 * @param bypassCache
	 * @param fieldsToSort
	 * @return
	 */
	protected List<T> find(T entity, boolean bypassCache, String... fieldsToSort) {
		this.logger.debug("find - START");
		CriteriaQuery<T> criteriaQuery = this.configureCriteria(entity, this.entityClass, fieldsToSort);
		TypedQuery<T> q = this.entityManager.createQuery(criteriaQuery);
		if(bypassCache) {
			this.setBypassCahe(q); 
		}
		this.logger.debug("find - END");
		return q.getResultList();
	}
	
	/**
	 * 
	 * @param bypassCache
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<T> findAll(boolean bypassCache) {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
		criteriaQuery.select(criteriaQuery.from(this.entityClass));
		Query query = this.entityManager.createQuery(criteriaQuery);
		if(bypassCache) {
			this.setBypassCahe(query); 
		}
		return query.getResultList();
	}
	
	/**
	 * 
	 * @param query
	 * @param bypassCache
	 * @return
	 */
	protected TypedQuery<T> createQuery(String query, boolean bypassCache) {
		TypedQuery<T> q = this.entityManager.createQuery(query, this.entityClass);
		if(bypassCache) {
			this.setBypassCahe(q); 
		}
		return q;
	}
	
	/**
	 * 
	 * @param query
	 * @param parameterizedClass
	 * @param bypassCache
	 * @return
	 */
	protected Query createQuery(String query, Class<?> parameterizedClass, boolean bypassCache) {
		Query q = this.entityManager.createQuery(query, parameterizedClass);
		if(bypassCache) {
			this.setBypassCahe(q); 
		}
		return q;
	}
	
	/**
	 * 
	 * @param namedQuery
	 * @param bypassCache
	 * @return
	 */
	protected TypedQuery<T> createNamedQuery(String namedQuery, boolean bypassCache) {
		TypedQuery<T> q = this.entityManager.createNamedQuery(namedQuery, this.entityClass);
		if(bypassCache) {
			this.setBypassCahe(q); 
		}
		return q;
	}

	/**
	 * 
	 * @param namedQuery
	 * @param parameterizedClass
	 * @param bypassCache
	 * @return
	 */
	protected <E> TypedQuery<E> createNamedQuery(String namedQuery, Class<E> parameterizedClass, boolean bypassCache) {
		TypedQuery<E> q = this.entityManager.createNamedQuery(namedQuery, parameterizedClass);
		if(bypassCache) {
			this.setBypassCahe(q); 
		}
		return q;
	}
	
	/**
	 * 
	 * @param query
	 * @param parameterizedClass
	 * @param bypassCache
	 * @return
	 */
	protected Query createNativeQuery(String query, Class<?> parameterizedClass, boolean bypassCache) {
		Query q = this.entityManager.createNativeQuery(query, parameterizedClass);
		if(bypassCache) {
			this.setBypassCahe(q); 
		}
		return q;
	}
	
	/**
	 * 
	 */
	protected void flushEntityManager() {
		this.entityManager.flush();
	}
	
	/**
	 * 
	 * @param query
	 */
	private void setBypassCahe(Query query) {
		query.setHint(RETRIEVE_MODE, CacheRetrieveMode.BYPASS);
		query.setHint(STORE_MODE, CacheStoreMode.REFRESH);
	}
	
	/**
	 * Sets the criteria parameters for searching execution.
	 *
	 * @param entity
	 *            the entity with the values for the criteria.
	 * @param fieldsToSort
	 *            the fields to sort the result.
	 */
	private <E> CriteriaQuery<E> configureCriteria(E entity, Class<E> entityClass, String... fieldsToSort) {
		CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<E> root = criteriaQuery.from(entityClass);
		criteriaQuery.select(root);
		this.constructQuery(criteriaBuilder, criteriaQuery, root, entity);
		Order[] orderCriteriaList = null;
		if (fieldsToSort.length > 0) {
			orderCriteriaList = new Order[fieldsToSort.length];
			for (int i = 0; i < fieldsToSort.length; i++) {
				if (fieldsToSort[i].startsWith("A,")) {
					if(fieldsToSort[i].contains(".")) {
						String compositeValue = fieldsToSort[i].substring(2);
						String compositeName = compositeValue.split("\\.")[0];
						String compositeFieldName = compositeValue.split("\\.")[1];
						orderCriteriaList[i] = criteriaBuilder.asc(root.get(compositeName).get(compositeFieldName));
					} else {
						orderCriteriaList[i] = criteriaBuilder.asc(root.get(fieldsToSort[i].substring(2)));
					}
				} else if (fieldsToSort[i].startsWith("D,")) {
					if(fieldsToSort[i].contains(".")) {
						String compositeValue = fieldsToSort[i].substring(2);
						String compositeName = compositeValue.split("\\.")[0];
						String compositeFieldName = compositeValue.split("\\.")[1];
						orderCriteriaList[i] = criteriaBuilder.desc(root.get(compositeName).get(compositeFieldName));
					} else {
						orderCriteriaList[i] = criteriaBuilder.desc(root.get(fieldsToSort[i].substring(2)));
					}
				}
			}
		} else {
			List<String> ids = this.getIdFields(entity);
			orderCriteriaList = new Order[ids.size()];
			int i = 0;
			for (String id : ids) {
				if(id.startsWith(PK_OBJECT_NAME)){
					String compositeFieldName = id.replace(PK_OBJECT_NAME.concat("."), "");
					orderCriteriaList[i] = criteriaBuilder.asc(root.get(PK_OBJECT_NAME).get(compositeFieldName));
				} else {
					orderCriteriaList[i] = criteriaBuilder.asc(root.get(id));
				}
				i = i + 1;
			}
		}
		criteriaQuery.orderBy(orderCriteriaList);
		return criteriaQuery;
	}

	/**
	 * 
	 * @param criteriaBuilder
	 * @param criteriaQuery
	 * @param root
	 * @param entity
	 */
	private <E> void constructQuery(CriteriaBuilder criteriaBuilder, 
			final CriteriaQuery<E> criteriaQuery, Root<E> root, E entity) {
		Map<String, Object> properties = this.getEntityProperties(entity);
		CriteriaFieldConditions criteria = new CriteriaFieldConditions(properties);
		List<Predicate> predicateList = new ArrayList<Predicate>();
		// Add equality conditions conditions
		Set<String> equalityKeys = criteria.equality.keySet();
		for (String key : equalityKeys) {
			predicateList.add(criteriaBuilder.equal(root.get(key), criteria.equality.get(key)));
		}
		// Add like conditions
		Set<String> likeKeys = criteria.like.keySet();
		for (String key : likeKeys) {
			predicateList.add(criteriaBuilder.like(root.<String> get(key), criteria.like.get(key).toString()));
		}
		// Add composite conditions, only with equality conditions
		Set<String> compositeKeys = criteria.composite.keySet();
		for (String key : compositeKeys) {
			Object value = criteria.composite.get(key);
			try {
				if (value.toString().startsWith("class java.util")) {
					continue;
				} else if (StringUtils.containsIgnoreCase(key, PK_OBJECT_NAME)) {
					Map<String, Object> propsComposites = this.getEntityProperties(value, key);
					CriteriaFieldConditions compositeCriteria = new CriteriaFieldConditions(propsComposites);
					if (!compositeCriteria.equality.isEmpty()) {
						Set<String> equalityKeysPk = compositeCriteria.equality.keySet();
						for (String keyPk : equalityKeysPk) {
							String pkValueName = keyPk.replace(PK_OBJECT_NAME.concat("."), "");
							predicateList.add(criteriaBuilder.equal(root.get(PK_OBJECT_NAME).get(pkValueName), 
									compositeCriteria.equality.get(keyPk)));
						}
					}
					if (!compositeCriteria.like.isEmpty()) {
						Set<String> likeKeysPK = compositeCriteria.like.keySet();
						for (String keyPk : likeKeysPK) {
							String pkValueName = keyPk.replace(PK_OBJECT_NAME.concat("."), "");
							Expression<String> expression = root.<String> get(PK_OBJECT_NAME).get(pkValueName);
							predicateList.add(criteriaBuilder.like(expression, compositeCriteria.like.get(keyPk).toString()));
						}
					}
				}
			} catch (RuntimeException e) {
				continue;
			}
		}
		// Adding where stuff is necessary
		if (predicateList.size() == 1) {
			criteriaQuery.where(predicateList.get(0));
		} else if (predicateList.size() > 1){
			Predicate[] predicateCriteria = new Predicate[predicateList.size()];
			predicateCriteria = predicateList.toArray(predicateCriteria);
			criteriaQuery.where(criteriaBuilder.and(predicateCriteria));
		}
	}
	
	/**
	 * Gets the entity properties of the object.
	 * 
	 * @param object
	 *            entity that contains the properties.
	 * @param primaryKey
	 *            the primary key fields.
	 * 
	 * @return map with the properties and values to be used in the construction
	 *         of the criteria object and the searching execution.
	 */
	@SuppressWarnings("unchecked")
	private <E> Map<String, Object> getEntityProperties(Object object, String... primaryKey) {
		Class<E> classType = (Class<E>) object.getClass();
		Field[] fields = classType.getDeclaredFields();
		Map<String, Object> fieldsMap = new HashMap<String, Object>(fields.length);
		for (Field field : fields) {
			Boolean isLob = Boolean.FALSE;
			Annotation[] annotationsArray = field.getAnnotations();
			for (Annotation annotation : annotationsArray) {
				if ("@javax.persistence.Lob()".equals(annotation.toString()) 
						|| "@javax.persistence.Transient()".equals(annotation.toString())) {
					isLob = Boolean.TRUE;
					break;
				}
			}
			if (!isLob) {
				String fieldName = field.getName();
				String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
				try {
					Method method = classType.getMethod(methodName, new Class[] {});
					Object result = method.invoke(object, new Object[] {});
					if (result != null) {
						if (primaryKey != null && primaryKey.length == 1) {
							fieldsMap.put(primaryKey[0] + "." + field.getName(), result);
						} else {
							fieldsMap.put(field.getName(), result);
						}
					}
				} catch (RuntimeException e) {
					continue;
				} catch (NoSuchMethodException e) {
					continue;
				} catch (IllegalAccessException e) {
					continue;
				} catch (InvocationTargetException e) {
					continue;
				}
			}
		}
		return fieldsMap;
	}

	/**
	 * Gets the columns that form the primary key of an entity.
	 * 
	 * @param object
	 *            Entity that contains the primary fields.
	 * @return the list of primary key fields.
	 */
	@SuppressWarnings("unchecked")
	private <E> List<String> getIdFields(Object object) {
		Class<E> classe = (Class<E>) object.getClass();
		Field[] fields = classe.getDeclaredFields();
		List<String> primaryKeys = new ArrayList<String>();
		for (Field field : fields) {
			Annotation[] annotationArray = field.getAnnotations();
			for (Annotation annotation : annotationArray) {
				if ("@javax.persistence.Id()".equals(annotation.toString())) {
					primaryKeys.add(field.getName());
					break;
				} else if ("@javax.persistence.EmbeddedId()".equals(annotation.toString())) {
					String fieldName = field.getName();
					String methodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
					try {
						Method method = classe.getMethod(methodName, new Class[] {});
						Object result = method.invoke(object, new Object[] {});
						Class<E> classe1 = (Class<E>)result.getClass();
						Field[] fields1 = classe1.getDeclaredFields();
						for (Field fieldPK : fields1) {
							if (!"serialVersionUID".equals(fieldPK.getName())) {
								primaryKeys.add(fieldName + "." + fieldPK.getName());
							}
						}
					} catch (RuntimeException e) {
						continue;
					} catch (NoSuchMethodException e) {
						continue;
					} catch (IllegalAccessException e) {
						continue;
					} catch (InvocationTargetException e) {
						continue;
					}
				}
			}
		}
		return primaryKeys;
	}
	
	/**
	 * Inner class to be used like data structure and constructor helper of the
	 * searching conditions.
	 * 
	 * @author Andres Solorzano
	 * 
	 */
	private class CriteriaFieldConditions {

		/** Specified equality conditions. */
		private final Map<String, Object> equality = new HashMap<String, Object>();

		/** Specified like conditions. */
		private final Map<String, Object> like = new HashMap<String, Object>();

		/** Specified composed entity conditions. */
		private final Map<String, Object> composite = new HashMap<String, Object>();

		/**
		 * Inspect entity and the assigned values to each property for the
		 * construction of searching criteria to be applied in the searching
		 * execution.
		 * 
		 * @param entityProperties
		 *            properties map of an entity with the values to be used in
		 *            the searching execution.
		 */
		public CriteriaFieldConditions(Map<String, Object> entityProperties) {
			Set<String> keys = entityProperties.keySet();
			for (String key : keys) {
				Object value = entityProperties.get(key);
				if (value.getClass().toString().startsWith("class java.sql")) {
					continue;
				} else if (value.getClass().toString().startsWith("class java.lang")) {
					if (value.toString().indexOf('%') >= 0) {
						this.like.put(key, value);
					} else if (value.toString().length() > 0) {
						this.equality.put(key, value);
					}
				} else if (value.getClass().toString().startsWith("class java.util.Date") && value instanceof Date) {
					// Dates can only used in equality.
					this.equality.put(key, value);
				} else {
					this.composite.put(key, value);
				}
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * 
	 * @param entityManager
	 */
	protected void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
