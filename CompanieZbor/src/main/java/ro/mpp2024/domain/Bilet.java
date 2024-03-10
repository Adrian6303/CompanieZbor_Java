package ro.mpp2024.domain;

import java.util.List;
import java.util.Objects;

public class Bilet {
    private int id_bilet;
    private int id_angajat;
    private int id_zbor;
    private int id_client;
    private List<String> nume_turistii;
    private String adresa_client;
    private int nr_locuri;

    public Bilet(int id_bilet, int id_angajat, int id_zbor, int id_client, List<String> nume_turistii, String adresa_client, int nr_locuri) {
        this.id_bilet = id_bilet;
        this.id_angajat = id_angajat;
        this.id_zbor = id_zbor;
        this.id_client = id_client;
        this.nume_turistii = nume_turistii;
        this.adresa_client = adresa_client;
        this.nr_locuri = nr_locuri;
    }

    public int getId_bilet() {
        return id_bilet;
    }

    public void setId_bilet(int id_bilet) {
        this.id_bilet = id_bilet;
    }

    public int getId_angajat() {
        return id_angajat;
    }

    public void setId_angajat(int id_angajat) {
        this.id_angajat = id_angajat;
    }

    public int getId_zbor() {
        return id_zbor;
    }

    public void setId_zbor(int id_zbor) {
        this.id_zbor = id_zbor;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public List<String> getNume_turistii() {
        return nume_turistii;
    }

    public void setNume_turistii(List<String> nume_turistii) {
        this.nume_turistii = nume_turistii;
    }

    public String getAdresa_client() {
        return adresa_client;
    }

    public void setAdresa_client(String adresa_client) {
        this.adresa_client = adresa_client;
    }

    public int getNr_locuri() {
        return nr_locuri;
    }

    public void setNr_locuri(int nr_locuri) {
        this.nr_locuri = nr_locuri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bilet bilet = (Bilet) o;
        return id_bilet == bilet.id_bilet && id_angajat == bilet.id_angajat && id_zbor == bilet.id_zbor && id_client == bilet.id_client && nr_locuri == bilet.nr_locuri && Objects.equals(nume_turistii, bilet.nume_turistii) && Objects.equals(adresa_client, bilet.adresa_client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_bilet, id_angajat, id_zbor, id_client, nume_turistii, adresa_client, nr_locuri);
    }
}
