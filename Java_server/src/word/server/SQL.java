package word.server;
import java.sql.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SQL {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/vocabulary?useSSL=false";
    
    static final String USER = "root";
    static final String PASS = "maxinyin";
    
    static final String SqlInsertOperation[] = {"insert into User (name, email, password) values (?, ?, ?)",  //operation 0
    									""};
    static final String SqlSelectOperation[] = {"select * from User where name = ?",   //operation 0
    											   "select * from User where email = ?",  //operation 1   
    											   "select * from cet4 where id = ?", 	 //operation 2
    											   "select * from cet4 where id = ?",     //operation 3
    											   "select * from toefl where id = ?",     //operation 4
    											   "select * from word_info where word = ?", //operation 5
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
	

	public static String[] get_word_info(String word) throws SQLException, ClassNotFoundException{
		Connection conn = null;
	    	ResultSet rs = null;
	    	int info_number = 13;
	    String[] word_info = new String[info_number];
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection(DB_URL,USER,PASS);
	        
	        PreparedStatement psql = conn.prepareStatement(SqlSelectOperation[5]);
	        psql.setString(1, word);
	        rs = psql.executeQuery();
	      
	        while (rs.next()){
	        		word_info[0] = rs.getString("pron");
	        		word_info[1] = rs.getString("pronlink");
	        		word_info[2] = rs.getString("def_form_1");
	        		word_info[3] = rs.getString("def_mean_1");
	        		word_info[4] = rs.getString("def_form_2");
	        		word_info[5] = rs.getString("def_mean_2");
	        		word_info[6] = rs.getString("def_form_3");
	        		word_info[7] = rs.getString("def_mean_3");
	        		word_info[8] = rs.getString("def_form_4");
	        		word_info[9] = rs.getString("def_mean_4");
	        		word_info[10] = rs.getString("sample_eng");
	        		word_info[11] = rs.getString("sample_chn");
	        		word_info[12] = rs.getString("sample_link");
	        }
	        return word_info;
	        
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
	
	public static String[] get_word_for_user(int word_number, int wordset) throws SQLException, ClassNotFoundException{
		Connection conn = null;
	    	ResultSet rs = null;
	    	String[] word_package = new String[word_number];
	    	int count = 0;
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection(DB_URL,USER,PASS);
	        
	        PreparedStatement psql;
	        int max_word = 0;
	        if (wordset == 0) { //cet4
	        		psql = conn.prepareStatement(SqlSelectOperation[2]);
        			max_word = 3596;
	        } else if (wordset == 1) {  //cet6
	        		psql = conn.prepareStatement(SqlSelectOperation[3]);
	        		max_word = 1000;
	        } else {  //toefl
	        		psql = conn.prepareStatement(SqlSelectOperation[4]);
	        		max_word = 4680;
	        }
	        
        		Random random = new Random();
        		for (int i=0; i<word_number; i++) {
        			int wordnum = random.nextInt(max_word) + 1;
        			psql.setInt(1, wordnum);
        			//System.out.println(psql.toString());
        			rs = psql.executeQuery(); 
        	        String res = null;
        	        
        	        while (rs.next()) {
        	        		res = rs.getString("word");
        	        }
        	        int flag = 0;
        	        for (String eachword: word_package) {
        	        		if (eachword == res) {
        	        			i--;
        	        			flag = 1;
        	        			break;
        	        		}
        	        }
        	        if (flag == 0)
        	        		word_package[i] = res;
        		}
        		
        		psql.close();
    	        conn.close();
    	        
    	        return word_package;
     
	    } finally{
	        if(conn!=null) conn.close();
	    }
	}

}
