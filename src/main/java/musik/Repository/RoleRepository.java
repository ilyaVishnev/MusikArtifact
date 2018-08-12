package musik.Repository;

import musik.dao.DBStore;
import musik.dao.UserDao;
import musik.models.Role;
import musik.models.User;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RoleRepository {

    private PGPoolingDataSource source = DBStore.getDBStore().getDataSource();
    private static Logger log = Logger.getLogger(UserDao.class.getName());
    private UserRepository userRepository = new UserRepository();

    public List<User> getListOfEntity(Role role) {
        return userRepository.getUserbyRole(role);
    }
}
