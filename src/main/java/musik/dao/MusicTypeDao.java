package musik.dao;

import musik.models.Address;
import musik.models.MusicType;
import musik.models.User;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class MusicTypeDao {
    private PGPoolingDataSource source = DBStore.getDBStore().getDataSource();
    private static Logger log = Logger.getLogger(MusicTypeDao.class.getName());

    public void create(MusicType musicType) {
        try (Connection connection = source.getConnection();
             PreparedStatement stmt = connection.prepareStatement(String.format("insert into musictypes (id,type) values(%d,%s)", musicType.getId(), musicType.getType()));
        ) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
    }

    public void edit(MusicType musicType) {
        try (Connection connection = source.getConnection();
             PreparedStatement stmt = connection.prepareStatement(String.format("update musictypes set type=%s where id = %d", musicType.getType(), musicType.getId()));
        ) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
    }

    public void delete(MusicType musicType) {
        try (Connection connection = source.getConnection();
             PreparedStatement stmt = connection.prepareStatement(String.format("delete from musictypes where id = %d", musicType.getId()));
        ) {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
    }

    public MusicType getTypeById(int index) {
        MusicType musicType = new MusicType();
        try (Connection connection = source.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(String.format("select * from musictypes where musictypes.id=%d", index));
             PreparedStatement pstmt2 = connection.prepareStatement(String.format("select id_user from userstypes where id_type=%d", index));
             ResultSet resultSet = pstmt.executeQuery();
             ResultSet resultSet2 = pstmt2.executeQuery();
        ) {
            while (resultSet.next()) {
                musicType.setId(index);
                musicType.setType(resultSet.getString(2));
            }
            while (resultSet2.next()) {
                musicType.getUsers().add(resultSet2.getInt(1));
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return musicType;
    }

    public List getAll() {
        List<MusicType> typesList = new ArrayList<>();
        try (Connection connection = source.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("select id from musictypes");
             ResultSet resultSet = pstmt.executeQuery();) {
            while (resultSet.next()) {
                typesList.add(this.getTypeById(resultSet.getInt(1)));
            }
        } catch (SQLException ex) {
            log.info(ex.getMessage());
        }
        return typesList;
    }

    public List<Integer> getListMTypes(String[] musikTypes) {
        List<Integer> listTypes = new ArrayList<>();
        for (String index : musikTypes) {
            listTypes.add(Integer.parseInt(index));
        }
        return listTypes;
    }
}
