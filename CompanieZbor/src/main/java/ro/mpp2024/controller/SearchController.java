package ro.mpp2024.controller;

import ro.mpp2024.domain.Angajat;
import ro.mpp2024.service.Service;
import ro.mpp2024.utils.Observer;

public class SearchController implements Observer{

    Service service;
    private Angajat angajat;

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void update() {

    }

    public void setAngajat(Angajat angajat) {
        this.angajat = angajat;
    }

}
