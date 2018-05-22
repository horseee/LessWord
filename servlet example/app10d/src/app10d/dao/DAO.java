package app10d.dao;
import java.sql.Connection;

public interface DAO {
    Connection getConnection() throws DAOException;
}
