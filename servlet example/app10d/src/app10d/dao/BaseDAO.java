package app10d.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

public class BaseDAO implements DAO {
    
    public Connection getConnection() throws DAOException {        
        DataSource dataSource = DataSourceCache.getInstance().getDataSource();
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException();
        }
    }

	protected void closeDBObjects(ResultSet resultSet, Statement statement, 
			Connection connection) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (Exception e) {
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
			}
		}
	}
}