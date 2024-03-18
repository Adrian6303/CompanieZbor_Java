package ro.mpp2024.repository;

import ro.mpp2024.domain.Zbor;

import java.util.List;
import java.util.Optional;

public class ZborRepo implements Repository<Integer, Zbor>{

    @Override
    public Zbor findOne(Integer aLong) {
        return null;
    }

    @Override
    public List<Zbor> findAll() {
        return null;
    }

    @Override
    public void save(Zbor entity) {

    }

    @Override
    public void delete(Zbor entity) {

    }

    @Override
    public void update(Integer id, Zbor entity) {

    }
}
