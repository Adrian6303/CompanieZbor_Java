package ro.mpp2024.domain;

import java.util.Objects;

public class Turist {
    private int id_turist;
    private String nume;

    public Turist(int id_turist, String nume) {
        this.id_turist = id_turist;
        this.nume = nume;
    }

    public int getId_turist() {
        return id_turist;
    }

    public void setId_turist(int id_turist) {
        this.id_turist = id_turist;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turist turist = (Turist) o;
        return id_turist == turist.id_turist && Objects.equals(nume, turist.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_turist, nume);
    }
}
