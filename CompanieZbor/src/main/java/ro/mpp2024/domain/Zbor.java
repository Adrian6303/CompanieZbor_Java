package ro.mpp2024.domain;

import java.util.Date;
import java.util.Objects;

public class Zbor {
    private int id_zbor;
    private String destinatia;
    private Date data_plecarii;
    private String aeroportul;
    private int nr_locuri;

    public Zbor(int id_zbor, String destinatia, Date data_plecarii, String aeroportul, int nr_locuri) {
        this.id_zbor = id_zbor;
        this.destinatia = destinatia;
        this.data_plecarii = data_plecarii;
        this.aeroportul = aeroportul;
        this.nr_locuri = nr_locuri;
    }

    public int getId_zbor() {
        return id_zbor;
    }

    public void setId_zbor(int id_zbor) {
        this.id_zbor = id_zbor;
    }

    public String getDestinatia() {
        return destinatia;
    }

    public void setDestinatia(String destinatia) {
        this.destinatia = destinatia;
    }

    public Date getData_plecarii() {
        return data_plecarii;
    }

    public void setData_plecarii(Date data_plecarii) {
        this.data_plecarii = data_plecarii;
    }

    public String getAeroportul() {
        return aeroportul;
    }

    public void setAeroportul(String aeroportul) {
        this.aeroportul = aeroportul;
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
        Zbor zbor = (Zbor) o;
        return id_zbor == zbor.id_zbor && nr_locuri == zbor.nr_locuri && Objects.equals(destinatia, zbor.destinatia) && Objects.equals(data_plecarii, zbor.data_plecarii) && Objects.equals(aeroportul, zbor.aeroportul);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_zbor, destinatia, data_plecarii, aeroportul, nr_locuri);
    }
}
