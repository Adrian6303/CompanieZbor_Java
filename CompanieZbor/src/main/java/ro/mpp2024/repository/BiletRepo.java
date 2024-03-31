package ro.mpp2024.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.domain.Angajat;
import ro.mpp2024.domain.Bilet;
import ro.mpp2024.domain.Turist;
import ro.mpp2024.domain.Zbor;
import ro.mpp2024.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import ro.mpp2024.repository.ZborRepo;
import ro.mpp2024.repository.TuristRepo;
import ro.mpp2024.repository.AngajatRepo;

public class BiletRepo implements Repository<Integer, Bilet>{

    private JdbcUtils dbUtils;
    ZborRepo zborRepo;
    TuristRepo turistRepo;

    AngajatRepo angajatRepo;
    private static final Logger logger = LogManager.getLogger();

    public BiletRepo(Properties props,AngajatRepo angajatRepo,ZborRepo zborRepo,TuristRepo turistRepo) {
        logger.info("Initializing BiletRepo with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
        this.angajatRepo=angajatRepo;
        this.zborRepo= zborRepo;
        this.turistRepo=turistRepo;

    }
    @Override
    public Bilet findOne(Integer aLong) {
        logger.traceEntry("finding task with id {} ", aLong);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from bilet where id=?")) {
            preparedStatement.setInt(1, aLong);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int angajatId = resultSet.getInt("angajat");
                    Angajat angajat= angajatRepo.findOne(angajatId);
                    int zborId = resultSet.getInt("zbor");
                    Zbor zbor=zborRepo.findOne(zborId);
                    int clientId = resultSet.getInt("client");
                    Turist turist = turistRepo.findOne(clientId);
                    //String listaTuristi = resultSet.getString("listaTuristi");
                    List<Turist> turists= new ArrayList<>();

                    List<String> Turisti = List.of(resultSet.getString("listaTuristi").split(";"));
                    for (String nume:Turisti){
                        Turist t = turistRepo.findTuristByNume(nume);
                        turists.add(t);
                    }
                    String adresaClient = resultSet.getString("adresaClient");
                    int nrLocuri = resultSet.getInt("nrLocuri");
                    Bilet bilet = new Bilet(angajat,zbor,turist,turists,adresaClient,nrLocuri);
                    bilet.setId(id);
                    logger.traceExit(bilet);
                    return bilet;
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
    public List<Bilet> findAll() {

        logger.traceEntry();
        Connection connection = dbUtils.getConnection();
        List<Bilet> bilete = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from bilet")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int angajatId = resultSet.getInt("angajat");
                    Angajat angajat= angajatRepo.findOne(angajatId);
                    int zborId = resultSet.getInt("zbor");
                    Zbor zbor=zborRepo.findOne(zborId);
                    int clientId = resultSet.getInt("client");
                    Turist turist = turistRepo.findOne(clientId);


                    //String listaTuristi = resultSet.getString("listaTuristi");

                    List<Turist> turists= new ArrayList<>();

                    List<String> Turisti = List.of(resultSet.getString("listaTuristi").split(";"));
                    for (String nume:Turisti){
                        Turist t = turistRepo.findTuristByNume(nume);
                        turists.add(t);
                    }


                    String adresaClient = resultSet.getString("adresaClient");
                    int nrLocuri = resultSet.getInt("nrLocuri");
                    Bilet bilet = new Bilet(angajat,zbor,turist,turists,adresaClient,nrLocuri);
                    bilet.setId(id);
                    bilete.add(bilet);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB" + e);
        }
        logger.traceExit(bilete);
        return bilete;

    }

    @Override
    public void save(Bilet entity) {
        logger.traceEntry("saving task {}", entity);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into bilet(zbor,client,listaTuristi,adresaClient,nrLocuri,angajat) values (?,?,?,?,?,?)")) {
            preparedStatement.setInt(1, entity.getZbor().getId());
            preparedStatement.setInt(2, entity.getClient().getId());
            String listaTuristi="";
            for (Turist t:entity.getListaTuristi()){
                listaTuristi+=t.getNume()+";";
            }
            preparedStatement.setString(3, listaTuristi);
            preparedStatement.setString(4, entity.getAdresaClient());
            preparedStatement.setInt(5, entity.getNrLocuri());
            preparedStatement.setInt(6, entity.getAngajat().getId());
            int result = preparedStatement.executeUpdate();
            logger.trace("saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Bilet entity) {
        logger.traceEntry("deleting task {}", entity);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from bilet where id=?")) {
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
    public void update(Integer id, Bilet entity) {
        logger.traceEntry("saving task {}", entity);
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("update bilet set zbor = ?, client=?, listaTuristi=?, adresaClient=?, nrLocuri=?, angajat=? where id=?")) {
            preparedStatement.setInt(1, entity.getZbor().getId());
            preparedStatement.setInt(2, entity.getClient().getId());
            preparedStatement.setString(3, entity.getListaTuristi().toString());
            preparedStatement.setString(4, entity.getAdresaClient());
            preparedStatement.setInt(5, entity.getNrLocuri());
            preparedStatement.setInt(6, entity.getAngajat().getId());
            preparedStatement.setInt(7, id);
            int result = preparedStatement.executeUpdate();
            logger.trace("saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
    }
}
