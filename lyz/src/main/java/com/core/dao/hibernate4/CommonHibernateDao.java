package com.core.dao.hibernate4;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.core.Constants;
import com.core.dao.ICommonDao;
import com.core.entity.AbstractModel;
import com.core.pagination.PageUtil;

import java.io.Serializable;
import java.util.List;

@Component("CommonHibernateDao")
public class CommonHibernateDao implements ICommonDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public <T extends AbstractModel> T save(T model) {
		getSession().save(model);
		return model;
	}

	public <T extends AbstractModel> void saveOrUpdate(T model) {
		getSession().saveOrUpdate(model);

	}

	public <T extends AbstractModel> void update(T model) {
		getSession().update(model);
	}

	public <T extends AbstractModel> void merge(T model) {
		getSession().merge(model);
	}

	public <T extends AbstractModel, PK extends Serializable> void delete(Class<T> entityClass, PK id) {
		getSession().delete(get(entityClass, id));
	}

	public <T extends AbstractModel> void deleteObject(T model) {
		getSession().delete(model);
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractModel, PK extends Serializable> T get(Class<T> entityClass, PK id) {
		return (T) getSession().get(entityClass, id);
	}

	public <T extends AbstractModel> int countAll(Class<T> entityClass) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractModel> List<T> listAll(Class<T> entityClass) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.setProjection(Projections.rowCount());
		return criteria.list();
	}

	public <T extends AbstractModel> List<T> listAll(Class<T> entityClass, int pn) {
		return listAll(entityClass, pn, Constants.DEFAULT_PAGE_SIZE);
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractModel> List<T> listAll(Class<T> entityClass, int pn, int pageSize) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.setFirstResult(PageUtil.getPageStart(pn, pageSize));
		return criteria.list();
	}
}