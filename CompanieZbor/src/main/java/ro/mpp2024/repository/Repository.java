package ro.mpp2024.repository;


import java.util.Optional;

public interface Repository<ID, E> {

    Optional<E> findOne(ID id);

    Iterable<E> findAll();

    Optional<E> save(E entity);

    Optional<E> delete(E entity);

    Optional<E> update(E entity);

}