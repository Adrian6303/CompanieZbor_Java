package ro.mpp2024.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Zbor extends Entity<Integer>{
    private String destinatia;
    private LocalDate dataPlecarii;
    private String aeroportul;
    private int nrLocuri;

    public Zbor(String destinatia, LocalDate dataPlecarii, String aeroportul, int nrLocuri) {
        this.destinatia = destinatia;
        this.dataPlecarii = dataPlecarii;
        this.aeroportul = aeroportul;
        this.nrLocuri = nrLocuri;
    }

    public void setID(Integer id){this.id = id;}

    public String getDestinatia() {
        return destinatia;
    }

    public void setDestinatia(String destinatia) {
        this.destinatia = destinatia;
    }

    public LocalDate getDataPlecarii() {
        return dataPlecarii;
    }

    public void setDataPlecarii(LocalDate dataPlecarii) {
        this.dataPlecarii = dataPlecarii;
    }

    public String getAeroportul() {
        return aeroportul;
    }

    public void setAeroportul(String aeroportul) {
        this.aeroportul = aeroportul;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Zbor zbor = (Zbor) o;
        return nrLocuri == zbor.nrLocuri && Objects.equals(destinatia, zbor.destinatia) && Objects.equals(dataPlecarii, zbor.dataPlecarii) && Objects.equals(aeroportul, zbor.aeroportul);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), destinatia, dataPlecarii, aeroportul, nrLocuri);
    }
}
