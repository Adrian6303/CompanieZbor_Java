package ro.mpp2024.domain;

import java.util.Objects;

public class Angajat {
    private int id_angajat;
    private String user;
    private String password;

    public Angajat(int id_angajat, String user, String password) {
        this.id_angajat = id_angajat;
        this.user = user;
        this.password = password;
    }

    public int getId_angajat() {
        return id_angajat;
    }

    public void setId_angajat(int id_angajat) {
        this.id_angajat = id_angajat;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Angajat angajat = (Angajat) o;
        return id_angajat == angajat.id_angajat && Objects.equals(user, angajat.user) && Objects.equals(password, angajat.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_angajat, user, password);
    }
}
