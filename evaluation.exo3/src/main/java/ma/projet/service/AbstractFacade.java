package ma.projet.service;

import ma.projet.dao.IDao;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractFacade<T, ID extends Serializable> {

    private final IDao<T, ID> dao;

    public AbstractFacade(IDao<T, ID> dao) {
        this.dao = dao;
    }

    public boolean create(T entity) {
        return dao.create(entity);
    }

    public boolean update(T entity) {
        return dao.update(entity);
    }

    public boolean delete(T entity) {
        return dao.delete(entity);
    }

    public T findById(ID id) {
        return dao.findById(id);
    }

    public List<T> findAll() {
        return dao.findAll();
    }
}
