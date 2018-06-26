package word.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import word.server.SQL;

/**
 * Servlet implementation class HelloForm
 */
@WebServlet("/user_define_word")
public class UserDefineWord extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDefineWord() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		request.setCharacterEncoding("utf-8");
    		String user = new String(request.getParameter("user"));
        String word =new String(request.getParameter("word"));
        String pron = new String(request.getParameter("pron"));
        String pron_link = new String(request.getParameter("pron_link"));
        String form = new String(request.getParameter("form"));
        String meaning = new String(request.getParameter("meaning"));
        String sentence_eng = new String(request.getParameter("sentence_eng"));
        String sentence_chn = new String(request.getParameter("sentence_chn"));
        
        System.out.println("user '"+ user + "' add word : " + word);
        
       
        String ResInfo = "Fail";
        try {
        	   SQL.insert(3, new String[]{user, word, pron, pron_link, form, meaning, sentence_eng, sentence_chn, "3"});
        	   ResInfo = "Success";
        } catch (SQLException e1){
        		
        } catch (ClassNotFoundException e2) {
			
		} 
        
        JSONObject jsonObject = new JSONObject(); 
		try {
			jsonObject.put("result", ResInfo);
		} catch (JSONException e) {
			e.printStackTrace();
		}  
		response.setStatus(200);
        response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.write(jsonObject.toString());
		out.close();
        
    }
}