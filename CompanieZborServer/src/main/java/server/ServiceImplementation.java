package server;

import domain.Angajat;
import domain.Bilet;
import domain.Turist;
import domain.Zbor;
import repository.AngajatRepo;
import repository.BiletRepo;
import repository.TuristRepo;
import repository.ZborRepo;
import service.IService;
import service.Observer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceImplementation implements IService {

    private AngajatRepo angajatRepo;
    private BiletRepo biletRepo;
    private TuristRepo turistRepo;
    private ZborRepo zborRepo;

    private Map<Angajat, Observer> observers;

    public ServiceImplementation(AngajatRepo angajatRepo, BiletRepo biletRepo, TuristRepo turistRepo, ZborRepo zborRepo) {
        this.angajatRepo = angajatRepo;
        this.biletRepo = biletRepo;
        this.turistRepo = turistRepo;
        this.zborRepo = zborRepo;
    }


    public List<Zbor> findAllZboruri(){
        List<Zbor> zboruri = zborRepo.findAll();
        for(Zbor zbor:zboruri){
            if(zbor.getNrLocuri() == 0){
                zborRepo.delete(zbor);
            }
        }
        return zboruri;
    }


    public void addBilet(Bilet bilet){
        biletRepo.save(bilet);
    }

    public Angajat findAngajatByUserAndPass(String username,String password){
        return angajatRepo.findAngajatByUserAndPass(username,password);
    }

    public List<String> addDestinations(){
        List<Zbor> zboruri = zborRepo.findAll();
        List<String> destinatii = new ArrayList<>();
        for(Zbor zbor:zboruri){
            if(!destinatii.contains(zbor.getDestinatia())){
                destinatii.add(zbor.getDestinatia());
            }
        }
        return destinatii;
    }

    public List<Zbor> findZboruriByDestinatieAndDate(String destinatie, Date dataplecarii){
        List<Zbor> zboruri = zborRepo.findAll();
        List<Zbor> zboruriFiltrate = new ArrayList<>();
        for(Zbor zbor:zboruri){
            if(zbor.getDestinatia().equals(destinatie) && zbor.getDataPlecarii().toString().equals(dataplecarii.toString()) && zbor.getNrLocuri()>0){
                zboruriFiltrate.add(zbor);
            }
        }
        return zboruriFiltrate;

    }

    public Turist findOrAddTurist(String nume){
        Turist turist = turistRepo.findTuristByNume(nume);
        if(turist == null){
            turist = new Turist(nume);
            turistRepo.save(turist);
        }
        return turist;
    }
    public List<Turist> findOrAddTurists(List<String> listaTuristi){
        List<Turist> turisti = new ArrayList<>();
        for (String nume:listaTuristi){
            Turist turist = turistRepo.findTuristByNume(nume);
            if(turist == null){
                turist = new Turist(nume);
                turistRepo.save(turist);
                turisti.add(turist);
            }
            else {
                turisti.add(turist);
            }

        }
        return turisti;
    }
    public void updateZbor(Zbor zbor){
        zborRepo.update(zbor.getId(),zbor);
    }


}
