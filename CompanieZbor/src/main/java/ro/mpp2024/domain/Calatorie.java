package ro.mpp2024.domain;

import java.util.Objects;

public class Calatorie {
    private int id_turist;
    private int id_bilet;

    public Calatorie(int id_turist, int id_bilet) {
        this.id_turist = id_turist;
        this.id_bilet = id_bilet;
    }

    public int getId_turist() {
        return id_turist;
    }

    public void setId_turist(int id_turist) {
        this.id_turist = id_turist;
    }

    public int getId_bilet() {
        return id_bilet;
    }

    public void setId_bilet(int id_bilet) {
        this.id_bilet = id_bilet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calatorie calatorie = (Calatorie) o;
        return id_turist == calatorie.id_turist && id_bilet == calatorie.id_bilet;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_turist, id_bilet);
    }
}
