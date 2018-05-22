package app10e.action;

import java.util.List;

import app10e.dao.DAOException;
import app10e.dao.ProductDAO;
import app10e.model.Product;

public class GetProductsAction {
	private ProductDAO productDAO;
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	public List<Product> getProducts() {
		List<Product> products = null;
		try {
			products = productDAO.getProducts();
		} catch (DAOException e) {
			
		}
		return products;
	}
}
