package ro.mpp2024.service;

import org.checkerframework.checker.units.qual.A;
import ro.mpp2024.domain.Angajat;
import ro.mpp2024.repository.*;
import ro.mpp2024.domain.*;
import ro.mpp2024.utils.Observable;
import ro.mpp2024.utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class Service implements Observable{
    private AngajatRepo angajatRepo;
    private BiletRepo biletRepo;
    private TuristRepo turistRepo;
    private ZborRepo zborRepo;
    private List<Observer> observers = new ArrayList<>();

    public Service(AngajatRepo angajatRepo, BiletRepo biletRepo, TuristRepo turistRepo, ZborRepo zborRepo) {
        this.angajatRepo = angajatRepo;
        this.biletRepo = biletRepo;
        this.turistRepo = turistRepo;
        this.zborRepo = zborRepo;
    }

    public List<Angajat> findAllAngajati(){
        return angajatRepo.findAll();
    }

    public List<Bilet> findAllBilete(){
        return biletRepo.findAll();
    }

    public List<Turist> findAllTuristi(){
        return turistRepo.findAll();
    }

    public List<Zbor> findAllZboruri(){
        return zborRepo.findAll();
    }

    public void addAngajat(Angajat angajat){
        angajatRepo.save(angajat);
    }

    public void addBilet(Bilet bilet){
        biletRepo.save(bilet);
    }

    public void addTurist(Turist turist){
        turistRepo.save(turist);
    }

    public void addZbor(Zbor zbor){
        zborRepo.save(zbor);
    }

    public void deleteAngajat(Angajat angajat){
        angajatRepo.delete(angajat);
    }
    public void deleteBilet(Bilet bilet){
        biletRepo.delete(bilet);
    }
    public void deleteTurist(Turist turist){
        turistRepo.delete(turist);
    }
    public void deleteZbor(Zbor zbor){
        zborRepo.delete(zbor);
    }
    public Angajat findAngajatById(Integer id){
        return angajatRepo.findOne(id);
    }
    public Angajat findAngajatByUserAndPass(String username,String password){
        return angajatRepo.findAngajatByUserAndPass(username,password);
    }
    public Bilet findBiletById(Integer id){
        return biletRepo.findOne(id);
    }
    public Turist findTuristById(Integer id){
        return turistRepo.findOne(id);
    }
    public Zbor findZborById(Integer id){
        return zborRepo.findOne(id);
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

}
