package app10d.dao;

import java.util.List;

import app10d.model.Product;

public interface ProductDAO extends DAO {
	List<Product> getProducts() throws DAOException;
	void insert(Product product) throws DAOException;

}
