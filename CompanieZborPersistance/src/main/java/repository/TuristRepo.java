package repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import domain.Turist;
import repository.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TuristRepo implements Repository<Integer, Turist>{

    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public TuristRepo(Properties props) {
        logger.info("Initializing TuristRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Turist findOne(Integer aLong) {
        logger.traceEntry("finding task with id {} ", aLong);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from turist where id=?")) {
            preparedStatement.setInt(1, aLong);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nume = resultSet.getString("nume");
                    Turist turist = new Turist(nume);
                    turist.setId(aLong);
                    logger.traceExit(turist);
                    return turist;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public List<Turist> findAll() {
        logger.traceEntry();
        Connection connection = dbUtils.getConnection();
        List<Turist> turisti = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from turist")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nume = resultSet.getString("nume");
                    Turist turist = new Turist(nume);
                    turist.setId(id);
                    turisti.add(turist);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB" + e);
        }
        logger.traceExit(turisti);
        return turisti;

    }

    @Override
    public void save(Turist entity) {
        logger.traceEntry("saving task {}", entity);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into turist(nume) values (?)")) {
            preparedStatement.setString(1, entity.getNume());
            int result = preparedStatement.executeUpdate();
            logger.trace("saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();

    }

    @Override
    public void delete(Turist entity) {
        logger.traceEntry("deleting task {}", entity);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from turist where id=?")) {
            preparedStatement.setInt(1, entity.getId());
            int result = preparedStatement.executeUpdate();
            logger.trace("deleted {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();

    }

    @Override
    public void update(Integer id, Turist entity) {
        logger.traceEntry("saving task {}", entity);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("update turist set nume = ? where id=?")) {
            preparedStatement.setString(1, entity.getNume());
            preparedStatement.setInt(2, id);
            int result = preparedStatement.executeUpdate();
            logger.trace("saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
    }

    public Turist findTuristByNume(String nume) {
        logger.traceEntry("finding task with nume {} ", nume);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from turist where nume=?")) {
            preparedStatement.setString(1, nume);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    Turist turist = new Turist(nume);
                    turist.setId(id);
                    logger.traceExit(turist);
                    return turist;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit();
        return null;
    }
}
