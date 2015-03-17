package com.core.pagination;

import javax.persistence.Id;

import com.core.util.KeySynchronizer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class PageUtil {
    
    /**
     * 获取主键时缓存
     */
    private static Map<Class<?>, Field> classPKMap = new WeakHashMap<Class<?>, Field>();
    
    /**
     * 不关心总记录数
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static int getPageStart(int pageNumber, int pageSize) {
        return (pageNumber - 1) * pageSize;
    }
    
    /**
     * 计算分页获取数据时游标的起始位置
     * 
     * @param totalCount 所有记录总和
     * @param pageNumber 页码,从1开始
     * @return
     */
    public static int getPageStart(int totalCount, int pageNumber, int pageSize) {
        int start = (pageNumber - 1) * pageSize;
        if (start >= totalCount) {
            start = 0;
        }

        return start;
    }

    /**
     * 构造分页对象
     * 
     * @param totalCount 满足条件的所有记录总和
     * @param pageNumber 本次分页的页码
     * @param items
     * @return
     */
    public static <E> Page<E> getPage(int totalCount, int pageNumber, List<E> items, int pageSize) {
        IPageContext<E> pageContext = new QuickPageContext<E>(totalCount, pageSize, items);
        return pageContext.getPage(pageNumber);
    }
    
    public static Field getPkField(Class<?> cls) {
        Field pkField = classPKMap.get(cls);
        if(pkField == null) {
            synchronized (KeySynchronizer.acquire(cls)) {
                Field[] fields = cls.getDeclaredFields();
                for(Field field : fields) {
                    if(field.isAnnotationPresent(Id.class)) {
                        pkField = field;
                        pkField.setAccessible(true);
                        classPKMap.put(cls, pkField);
                    }
                }
            }
        }
        if(pkField == null) {
        }
        return pkField;
    }
    
    public static <T> String getIdValue(T obj) {
        if(obj == null) {
            return "";
        }
        String retVal = "";
        Field pkField = getPkField(obj.getClass());
        try {
            retVal = pkField.get(obj).toString();
        } catch (Exception e) {
        }
        return retVal;
    }
    public static <T> String getIdName(T obj) {
        if(obj == null) {
            return "";
        }
        String retVal = "";
        Field pkField = getPkField(obj.getClass());
        try {
            retVal = pkField.getName();
        } catch (Exception e) {
        }
        return retVal;
    }
}
