package org.smart_job.dao;

import java.util.List;

// return 0 => flase
// return > 0 => true
public interface GenericDAO <T, ID> {
    T insert(T entity) throws Exception;
    T update(T entity) throws Exception;
    int delete(ID id) throws Exception;
    T findById(ID id) throws Exception;
    List<T> findAll() throws Exception;
}