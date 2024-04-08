package domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Bilet extends Entity<Integer> implements Serializable {
    private Angajat angajat;
    private Zbor zbor;
    private Turist client;
    private List<Turist> listaTuristi;
    private String adresaClient;
    private int nrLocuri;

    public Bilet(Angajat angajat, Zbor zbor, Turist client, List<Turist> listaTuristi, String adresaClient, int nrLocuri) {
        this.angajat = angajat;
        this.zbor = zbor;
        this.client = client;
        this.listaTuristi = listaTuristi;
        this.adresaClient = adresaClient;
        this.nrLocuri = nrLocuri;
    }

    public void setID(Integer id){this.id = id;}

    public Angajat getAngajat() {
        return angajat;
    }

    public void setAngajat(Angajat angajat) {
        this.angajat = angajat;
    }

    public Zbor getZbor() {
        return zbor;
    }

    public void setZbor(Zbor zbor) {
        this.zbor = zbor;
    }

    public Turist getClient() {
        return client;
    }

    public void setClient(Turist client) {
        this.client = client;
    }

    public List<Turist> getListaTuristi() {
        return listaTuristi;
    }

    public void setListaTuristi(List<Turist> listaTuristi) {
        this.listaTuristi = listaTuristi;
    }

    public String getAdresaClient() {
        return adresaClient;
    }

    public void setAdresa_client(String adresaClient) {
        this.adresaClient = adresaClient;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    @Override
    public String toString() {
        return "Bilet{" +
                "angajat=" + angajat +
                ", zbor=" + zbor +
                ", client=" + client +
                ", listaTuristi=" + listaTuristi +
                ", adresaClient='" + adresaClient + '\'' +
                ", nrLocuri=" + nrLocuri +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bilet bilet = (Bilet) o;
        return nrLocuri == bilet.nrLocuri && Objects.equals(angajat, bilet.angajat) && Objects.equals(zbor, bilet.zbor) && Objects.equals(client, bilet.client) && Objects.equals(listaTuristi, bilet.listaTuristi) && Objects.equals(adresaClient, bilet.adresaClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), angajat, zbor, client, listaTuristi, adresaClient, nrLocuri);
    }
}
