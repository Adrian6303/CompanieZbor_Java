package domain;

import java.io.Serializable;
import java.util.Objects;

public class Turist extends Entity<Integer> implements Serializable {
    private String nume;

    public Turist(String nume) {
        this.nume = nume;
    }

    public void setID(Integer id){this.id = id;}

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return nume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Turist turist = (Turist) o;
        return Objects.equals(nume, turist.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nume);
    }
}
