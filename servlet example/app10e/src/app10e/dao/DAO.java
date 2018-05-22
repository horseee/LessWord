package app10e.dao;
import java.sql.Connection;

import javax.sql.DataSource;

public interface DAO {
	void setDataSource(DataSource dataSource);
    Connection getConnection() throws DAOException;
}
