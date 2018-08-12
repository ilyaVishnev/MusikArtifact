package musik.models;

import java.util.List;

public class Role {

    private int id;
    private String role;
    private List<Integer> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsers(List<Integer> users) {
        this.users = users;
    }

    public List<Integer> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return this.getRole();
    }

    @Override
    public int hashCode() {
        return this.getId();
    }
}
