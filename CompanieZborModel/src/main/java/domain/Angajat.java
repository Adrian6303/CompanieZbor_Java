package domain;

import java.io.Serializable;
import java.util.Objects;

public class Angajat extends Entity<Integer> implements Serializable {
    private String user;
    private String password;

    public Angajat(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void setID(Integer id){this.id = id;}

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
    public String toString() {
        return "Angajat{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Angajat angajat = (Angajat) o;
        return Objects.equals(user, angajat.user) && Objects.equals(password, angajat.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, password);
    }
}
