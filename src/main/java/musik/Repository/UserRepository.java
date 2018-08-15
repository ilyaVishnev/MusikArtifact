package musik.Repository;

import musik.dao.DBStore;
import musik.dao.UserDao;
import musik.models.Address;
import musik.models.MusicType;
import musik.models.Role;
import musik.models.User;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class UserRepository {


    private PGPoolingDataSource source = DBStore.getDBStore().getDataSource();
    private static Logger log = Logger.getLogger(UserDao.class.getName());

    // операцию получения всех связанных с ним сущностей
    public List<Object> getListOfEntity(User user) {
        List<Object> objectList = new ArrayList<>();
        objectList.add(this.getAddress(user));
        objectList.add(this.getRole(user));
        objectList.add(this.getMusicType(user));
        return objectList;

    }

    //операцию добавления нового User

    public void add(User user) {
        try (Connection connection = source.getConnection();
             Statement stmt = connection.createStatement();
        ) {
            connection.setAutoCommit(false);
            stmt.addBatch(String.format("insert into users (id,login,password,name,id_roles) values(%d,'%s','%s','%s',%d)", user.getId(), user.getLogin(), user.getPassword(), user.getName(), user.getId_role()));
            stmt.addBatch(String.format("insert into adress (id,adress,id_user) values(%d,'%s',%d)", user.getAddress().getId(), user.getAddress().getAddress(), user.getId()));
            Iterator<Integer> iterator = user.getId_musicType().iterator();
            while (iterator.hasNext()) {
                stmt.addBatch(String.format("insert into userstypes (id_type,id_user) values(%d,%d)", iterator.next(), user.getId()));
            }
            stmt.executeBatch();
            connection.commit();
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
    }

    public List<MusicType> getMusicType(User user) {
        List<MusicType> musicTypeList = new ArrayList<>();
        Iterator<Integer> iterator = this.getListIdTypes(user).iterator();
        while (iterator.hasNext()) {
            int id = iterator.next();
            MusicType musicType = new MusicType();
            try (Connection connection = source.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(String.format("select * from musictypes where id=%d", id));
                 PreparedStatement pstmt2 = connection.prepareStatement(String.format("select id_user from userstypes where id_type=%d", id));
                 ResultSet resultSet = pstmt.executeQuery();
                 ResultSet resultSet2 = pstmt2.executeQuery();
            ) {
                while (resultSet.next()) {
                    musicType.setId(resultSet.getInt(1));
                    musicType.setType(resultSet.getString(2));
                }
                while (resultSet2.next()) {
                    musicType.getUsers().add(resultSet2.getInt(1));
                }
            } catch (SQLException ex) {
                log.info(ex.getMessage());
            }
            musicTypeList.add(musicType);
        }
        return musicTypeList;
    }

//операцию поиска User по заданному параметру (Address)

    public User getUserbyAddress(Address address) {
        return this.getUserById(address.getIdUser());
    }

    // операцию поиска User по заданному параметру (Role)

    public List<User> getUserbyRole(Role role) {
        List<User> userList = new ArrayList<>();
        User user = null;
        try (Connection connection = source.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(String.format("select id from users where id_roles=%d", role.getId()));
             ResultSet resultSet = pstmt.executeQuery();
        ) {
            while (resultSet.next()) {
                user = this.getUserById(resultSet.getInt(1));
                userList.add(user);
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return userList;
    }

    //операцию поиска User по заданному параметру (MusicType)

    public List<User> getUserbyType(MusicType musicType) {
        List<User> userList = new ArrayList<>();
        User user = null;
        try (Connection connection = source.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(String.format("select id_user from userstypes where id_type=%d", musicType.getId()));
             ResultSet resultSet = pstmt.executeQuery();
        ) {
            while (resultSet.next()) {
                user = this.getUserById(resultSet.getInt(1));
                userList.add(user);
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return userList;
    }

    public List<Integer> getListIdTypes(User user) {
        List<Integer> listIdTypes = new ArrayList<>();
        try (Connection connection = source.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(String.format("select id_type from userstypes where id_user=%d", user.getId()));
             ResultSet resultSet = pstmt.executeQuery();
        ) {
            while (resultSet.next()) {
                listIdTypes.add(resultSet.getInt(1));
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return listIdTypes;
    }

    public User getUserById(int index) {
        User user = new User();
        try (Connection connection = source.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(String.format("select users.login,users.password,users.name,adress.id,adress.adress,users.id_roles from users inner join adress on users.id=adress.id_user where users.id=%d", index));
             PreparedStatement pstmt2 = connection.prepareStatement(String.format("select id_type from userstypes where id_user=%d", index));
             ResultSet resultSet = pstmt.executeQuery();
             ResultSet resultSet2 = pstmt2.executeQuery();
        ) {
            while (resultSet.next()) {
                user.setId(index);
                user.setLogin(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                Address address = new Address();
                address.setId(resultSet.getInt(4));
                address.setAddress(resultSet.getString(5));
                user.setAddress(address);
                user.setId_role(resultSet.getInt(6));
            }
            while (resultSet2.next()) {
                user.getId_musicType().add(resultSet2.getInt(1));
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return user;
    }

    public Address getAddress(User user) {
        Address address = new Address();
        try (Connection connection = source.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(String.format("select * from adress where id_user=%d", user.getId()));
             ResultSet resultSet = pstmt.executeQuery();
        ) {
            while (resultSet.next()) {
                address.setId(resultSet.getInt(1));
                address.setAddress(resultSet.getString(2));
                address.setIdUser(resultSet.getInt(3));
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return address;
    }

    public Role getRole(User user) {
        Role role = new Role();
        try (Connection connection = source.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(String.format("select * from roles where id=(select id_roles from users where id =%d)", user.getId()));
             PreparedStatement pstmt2 = connection.prepareStatement(String.format("select id from users where id_roles=(select id_roles from users where id =%d)", user.getId()));
             ResultSet resultSet = pstmt.executeQuery();
             ResultSet resultSet2 = pstmt2.executeQuery();
        ) {
            while (resultSet.next()) {
                role.setId(resultSet.getInt(1));
                role.setRole(resultSet.getString(2));
            }
            while (resultSet2.next()) {
                role.getUsers().add(resultSet2.getInt(1));
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return role;
    }
}
