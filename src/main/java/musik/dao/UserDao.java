package musik.dao;

import org.postgresql.ds.PGPoolingDataSource;
import musik.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class UserDao {

    private PGPoolingDataSource source = DBStore.getDBStore().getDataSource();
    private static Logger log = Logger.getLogger(UserDao.class.getName());

    public void create(User user) {
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

    public void edit(User user) {
        try (Connection connection = source.getConnection();
             Statement stmt = connection.createStatement();
        ) {
            connection.setAutoCommit(false);
            stmt.addBatch(String.format("update users set login='%s', password='%s',name='%s', id_roles='%d' where id = '%d'", user.getLogin(), user.getPassword(), user.getName(), user.getId_role(), user.getId()));
            stmt.addBatch(String.format("update adress set adress='%s',id_user='%d' where id = '%d'", user.getAddress().getAddress(), user.getId(), user.getAddress().getId()));
            stmt.addBatch(String.format("delete from userstypes where id_user = '%d'", user.getId()));
            Iterator<Integer> iterator = user.getId_musicType().iterator();
            while (iterator.hasNext()) {
                stmt.addBatch(String.format("insert into userstypes (id_type,id_user) values('%d','%d')", iterator.next(), user.getId()));
            }
            stmt.executeBatch();
            connection.commit();
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
    }

    public void delete(User user) {
        try (Connection connection = source.getConnection();
             Statement stmt = connection.createStatement();
        ) {
            connection.setAutoCommit(false);
            stmt.addBatch(String.format("delete from users where id = %d", user.getId()));
            stmt.addBatch(String.format("delete from adress where id_user = %d", user.getId()));
            stmt.addBatch(String.format("delete from userstypes where id_user = %d", user.getId()));
            stmt.executeBatch();
            connection.commit();
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
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

    public List getAll() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = source.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("select id from users");
             ResultSet resultSet = pstmt.executeQuery();) {
            while (resultSet.next()) {
                userList.add(this.getUserById(resultSet.getInt(1)));
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return userList;
    }

    public User isCredential(String login, String password) {
        User user = new User();
        try (Connection connection = source.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT users.id,users.login,users.password,users.name,adress.id,adress.adress,users.id_roles FROM users inner join adress on users.id=adress.id_user WHERE users.login=? AND users.password=?");
             PreparedStatement pstmt2 = connection.prepareStatement(String.format("select id_type from userstypes where id_user=(select users.id from users where users.login = '%s' and users.password = '%s')", login, password));
        ) {
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            pstmt.execute();
            try (ResultSet resultSet = pstmt.executeQuery();
                 ResultSet resultSet2 = pstmt2.executeQuery();
            ) {
                while (resultSet.next()) {
                    user.setId(resultSet.getInt(1));
                    user.setLogin(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setName(resultSet.getString(4));
                    Address address = new Address();
                    address.setId(resultSet.getInt(5));
                    address.setAddress(resultSet.getString(6));
                    user.setAddress(address);
                    user.setId_role(resultSet.getInt(7));
                }
                while (resultSet2.next()) {
                    user.getId_musicType().add(resultSet2.getInt(1));
                }
            } catch (Exception e) {
                connection.rollback();
                log.info(e.getMessage());
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return user;
    }

    public List<Integer> getListMTypes(String[] musikTypes) {
        List<Integer> listTypes = new ArrayList<>();
        for (String index : musikTypes) {
            listTypes.add(Integer.parseInt(index));
        }
        return listTypes;
    }
}
