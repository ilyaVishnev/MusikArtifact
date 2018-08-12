package musik.dao;

import musik.models.User;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBStore {

    private static Logger log = Logger.getLogger(DBStore.class.getName());
    private static final DBStore dbStore = new DBStore();

    public static DBStore getDBStore() {
        return dbStore;
    }

    private PGPoolingDataSource source = this.getDataSource();

    public PGPoolingDataSource getDataSource() {
        if (source == null) {
            source = new PGPoolingDataSource();
            source.setDataSourceName("Logistica");
            source.setServerName("localhost");
            source.setPortNumber(5432);
            source.setDatabaseName("musik");
            source.setUser("postgres");
            source.setPassword("pobeda");
            source.setMaxConnections(30);
        }
        return source;
    }
}
