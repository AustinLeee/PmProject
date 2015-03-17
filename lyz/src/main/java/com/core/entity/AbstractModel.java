package com.core.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.core.service.ICommonService;
import com.core.util.SpringContextUtil;

@SuppressWarnings("serial")
public abstract class AbstractModel implements Serializable {
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public void save() {
        ICommonService commonService = SpringContextUtil.getBean("CommonService");
        commonService.save(this);
    }
    
    public void delete() {
        ICommonService commonService = SpringContextUtil.getBean("CommonService");
        commonService.deleteObject(this);
    }
    
    public void update() {
        ICommonService commonService = SpringContextUtil.getBean("CommonService");
        commonService.update(this);
    }
}