package musik.models;

import java.util.*;

public class User {

    private static int count = 0;
    private int id = count++;
    private String name;
    private String login;
    private String password;
    private Address address = new Address();
    private int id_role;
    private List<Integer> id_musicType = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public List<Integer> getId_musicType() {
        return id_musicType;
    }

    public void setId_musicType(List<Integer> id_musicType) {
        this.id_musicType = id_musicType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public int hashCode() {
        return this.getId();
    }
}
