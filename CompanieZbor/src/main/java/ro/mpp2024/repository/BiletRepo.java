package ro.mpp2024.repository;

import ro.mpp2024.domain.Bilet;

import java.util.List;
import java.util.Optional;

public class BiletRepo implements Repository<Integer, Bilet>{

    @Override
    public Bilet findOne(Integer aLong) {
        return null;
    }

    @Override
    public List<Bilet> findAll() {
        return null;
    }

    @Override
    public void save(Bilet entity) {

    }

    @Override
    public void delete(Bilet entity) {

    }

    @Override
    public void update(Integer id, Bilet entity) {

    }
}
