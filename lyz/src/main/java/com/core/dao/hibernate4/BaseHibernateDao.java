package com.core.dao.hibernate4;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import com.core.dao.IBaseDao;
import com.core.pagination.Page;
import com.core.pagination.PageUtil;

import javax.persistence.Id;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class BaseHibernateDao<M extends Serializable, PK extends Serializable> implements IBaseDao<M, PK> {

	protected static final Logger LOGGER = LoggerFactory.getLogger(BaseHibernateDao.class);

	private final Class<M> entityClass;
	private final String HQL_LIST_ALL;
	private final String HQL_OPTIMIZE_NEXT_LIST_ALL;
	private final String HQL_OPTIMIZE_PRE_LIST_ALL;
	private String pkName = null;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public BaseHibernateDao() {
		this.entityClass = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		Field[] fields = this.entityClass.getDeclaredFields();
		for (Field f : fields) {
			if (f.isAnnotationPresent(Id.class)) {
				this.pkName = f.getName();
			}
		}
		Assert.notNull(pkName);
		HQL_LIST_ALL = "from " + this.entityClass.getSimpleName() + " order by " + pkName + " desc";
		HQL_OPTIMIZE_PRE_LIST_ALL = "from " + this.entityClass.getSimpleName() + " where " + pkName + " > ? order by " + pkName + " asc";
		HQL_OPTIMIZE_NEXT_LIST_ALL = "from " + this.entityClass.getSimpleName() + " where " + pkName + " < ? order by " + pkName + " desc";
	}

	@SuppressWarnings("unchecked")
	protected <T> T aggregate(final String hql, final Map<String, Collection<?>> map, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		if (paramlist != null) {
			setParameters(query, paramlist);
			for (Entry<String, Collection<?>> e : map.entrySet()) {
				query.setParameterList(e.getKey(), e.getValue());
			}
		}
		return (T) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	protected <T> T aggregate(final String hql, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);

		return (T) query.uniqueResult();

	}

	@SuppressWarnings("unchecked")
	protected <T> T aggregateByNative(final String natvieSQL, final List<Entry<String, Type>> scalarList, final Object... paramlist) {
		SQLQuery query = getSession().createSQLQuery(natvieSQL);
		if (scalarList != null) {
			for (Entry<String, Type> entity : scalarList) {
				query.addScalar(entity.getKey(), entity.getValue());
			}
		}

		setParameters(query, paramlist);

		Object result = query.uniqueResult();
		return (T) result;
	}

	@Override
	public void clear() {
		getSession().clear();
	}

	@Override
	public int countAll() {
		Criteria criteria = createCriteria();
		return Integer.valueOf(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
	}

	@Override
	public int countAll(Criteria criteria) {
		return Integer.valueOf(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
	}

	private Criteria createCriteria() {
		return getSession().createCriteria(entityClass);
	}

	private Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = createCriteria();
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	@Override
	public void delete(PK id) {
		getSession().delete(this.get(id));
	}

	@Override
	public void deleteAll(Collection<?> entities) {
		if (entities == null)
			return;
		for (Object entity : entities) {
			getSession().delete(entity);
		}
	}

	@Override
	public void deleteObject(M model) {
		getSession().delete(model);
	}

	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	protected int execteBulk(final String hql, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		Object result = query.executeUpdate();
		return result == null ? 0 : ((Integer) result).intValue();
	}

	protected int execteNativeBulk(final String natvieSQL, final Object... paramlist) {
		Query query = getSession().createSQLQuery(natvieSQL);
		setParameters(query, paramlist);
		Object result = query.executeUpdate();
		return result == null ? 0 : ((Integer) result).intValue();
	}

	@Override
	public boolean exists(PK id) {
		return get(id) != null;
	}

	public List<M> findPage(Criteria criteria, int pageNo, int pageSize) {
		criteria.setFirstResult((pageNo - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		return list(criteria);
	}

	@Override
	public void flush() {
		getSession().flush();
	}

	@Override
	@SuppressWarnings("unchecked")
	public M get(PK id) {
		return (M) getSession().get(this.entityClass, id);
	}

	protected long getIdResult(String hql, Object... paramlist) {
		long result = -1;
		List<?> list = listWithoutPnParam(hql, paramlist);
		if (list != null && list.size() > 0) {
			return ((Number) list.get(0)).longValue();
		}
		return result;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<M> list() {
		return createCriteria().list();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> list(ConditionQuery query, OrderBy orderBy, final int pn, final int pageSize) {
		Criteria criteria = getSession().createCriteria(this.entityClass);
		query.build(criteria);
		orderBy.build(criteria);
		int start = PageUtil.getPageStart(pn, pageSize);
		if (start != 0) {
			criteria.setFirstResult(start);
		}
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> list(Criteria criteria) {
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	private List<M> list(Criterion criterion) {
		Criteria criteria = createCriteria();
		criteria.add(criterion);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<M> list(Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	public <T> List<T> list(DetachedCriteria criteria) {
		return list(criteria.getExecutableCriteria(getSession()));
	}

	@SuppressWarnings("unchecked")
	public List<M> list(String orderBy, boolean isAsc) {
		Criteria criteria = createCriteria();
		if (isAsc) {
			criteria.addOrder(Order.asc(orderBy));
		} else {
			criteria.addOrder(Order.desc(orderBy));
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> list(final String hql, final int pn, final int pageSize, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		if (pn > -1 && pageSize > -1) {
			query.setMaxResults(pageSize);
			int start = PageUtil.getPageStart(pn, pageSize);
			if (start != 0) {
				query.setFirstResult(start);
			}
		}
		if (pn < 0) {
			query.setFirstResult(0);
		}
		List<T> results = query.list();
		return results;
	}

	public List<M> list(String propertyName, Object value) {
		Criterion criterion = Restrictions.like(propertyName, "%" + value + "%");
		return list(criterion);
	}

	@Override
	public List<M> listAll(int pn, int pageSize) {
		return list(HQL_LIST_ALL, pn, pageSize);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> listByNative(final String nativeSQL, final int pn, final int pageSize, final List<Entry<String, Class<?>>> entityList, final List<Entry<String, Type>> scalarList, final Object... paramlist) {
		SQLQuery query = getSession().createSQLQuery(nativeSQL);
		if (entityList != null) {
			for (Entry<String, Class<?>> entity : entityList) {
				query.addEntity(entity.getKey(), entity.getValue());
			}
		}
		if (scalarList != null) {
			for (Entry<String, Type> entity : scalarList) {
				query.addScalar(entity.getKey(), entity.getValue());
			}
		}
		setParameters(query, paramlist);
		if (pn > -1 && pageSize > -1) {
			query.setMaxResults(pageSize);
			int start = PageUtil.getPageStart(pn, pageSize);
			if (start != 0) {
				query.setFirstResult(start);
			}
		}
		List<T> result = query.list();
		return result;
	}

	protected List<M> listSelf(final String hql, final int pn, final int pageSize, final Object... paramlist) {
		return this.<M> list(hql, pn, pageSize, paramlist);
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> listWithIn(final String hql, final int start, final int length, final Map<String, Collection<?>> map, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		for (Entry<String, Collection<?>> e : map.entrySet()) {
			query.setParameterList(e.getKey(), e.getValue());
		}
		if (start > -1 && length > -1) {
			query.setMaxResults(length);
			if (start != 0) {
				query.setFirstResult(start);
			}
		}
		List<T> results = query.list();
		return results;
	}

	protected <T> List<T> listWithoutPnParam(final String sql, final Object... paramlist) {
		return list(sql, -1, -1, paramlist);
	}

	@SuppressWarnings("unchecked")
	public M load(PK id) {
		return (M) getSession().load(this.entityClass, id);
	}

	@Override
	public void merge(M model) {
		getSession().merge(model);
	}

	@Override
	public List<M> next(PK pk, int pn, int pageSize) {
		if (pk == null) {
			return list(HQL_LIST_ALL, pn, pageSize);
		}
		return list(HQL_OPTIMIZE_NEXT_LIST_ALL, 1, pageSize, pk);
	}

	@Override
	public List<M> pre(PK pk, int pn, int pageSize) {
		if (pk == null) {
			return list(HQL_LIST_ALL, pn, pageSize);
		}
		List<M> result = list(HQL_OPTIMIZE_PRE_LIST_ALL, 1, pageSize, pk);
		Collections.reverse(result);
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public PK save(M model) {
		return (PK) getSession().save(model);
	}

	@Override
	public void saveOrUpdate(M model) {
		getSession().saveOrUpdate(model);
	}

	protected void setParameters(Query query, Object[] paramlist) {
		if (paramlist != null) {
			for (int i = 0; i < paramlist.length; i++) {
				if (paramlist[i] instanceof Date) {
					query.setTimestamp(i, (Date) paramlist[i]);
				} else {
					query.setParameter(i, paramlist[i]);
				}
			}
		}
	}

	public Page<M> pagedQuery(ConditionQuery conditionQuery, OrderBy orderBy, int pageNo, int pageSize) {
		Assert.isTrue(pageNo >= 1, "pageNO should start from 1");
		Criteria criteria = createCriteria();
		conditionQuery.build(criteria);
		orderBy.build(criteria);
		long totalCount = countAll(criteria);
		if (totalCount < 1) {
			return new Page<M>();
		}
		int startIndex = PageUtil.getPageStart(pageNo, pageSize);
		List<M> list = findPage(criteria, pageSize, pageNo);
		return new Page<M>(startIndex, totalCount, pageSize, list);
	}

	public Page<M> pagedQuery(Criteria criteria, int pageNo, int pageSize) {
		Assert.isTrue(pageNo >= 1, "pageNO should start from 1");
		List<M> list = findPage(criteria, pageNo, pageSize);
		criteria.setFirstResult(0);
		long totalCount = countAll(criteria);
		if (totalCount < 1) {
			return new Page<M>();
		}
		int startIndex = PageUtil.getPageStart(pageNo, pageSize);
		return new Page<M>(startIndex, totalCount, pageSize, list);
	}

	@SuppressWarnings("unchecked")
	public <T> T unique(Criteria criteria) {
		return (T) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public <T> T unique(DetachedCriteria criteria) {
		return (T) unique(criteria.getExecutableCriteria(getSession()));
	}

	@SuppressWarnings("unchecked")
	protected <T> T unique(final String hql, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		return (T) query.setMaxResults(1).uniqueResult();
	}

	public M unique(Criterion... criterions) {
		Criteria criteria = createCriteria(criterions);
		return unique(criteria);
	}

	@SuppressWarnings("unchecked")
	public M unique(String propertyName, Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (M) createCriteria(criterion).uniqueResult();
	}

	@Override
	public void update(M model) {
		getSession().update(model);
	}
}