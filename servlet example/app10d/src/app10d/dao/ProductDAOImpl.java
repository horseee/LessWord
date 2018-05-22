package app10d.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app10d.model.Product;

public class ProductDAOImpl extends BaseDAO implements ProductDAO {
	
	private static final String GET_PRODUCTS_SQL = 
			"SELECT name, description, price FROM products";
	public List<Product> getProducts() throws DAOException {
		List<Product> products = new ArrayList<Product>();
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(
					GET_PRODUCTS_SQL);
			resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				Product product = new Product();
				product.setName(resultSet.getString("name"));
				product.setDescription(
						resultSet.getString("description"));
				product.setPrice(resultSet.getFloat("price"));
				products.add(product);
			}
		} catch (SQLException e) {
			throw new DAOException("Error getting products. "
					+ e.getMessage());
		} finally {
			closeDBObjects(resultSet, pStatement, connection);
		}
		return products;
	}
	
	private static final String INSERT_PRODUCT_SQL = 
			"INSERT INTO products " +
			"(name, description, price) " +
			"VALUES (?, ?, ?)";
			
	public void insert(Product product) 
			throws DAOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			pStatement = connection.prepareStatement(
					INSERT_PRODUCT_SQL);
			pStatement.setString(1, product.getName());
			pStatement.setString(2, 
					product.getDescription());
			pStatement.setFloat(3, product.getPrice());
			pStatement.execute();
		} catch (SQLException e) {
			throw new DAOException("Error adding product. "
					+ e.getMessage());
		} finally {
			closeDBObjects(null, pStatement, connection);
		}
	}
}
