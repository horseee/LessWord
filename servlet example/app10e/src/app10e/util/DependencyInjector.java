package app10e.util;

import javax.sql.DataSource;

import app10e.action.GetProductsAction;
import app10e.action.SaveProductAction;
import app10e.dao.ProductDAO;
import app10e.dao.ProductDAOImpl;
import app10e.validator.ProductValidator;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public class DependencyInjector {
	private DataSource dataSource;

	public void start() {
		// create dataSource
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
		cpds.setUser("testuser");
		cpds.setPassword("secret");                                  
			
		// to override default settings:
		cpds.setMinPoolSize(5);                                     
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		dataSource = cpds;
	}

	public void shutDown() {
		// destroy dataSource
		try {
			DataSources.destroy(dataSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Returns an instance of type. type is of type Class
	 * and not String because it's easy to misspell a class name
	 */
	public Object getObject(Class type) {
		if (type == ProductValidator.class) {
			return new ProductValidator();
		} else if (type == ProductDAO.class) {
			return createProductDAO();
		} else if (type == GetProductsAction.class) {
			return createGetProductsAction();
		} else if (type == SaveProductAction.class) {
			return createSaveProductAction();
		}
		return null;
	}

	private GetProductsAction createGetProductsAction() {
		GetProductsAction getProductsAction = new GetProductsAction();
		// inject a ProductDAO to getProductsAction
		getProductsAction.setProductDAO(createProductDAO());
		return getProductsAction;
	}

	private SaveProductAction createSaveProductAction() {
		SaveProductAction saveProductAction = new SaveProductAction();
		// inject a ProductDAO to saveProductAction
		saveProductAction.setProductDAO(createProductDAO());
		return saveProductAction;
	}

	private ProductDAO createProductDAO() {
		ProductDAO productDAO = new ProductDAOImpl();
		// inject a DataSource to productDAO
		productDAO.setDataSource(dataSource);
		return productDAO;
	}
}