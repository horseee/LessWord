package app10d.dao;

public class DAOFactory {
	public static ProductDAO getProductDAO() {
        return new ProductDAOImpl(); 
    }
}
