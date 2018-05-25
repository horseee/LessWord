package word.server;
import java.sql.*;

public class SQL {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/vocabulary?useSSL=false";
    
    static final String USER = "root";
    static final String PASS = "maxinyin";
    
    static final String SqlInsertOperation[] = {"insert into User (name, email, password) values (?, ?, ?)",  //operation 0
    									""};
    static final String SqlSelectOperation[] = {"select * from User where name = ?",   //operation 0
    											   "select * from User where email = ?",  //operation 1   
    									""};
    
    public static void insert(int operator, String[] data ) throws SQLException, ClassNotFoundException {
    		Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        
            PreparedStatement psql = conn.prepareStatement(SqlInsertOperation[operator]);
            for (int i=1; i<=data.length; i++) {
            		psql.setString(i, data[i-1]);
            }
            psql.executeUpdate(); 
            
            psql.close();
            conn.close();
        } finally{
            if(conn!=null) conn.close();
        }
    }
    
	public static int select_count(int operator, String[] data) throws SQLException, ClassNotFoundException {
	    	Connection conn = null;
	    	ResultSet rs = null;
	    	int count = 0;
	    try{
	        Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection(DB_URL,USER,PASS);
	    
	        PreparedStatement psql = conn.prepareStatement(SqlSelectOperation[operator]);
	        for (int i=1; i<=data.length; i++) {
	        		psql.setString(i, data[i-1]);
	        }
	        rs = psql.executeQuery(); 
	        
	        while (rs.next()) count ++;
	        
	        psql.close();
	        conn.close();
	        return count;
	    } finally{
	        if(conn!=null) conn.close();
	    }
    }
	
	public static String select_get_one(int operator, String data) throws SQLException, ClassNotFoundException {
    	Connection conn = null;
    	ResultSet rs = null;
    	int count = 0;
    try{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
    
        PreparedStatement psql = conn.prepareStatement(SqlSelectOperation[operator]);
        	psql.setString(1, data);
        
        rs = psql.executeQuery(); 
        String res = null;
        
        while (rs.next()) {
        		res = rs.getString("password");
        }
        
        psql.close();
        conn.close();
        
        return res;
        
    } finally{
        if(conn!=null) conn.close();
    }
}
	
}
