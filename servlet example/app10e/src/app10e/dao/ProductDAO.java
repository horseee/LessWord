package app10e.dao;

import java.util.List;

import app10e.model.Product;

public interface ProductDAO extends DAO {
	List<Product> getProducts() throws DAOException;
	void insert(Product product) throws DAOException;

}
