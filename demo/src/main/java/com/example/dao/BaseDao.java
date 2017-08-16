package com.example.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.exception.ConstraintViolationException;
/*import com.cssiot.cssflow.common.utils.Page;
import com.cssiot.cssflow.common.utils.Parameter;*/

/** * * @description Dao基类接口 * @author athena * @time 2016-02-04 * */
public interface BaseDao<E> {
 /*   public void save(E e);

    public void save(List<E> entityList);

    public void delete(E e) throws ConstraintViolationException, Exception;

    public void deleteByIds(String[] ids) throws ConstraintViolationException, Exception;

    public boolean isExist(String filed, String value) throws Exception;

    public boolean isExist(String filter) throws Exception;

    public E get(Serializable id) throws Exception;

    public List<E> findAll() throws Exception;

    public List<E> findByHql(String hqlString) throws Exception;

    public List<Map<String, Object>> findByHqlMap(String hqlString) throws Exception;

    @SuppressWarnings("rawtypes")
    public List<Map> executeProcedure(final String names, final List params) throws Exception;

    @SuppressWarnings("hiding")
    public <E> Page<E> find(Page<E> page, String hqlString);

    @SuppressWarnings("hiding")
    public <E> Page<E> find(Page<E> page, String hqlString, Parameter parameter);

    @SuppressWarnings("hiding")
    public <E> List<E> find(String hqlString);

    @SuppressWarnings("hiding")
    public <E> List<E> find(String hqlString, Parameter parameter);

    public E getByHql(String hqlString);

    public E getByHql(String hqlString, Parameter parameter);

    public Query createQuery(String hqlString, Parameter parameter);

    @SuppressWarnings("hiding")
    public <E> Page<E> findBySql(Page<E> page, String sqlString);

    @SuppressWarnings("hiding")
    public <E> Page<E> findBySql(Page<E> page, String sqlString, Parameter parameter);

    @SuppressWarnings("hiding")
    public <E> Page<E> findBySql(Page<E> page, String sqlString, Class<?> resultClass);

    @SuppressWarnings("hiding")
    public <E> Page<E> findBySql(Page<E> page, String sqlString, Parameter parameter,
            Class<?> resultClass);

    @SuppressWarnings("hiding")
    public <E> List<E> findBySql(String sqlString);

    @SuppressWarnings("hiding")
    public <E> List<E> findBySql(String sqlString, Parameter parameter);

    @SuppressWarnings("hiding")
    public <E> List<E> findBySql(String sqlString, Parameter parameter, Class<?> resultClass);

    public int updateBySql(String sqlString, Parameter parameter);

    public SQLQuery createSqlQuery(String sqlString, Parameter parameter);*/
}
