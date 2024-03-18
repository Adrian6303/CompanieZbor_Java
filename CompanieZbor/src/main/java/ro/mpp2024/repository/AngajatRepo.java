package ro.mpp2024.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.domain.Angajat;
import ro.mpp2024.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class AngajatRepo implements Repository<Integer,Angajat>{

    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public AngajatRepo(Properties props) {
        logger.info("Initializing AngajatRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }
    @Override
    public Angajat findOne(Integer id) {
        return null;
    }

    @Override
    public List<Angajat> findAll() {
        logger.traceEntry();
        Connection connection = dbUtils.getConnection();
        List<Angajat> angajati = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from angajat")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String user = resultSet.getString("user");
                    String password = resultSet.getString("password");
                    Angajat angajat = new Angajat(user, password);
                    angajat.setId(id);
                    angajati.add(angajat);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB" + e);
        }
        logger.traceExit(angajati);
        return angajati;
    }

    @Override
    public void save(Angajat entity) {
        logger.traceEntry("saving task {}", entity);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into angajat(user, password) values (?,?)")) {
            preparedStatement.setString(1, entity.getUser());
            preparedStatement.setString(2, entity.getPassword());
            int result = preparedStatement.executeUpdate();
            logger.trace("saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Angajat entity) {

    }

    @Override
    public void update(Integer id,Angajat entity) {
        logger.traceEntry("saving task {}", entity);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("update angajat set user = ?, password=? where id=?")) {
            preparedStatement.setString(1, entity.getUser());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, id);
            int result = preparedStatement.executeUpdate();
            logger.trace("saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
    }
}
