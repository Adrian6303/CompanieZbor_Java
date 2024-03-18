package ro.mpp2024.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.domain.Angajat;
import ro.mpp2024.domain.Bilet;
import ro.mpp2024.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class BiletRepo implements Repository<Integer, Bilet>{

    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public BiletRepo(Properties props) {
        logger.info("Initializing BiletRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }
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
