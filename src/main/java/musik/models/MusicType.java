package musik.models;

import java.util.ArrayList;
import java.util.List;

public class MusicType {

    private int id;
    private String type;
    private List<Integer> usersId = new ArrayList<>();

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getUsers() {
        return usersId;
    }

    public void setUsers(List<Integer> users) {
        this.usersId = users;
    }

    @Override
    public String toString() {
        return this.getType();
    }

    @Override
    public int hashCode() {
        return this.getId();
    }
}
