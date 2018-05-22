package test.app10e.dao;

import app10e.dao.DAOException;
import app10e.dao.ProductDAO;
import app10e.model.Product;
import app10e.util.DependencyInjector;

public class ProductDAOTest {
	
	public static void main(String[] args) {
		DependencyInjector injector = new DependencyInjector();
		try {
			injector.start();
			ProductDAO productDAO = (ProductDAO) injector.getObject(ProductDAO.class);
			Product product = new Product();
			product.setName("New Product");
			product.setDescription("Testing");
			product.setPrice(20.20f);
			try {
				productDAO.insert(product);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		} finally {
			injector.shutDown();
		}
	}
}
