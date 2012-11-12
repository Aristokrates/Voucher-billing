package org.pan.voucher.dao;

import java.io.Serializable;
import java.util.List;

/**
 *  Base Dao for all database accessed
 * 
 * @author Pance.Isajeski
 */
public interface BaseDao<T, PK extends Serializable> {

    void save(T entity);

    void merge(T entity);

    void remove(PK id);

    T findById(PK id);

    List<T> findAll();
}
