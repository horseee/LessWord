package app10e.action;

import app10e.dao.DAOException;
import app10e.dao.ProductDAO;
import app10e.model.Product;

public class SaveProductAction {

	private ProductDAO productDAO;
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	public void save(Product product) {
		try {
			productDAO.insert(product);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
}
