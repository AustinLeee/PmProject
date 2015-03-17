package com.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;

public interface IBaseDao<M extends Serializable, PK extends Serializable> {
	public void clear();

	public int countAll();

	public int countAll(Criteria criteria);

	public void delete(PK id);
	
	public void deleteAll(Collection<?> entities);

	public void deleteObject(M model);
	
	boolean exists(PK id);

	public void flush();

	public M get(PK id);
	
	public List<M> listAll();
	
	public List<M> listAll(int pn, int pageSize);

	public M load(PK id);

	public void merge(M model);

	public List<M> next(PK pk, int pn, int pageSize);

	public List<M> pre(PK pk, int pn, int pageSize);

	public PK save(M model);

	public void saveOrUpdate(M model);

	public void update(M model);
}
