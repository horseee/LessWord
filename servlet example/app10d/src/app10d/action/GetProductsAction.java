package app10d.action;

import java.util.List;

import app10d.dao.DAOException;
import app10d.dao.DAOFactory;
import app10d.dao.ProductDAO;
import app10d.model.Product;

public class GetProductsAction {
	public List<Product> getProducts() {
		ProductDAO productDAO = DAOFactory.getProductDAO();
		List<Product> products = null;
		try {
			products = productDAO.getProducts();
		} catch (DAOException e) {
			
		}
		return products;
	}
}
