package musik.dao;

import musik.models.Address;
import musik.models.Role;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AddressDao {
    private PGPoolingDataSource source = DBStore.getDBStore().getDataSource();
    private static Logger log = Logger.getLogger(RoleDao.class.getName());

    public void create(Address address) {
        try (Connection connection = source.getConnection();
             PreparedStatement stmt = connection.prepareStatement(String.format("insert into adress (id,adress,id_user) values(%d,'%s',%d)", address.getId(), address.getAddress(), address.getIdUser()));
        ) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
    }

    public void edit(Address address) {
        try (Connection connection = source.getConnection();
             PreparedStatement stmt = connection.prepareStatement(String.format("update adress set adress='%s',id_user='%d' where id = '%d'", address.getAddress(), address.getIdUser(), address.getId()));
        ) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
    }

    public void delete(Address address) {
        try (Connection connection = source.getConnection();
             PreparedStatement stmt = connection.prepareStatement(String.format("delete from adress where id = %d", address.getId()));
        ) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
    }

    public Address getAddressById(int index) {
        Address address = new Address();
        try (Connection connection = source.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(String.format("select * from adress where adress.id=%d", index));
             ResultSet resultSet = pstmt.executeQuery();
        ) {
            while (resultSet.next()) {
                address.setId(index);
                address.setAddress(resultSet.getString(2));
                address.setIdUser(resultSet.getInt(3));
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return address;
    }

    public List getAll() {
        List<Address> addressList = new ArrayList<>();
        try (Connection connection = source.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("select id from adress");
             ResultSet resultSet = pstmt.executeQuery();) {
            while (resultSet.next()) {
                addressList.add(this.getAddressById(resultSet.getInt(1)));
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return addressList;
    }
}
