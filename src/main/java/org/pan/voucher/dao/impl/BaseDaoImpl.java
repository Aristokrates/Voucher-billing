package org.pan.voucher.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.pan.voucher.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

/**
 *  Base dao impl for all DB objects
 * 
 * @author Pance.Isajeski
 */
@Component
public abstract class BaseDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements BaseDao<T, PK> {

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public BaseDaoImpl() {

        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Autowired
    public void initializeDependencies(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    @Override
    public void save(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public void merge(T entity) {
        getHibernateTemplate().merge(entity);
    }

    @Override
    public void remove(PK id) {
        T existingEntity = findById(id);
        if (existingEntity == null) {
            throw new IllegalArgumentException("Item does not exist");
        }
        getHibernateTemplate().delete(existingEntity);

    }

    @Override
    public T findById(PK id) {
        return getHibernateTemplate().get(getPersistentClass(), id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        return getHibernateTemplate().findByCriteria(createDetachedCriteria());
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    protected DetachedCriteria createDetachedCriteria() {

        return DetachedCriteria.forClass(getPersistentClass());
    }
}
