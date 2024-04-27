package service;
import domain.*;

import java.sql.Date;
import java.util.List;

public interface IService {


    List<Zbor> findAllZboruri() throws Exception;

    void addBilet(Bilet bilet) throws Exception;

    Angajat findAngajatByUserAndPass(String username,String password,Observer observer) throws Exception;

    void setObserver(Angajat angajat,Observer obs);

    List<Zbor> findZboruriByDestinatieAndDate(String destinatie, Date dataplecarii) throws Exception;

    Turist findOrAddTurist(String nume) throws Exception;

    void updateZbor(Zbor zbor) throws Exception;
    void Logout(Angajat angajat) throws Exception;
}