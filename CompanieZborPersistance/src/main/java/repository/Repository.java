package repository;


import domain.Entity;

import java.util.List;

public interface Repository<ID, E  extends Entity<ID>> {

    E findOne(ID id);

    List<E> findAll();

    void save(E entity);

    void delete(E entity);

    void update(ID id,E entity);

}