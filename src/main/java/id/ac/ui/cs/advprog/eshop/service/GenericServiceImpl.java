package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.repository.GenericRepository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class GenericServiceImpl<T> implements GenericService<T> {
    protected GenericRepository<T> repository;

    public GenericServiceImpl(GenericRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public T create(T entity) {
        return repository.create(entity);
    }

    @Override
    public List<T> findAll() {
        Iterator<T> it = repository.findAll();
        List<T> list = new ArrayList<>();
        it.forEachRemaining(list::add);
        return list;
    }

    @Override
    public T findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void update(String id, T entity) {
        repository.update(id, entity);
    }

    @Override
    public void deleteById(String id) {
        repository.delete(id);
    }
}
