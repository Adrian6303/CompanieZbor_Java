package service;
import domain.*;

import java.sql.Date;
import java.util.List;

public interface IService {


    List<Zbor> findAllZboruri() throws Exception;

    void addBilet(Bilet bilet) throws Exception;

    Angajat findAngajatByUserAndPass(String username,String password) throws Exception;

    List<String> addDestinations() throws Exception;

    List<Zbor> findZboruriByDestinatieAndDate(String destinatie, Date dataplecarii) throws Exception;

    Turist findOrAddTurist(String nume) throws Exception;

    List<Turist> findOrAddTurists(List<String> listaTuristi) throws Exception;

    void updateZbor(Zbor zbor) throws Exception;
}