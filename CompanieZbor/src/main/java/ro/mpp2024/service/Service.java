package ro.mpp2024.service;

import ro.mpp2024.domain.Angajat;
import ro.mpp2024.repository.*;

import java.util.List;

public class Service {
    private AngajatRepo angajatRepo;
    private BiletRepo biletRepo;
    private TuristRepo turistRepo;
    private ZborRepo zborRepo;

    public Service(AngajatRepo angajatRepo, BiletRepo biletRepo, TuristRepo turistRepo, ZborRepo zborRepo) {
        this.angajatRepo = angajatRepo;
        this.biletRepo = biletRepo;
        this.turistRepo = turistRepo;
        this.zborRepo = zborRepo;
    }

    public List<Angajat> findAllAngajati(){
        return angajatRepo.findAll();
    }

}
