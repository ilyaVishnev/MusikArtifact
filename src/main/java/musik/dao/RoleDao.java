package musik.dao;

import musik.models.MusicType;
import musik.models.Role;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RoleDao {
    private PGPoolingDataSource source = DBStore.getDBStore().getDataSource();
    private static Logger log = Logger.getLogger(RoleDao.class.getName());

    public void create(Role role) {
        try (Connection connection = source.getConnection();
             Statement stmt = connection.createStatement();
        ) {
            connection.setAutoCommit(false);
            stmt.addBatch(String.format("insert into roles (id,role) values(%d,%s)", role.getId(), role.getRole()));
            stmt.executeBatch();
            connection.commit();
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
    }

    public void edit(Role role) {
        try (Connection connection = source.getConnection();
             Statement stmt = connection.createStatement();
        ) {
            connection.setAutoCommit(false);
            stmt.addBatch(String.format("update roles set role=%s where id = %d", role.getRole(), role.getId()));
            stmt.executeBatch();
            connection.commit();
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
    }

    public void delete(Role role) {
        try (Connection connection = source.getConnection();
             Statement stmt = connection.createStatement();
        ) {
            connection.setAutoCommit(false);
            stmt.addBatch(String.format("delete from roles where id = %d", role.getId()));
            stmt.executeBatch();
            connection.commit();
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
    }

    public Role getRoleById(int index) {
        Role role = new Role();
        try (Connection connection = source.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(String.format("select * from roles where roles.id=%d", index));
             PreparedStatement pstmt2 = connection.prepareStatement(String.format("select id from users where id_roles=%d", index));
        ) {
            try (ResultSet resultSet = pstmt.executeQuery();
                 ResultSet resultSet2 = pstmt2.executeQuery();
            ) {
                while (resultSet.next()) {
                    role.setId(index);
                    role.setRole(resultSet.getString(2));
                }
                while (resultSet2.next()) {
                    role.getUsers().add(resultSet.getInt(1));
                }
            } catch (Exception e) {
                connection.rollback();
                log.info(e.getMessage());
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return role;
    }

    public List getAll() {
        List<Role> rolesList = new ArrayList<>();
        try (Connection connection = source.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("select id from roles")) {

            try (ResultSet resultSet = pstmt.executeQuery();
            ) {
                while (resultSet.next()) {
                    rolesList.add(this.getRoleById(resultSet.getInt(1)));
                }
            } catch (Exception e) {
                connection.rollback();
                log.info(e.getMessage());
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return rolesList;
    }
}
