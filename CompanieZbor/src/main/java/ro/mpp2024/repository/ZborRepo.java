package ro.mpp2024.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.domain.Angajat;
import ro.mpp2024.domain.Zbor;
import ro.mpp2024.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.*;

public class ZborRepo implements Repository<Integer, Zbor>{

    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public ZborRepo(Properties props) {
        logger.info("Initializing ZborRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Zbor findOne(Integer aLong) {
        logger.traceEntry("finding task with id {} ", aLong);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from zbor where id=?")) {
            preparedStatement.setInt(1, aLong);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String destinatia = resultSet.getString("destinatia");
                    Date dataPlecarii = resultSet.getDate("dataPlecarii");
                    String aeroportul = resultSet.getString("aeroportul");
                    int nrLocuri = resultSet.getInt("nrLocuri");
                    Zbor zbor = new Zbor(destinatia,dataPlecarii,aeroportul,nrLocuri);
                    zbor.setId(aLong);
                    logger.traceExit(zbor);
                    return zbor;
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
    public List<Zbor> findAll() {
        logger.traceEntry();
        Connection connection = dbUtils.getConnection();
        List<Zbor> zboruri = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from zbor")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String destinatia = resultSet.getString("destinatia");
                    Date dataPlecarii = resultSet.getDate("dataPlecarii");
                    String aeroportul = resultSet.getString("aeroportul");
                    int nrLocuri = resultSet.getInt("nrLocuri");
                    Zbor zbor = new Zbor(destinatia,dataPlecarii,aeroportul,nrLocuri);
                    zbor.setId(id);
                    zboruri.add(zbor);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB" + e);
        }
        logger.traceExit(zboruri);
        return zboruri;
    }

    @Override
    public void save(Zbor entity) {
        logger.traceEntry("saving task {}", entity);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into zbor(destinatia, dataPlecarii, aeroportul, nrLocuri) values (?,?,?,?)")) {
            preparedStatement.setString(1, entity.getDestinatia());
            preparedStatement.setDate(2, entity.getDataPlecarii());
            preparedStatement.setString(3, entity.getAeroportul());
            preparedStatement.setInt(4, entity.getNrLocuri());
            int result = preparedStatement.executeUpdate();
            logger.trace("saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Zbor entity) {
        logger.traceEntry("deleting task {}", entity);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from zbor where id=?")) {
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
    public void update(Integer id, Zbor entity) {
        logger.traceEntry("saving task {}", entity);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("update zbor set destinatia = ?, dataPlecarii = ?, aeroportul = ?, nrLocuri=? where id=?")) {
            preparedStatement.setString(1, entity.getDestinatia());
            preparedStatement.setDate(2, entity.getDataPlecarii());
            preparedStatement.setString(3, entity.getAeroportul());
            preparedStatement.setInt(4, entity.getNrLocuri());
            preparedStatement.setInt(5, id);
            int result = preparedStatement.executeUpdate();
            logger.trace("saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
    }

}
