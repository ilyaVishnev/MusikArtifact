package musik.models;

public class Address {

    private static int count = 0;
    private int id = count++;
    private String address;
    private int idUser;

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getAddress();
    }

    @Override
    public int hashCode() {
        return this.getId();
    }
}
