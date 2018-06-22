package word.server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SQL {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/vocabulary?useSSL=false";
    
    static final String USER = "root";
    static final String PASS = "maxinyin";
    
    static final String SqlInsertOperation[] = {"insert into User (name, email, password, portrait, cet4, cet6, toefl, day) values (?, ?, ?, ?, ?, ?, ?, ?)",  //operation 0
    											   "insert into ",  //operation 1
    											   "_word (label, wordbook, wordid) values (?, ?, ?)", //operation 2
    									""};
    static final String SqlSelectOperation[] = {"select * from User where name = ?",   //operation 0
    											   "select * from User where email = ?",  //operation 1   
    											   "select * from cet4 where id = ?", 	 //operation 2
    											   "select * from cet6 where id = ?",     //operation 3
    											   "select * from toefl where id = ?",     //operation 4
    											   "select * from word_info where word = ?", //operation 5
    											   "select * from ", // operation 6
    											   "_word where label = 2 order by rand() limit ?",//operation 7
    											   "_word where label = 1 and TO_DAYS(NOW()) - TO_DAYS(recite_time) > 2", //operation 8
    									""};
    static final String SqlCreateOperation[] = {"create table ","_word( label int, wordbook int, wordid int, recite_time date, primary key(wordbook, wordid))"};
    static final String SqlUpdateOperation[] = {"update ","_word set label = ?, recite_time = ? where wordbook = ? and wordid = ?"};
    
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
    
    public static int select_review_count(String username) throws SQLException, ClassNotFoundException {
	    	Connection conn = null;
	    	ResultSet rs = null;
	    	int count = 0;
	    try{
	        Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection(DB_URL,USER,PASS);
	    
	        PreparedStatement psql = conn.prepareStatement(SqlSelectOperation[6] + username + SqlSelectOperation[8]);
	        rs = psql.executeQuery(); 
	        
	        while (rs.next()) count ++;
	        
	        psql.close();
	        conn.close();
	        return count;
	    } finally{
	        if(conn!=null) conn.close();
	    }
    }
    
    public static void change_word_status(String username, int wordset, int wordid, int status) throws SQLException, ClassNotFoundException{
	    	Connection conn = null;
	    	ResultSet rs = null;
	    	int count = 0;
	    try{
	    		Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection(DB_URL,USER,PASS);
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String strdate = sdf.format(new Date());
	        
	        PreparedStatement psql = conn.prepareStatement(SqlUpdateOperation[0] + username + SqlUpdateOperation[1]);
	        psql.setInt(1, status);
	        psql.setInt(3, wordset);
	        psql.setInt(4, wordid);
	        psql.setString(2, strdate);
	        psql.executeUpdate();
	        
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
	
	public static void create_own_word_book(String cet4, String cet6, String toefl, String username) throws SQLException, ClassNotFoundException {
		Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        
            PreparedStatement psql_create = conn.prepareStatement(SqlCreateOperation[0] + username + SqlCreateOperation[1]);
            psql_create.execute(); 
            
            PreparedStatement psql_insert = conn.prepareStatement(SqlInsertOperation[1] + username + SqlInsertOperation[2]);
        		psql_insert.setString(1, username);
            if (cet4.equals("1")) {
            		for (int i=1; i<=3596; i++) {
            			psql_insert.setInt(1, 2);
            			psql_insert.setInt(2, 0);
            			psql_insert.setInt(3, i);
            			psql_insert.execute();
            		}
            } 
            if (cet6.equals("1")) {
	        		for (int i=1; i<=1000; i++) {
	        			psql_insert.setInt(1, 2);
	        			psql_insert.setInt(2, 1);
	        			psql_insert.setInt(3, i);
	        			psql_insert.execute();
	        		}
	        } 
            if (toefl.equals("1")) {
	        		for (int i=1; i<=4680; i++) {
	        			psql_insert.setInt(1, 2);
	        			psql_insert.setInt(2, 2);
	        			psql_insert.setInt(3, i);
	        			psql_insert.execute();
	        		}
	        } 
            
            psql_create.close();
            psql_insert.close();
            conn.close();
        } finally{
            if(conn!=null) conn.close();
        }
	}
	
	public static String[] select_get_one(int operator, String data) throws SQLException, ClassNotFoundException {
	    	Connection conn = null;
	    	ResultSet rs = null;
	    	int count = 0;
	    try{
	        Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection(DB_URL,USER,PASS);
	    
	        PreparedStatement psql = conn.prepareStatement(SqlSelectOperation[operator]);
	        	psql.setString(1, data);
	        
	        rs = psql.executeQuery(); 
	        String[] res = new String[4];
	        
	        while (rs.next()) {
	        		res[0] = rs.getString("name");
	        		res[1] = rs.getString("password");
	        		res[2] = rs.getString("portrait");
	        		int cet4 = rs.getInt("cet4");
	        		int cet6 = rs.getInt("cet6");
	        		int toefl = rs.getInt("toefl");
	        		int day = rs.getInt("day");
	        		int total_word = 0;
	        		if (cet4 > 0) total_word += 3596;
	        		if (cet6 > 0) total_word += 1000;
	        		if (toefl > 0) total_word += 4680;
	        		total_word = total_word / day;
	        		res[3] = String.valueOf(total_word);
	        }
	        
	        psql.close();
	        conn.close();
	        
	        return res;
	        
	    } finally{
	        if(conn!=null) conn.close();
	    }
	}
	
	public static String[] get_word_for_user(int word_number, String username, String type) throws SQLException, ClassNotFoundException{
		Connection conn = null;
	    	ResultSet rs = null;
	    	String[] word_package = new String[word_number * 3];
	    	int count = 0;
	    try {
	        Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection(DB_URL,USER,PASS);
	        
	        PreparedStatement psql = null;
	        if (type.equals("start-recite")) {
	        		psql = conn.prepareStatement(SqlSelectOperation[6] + username + SqlSelectOperation[7]);
	        		psql.setInt(1, word_number);
	        }
	        else if (type.equals("start-review"))
	        		psql = conn.prepareStatement(SqlSelectOperation[6] + username + SqlSelectOperation[8]);
	        
	        rs = psql.executeQuery(); 
	        
	        PreparedStatement psql_select = null;
	        int ptr = 0;
	        while (rs.next()) {
	        		int wordset = rs.getInt("wordbook");
	        		int wordid = rs.getInt("wordid");
	        		 if (wordset == 0) { //cet4
	 	        		psql_select = conn.prepareStatement(SqlSelectOperation[2]);
	 	        		psql_select.setInt(1, wordid);
	 	         } else if (wordset == 1) {  //cet6
	 	        		psql_select = conn.prepareStatement(SqlSelectOperation[3]);
	 	        		psql_select.setInt(1, wordid);
	 	         } else {  //toefl
	 	        		psql_select = conn.prepareStatement(SqlSelectOperation[4]);
	 	        		psql_select.setInt(1, wordid);
	 	         }
	        		 ResultSet word_res = psql_select.executeQuery();
	        		 while (word_res.next()) {
	        			 word_package[3 * ptr] = word_res.getString("word");
	        			 word_package[3 * ptr + 1] = String.valueOf(wordset);
	        			 word_package[3 * ptr + 2] = String.valueOf(wordid);
	        		 }
	        		 ptr ++;
	        }
        		psql.close();
    	        conn.close();
    	        psql_select.close();
    	        
    	        return word_package;
	    } finally{
	        if(conn!=null) conn.close();
	    }
	}

}
