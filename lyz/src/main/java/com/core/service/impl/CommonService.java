package com.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.Constants;
import com.core.dao.ICommonDao;
import com.core.entity.AbstractModel;
import com.core.pagination.Page;
import com.core.pagination.PageUtil;
import com.core.service.ICommonService;

import java.io.Serializable;
import java.util.List;

@Service("CommonService")
public class CommonService implements ICommonService {
    
    @Autowired
    @Qualifier("CommonHibernateDao")
    private ICommonDao commonDao;


    public <T extends AbstractModel> T save(T model) {
        return commonDao.save(model);
    }

    public <T extends AbstractModel> void saveOrUpdate(T model) {
        commonDao.saveOrUpdate(model);
        
    }
    
    public <T extends AbstractModel> void update(T model) {
        commonDao.update(model);
    }
    
    public <T extends AbstractModel> void merge(T model) {
        commonDao.merge(model);
    }

    public <T extends AbstractModel, PK extends Serializable> void delete(Class<T> entityClass, PK id) {
        commonDao.delete(entityClass, id);
    }

    public <T extends AbstractModel> void deleteObject(T model) {
        commonDao.deleteObject(model);
    }
    
    @Transactional(readOnly=true)
    public <T extends AbstractModel, PK extends Serializable> T get(Class<T> entityClass, PK id) {
        return commonDao.get(entityClass, id);
    }
    
    public <T extends AbstractModel> int countAll(Class<T> entityClass) {
        return commonDao.countAll(entityClass);
    }
    
    public <T extends AbstractModel> List<T> listAll(Class<T> entityClass) {
        return commonDao.listAll(entityClass);
    }
    
    public <T extends AbstractModel> Page<T> listAll(Class<T> entityClass, int pn) {
        return listAll(entityClass, pn, Constants.DEFAULT_PAGE_SIZE);
    }
    
    public <T extends AbstractModel> Page<T> listAll(Class<T> entityClass, int pn, int pageSize) {
        int total = countAll(entityClass);
        List<T> items = commonDao.listAll(entityClass, pn, pageSize);
        return PageUtil.getPage(total, pn, items, pageSize);
    }
}