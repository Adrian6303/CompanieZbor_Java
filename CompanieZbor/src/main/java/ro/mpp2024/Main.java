package ro.mpp2024;

import ro.mpp2024.domain.Angajat;
import ro.mpp2024.domain.Bilet;
import ro.mpp2024.domain.Turist;
import ro.mpp2024.domain.Zbor;
import ro.mpp2024.repository.AngajatRepo;
import ro.mpp2024.repository.BiletRepo;
import ro.mpp2024.repository.TuristRepo;
import ro.mpp2024.repository.ZborRepo;
import ro.mpp2024.service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }


        AngajatRepo angajatRepo=new AngajatRepo(props);
        TuristRepo turistRepo= new TuristRepo(props);
        ZborRepo zborRepo = new ZborRepo(props);
        BiletRepo biletRepo = new BiletRepo(props,angajatRepo,zborRepo,turistRepo);

        Angajat ang = new Angajat("user3","23456");
        //angajatRepo.save(ang);
        //angajatRepo.update(3,ang);


        for(Angajat a:angajatRepo.findAll())
            System.out.println(a);

        for(Turist t:turistRepo.findAll())
            System.out.println(t);

        for(Zbor z:zborRepo.findAll())
            System.out.println(z);

        for(Bilet b:biletRepo.findAll())
            System.out.println(b);

    }



}