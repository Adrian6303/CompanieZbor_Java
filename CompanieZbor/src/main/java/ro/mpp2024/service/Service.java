package ro.mpp2024.service;

import ro.mpp2024.repository.*;

public class Service {
    private AngajatRepo angajatRepo;
    private BiletRepo biletRepo;
    private CalatorieRepo calatorieRepo;
    private TuristRepo turistRepo;
    private ZborRepo zborRepo;

    public Service(AngajatRepo angajatRepo, BiletRepo biletRepo, CalatorieRepo calatorieRepo, TuristRepo turistRepo, ZborRepo zborRepo) {
        this.angajatRepo = angajatRepo;
        this.biletRepo = biletRepo;
        this.calatorieRepo = calatorieRepo;
        this.turistRepo = turistRepo;
        this.zborRepo = zborRepo;
    }

}
