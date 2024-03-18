package ro.mpp2024.repository;

import ro.mpp2024.domain.Turist;

import java.util.List;
import java.util.Optional;

public class TuristRepo implements Repository<Integer, Turist>{

    @Override
    public Turist findOne(Integer aLong) {
        return null;
    }

    @Override
    public List<Turist> findAll() {
        return null;
    }

    @Override
    public void save(Turist entity) {

    }

    @Override
    public void delete(Turist entity) {

    }

    @Override
    public void update(Integer id, Turist entity) {

    }
}
