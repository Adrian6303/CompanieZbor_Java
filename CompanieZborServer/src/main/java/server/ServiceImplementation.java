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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceImplementation implements IService {

    private AngajatRepo angajatRepo;
    private BiletRepo biletRepo;
    private TuristRepo turistRepo;
    private ZborRepo zborRepo;

    private Map<String, Observer> observers;

    private final int defaultThreadsNo=5;
    public ServiceImplementation(AngajatRepo angajatRepo, BiletRepo biletRepo, TuristRepo turistRepo, ZborRepo zborRepo) {
        this.angajatRepo = angajatRepo;
        this.biletRepo = biletRepo;
        this.turistRepo = turistRepo;
        this.zborRepo = zborRepo;
        observers = new ConcurrentHashMap<>();
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

    public void setObserver(Angajat angajat,Observer observer)
    {
        for (Map.Entry<String, Observer> entry : observers.entrySet()) {
            if (entry.getKey().equals(angajat.getUser())) {
                observers.put(angajat.getUser(), observer);
                return;
            }
        }
        //observers.put(angajat.getUser(),observer);
    }
    public Angajat findAngajatByUserAndPass(String username,String password,Observer observer) throws Exception{
        Angajat angajat = angajatRepo.findAngajatByUserAndPass(username,password);
        if (angajat != null) {
            observers.put(username,observer);
            return angajat;
        } else {
            throw new Exception("Angajatul nu exista");
        }
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
            turist=turistRepo.findTuristByNume(nume);
        }

        return turist;
    }

    public void updateZbor(Zbor zbor) throws Exception {
        zborRepo.update(zbor.getId(),zbor);
        notifyall(zbor);
    }

    @Override
    public void Logout(Angajat angajat) {
        Iterator<Map.Entry<String, Observer>> iterator = observers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Observer> entry = iterator.next();
            if (entry.getKey().equals(angajat.getUser())) {
                iterator.remove();
                break;
            }
        }

    }

    public void notifyall(Zbor zbor)
    {
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);

        for(var obs:observers.values())
        {
            if(obs==null)
                continue;
            executor.execute(()->{
                try{
                    obs.updateZbor(zbor);

                }catch (Exception ex)
                {
                    throw new RuntimeException(ex);
                }
            });

        }
    }
}
