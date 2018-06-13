package word.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetTodayWord")
public class GetWord extends HttpServlet {
	
	public GetWord() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name =new String(request.getParameter("name"));
        Integer wordnumber = new Integer(request.getParameter("wordnumber"));
        Integer wordset = new Integer(request.getParameter("wordset"));
        
        String[] wordres = null;
        boolean statusRes = false;
        String ResInfo = null;
		try {
			wordres = SQL.get_word_for_user(wordnumber.intValue(), wordset.intValue());
		} catch (Exception e) {
			statusRes = false;
			e.printStackTrace();
		} 
		
		try {
			response.setContentType("application/json;charset=utf-8");
			response.setStatus(200);
			PrintWriter out = response.getWriter();
			
			String JsonStr = "{\"word\" : [";
			int i=0;
			for (i=0; i<wordnumber.intValue()-1; i++) {
				JsonStr = JsonStr + "\""+wordres[i] + "\",";
			}
			JsonStr = JsonStr +  "\""+wordres[i] + "\"";
			JsonStr = JsonStr + "]}";
			System.out.println(JsonStr);
			out.write(JsonStr);
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(403);
		}
	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
	    String name =new String(request.getParameter("name"));
	    String password = new String(request.getParameter("password"));
	    String email = new String(request.getParameter("email"));
	    System.out.println("new user: \nname: "+ name + "\nemail:" + email + "\npassword: "+ password);
	    
	    response.setContentType("text/html;charset=UTF-8");
	    try {
	    	   SQL.insert(0, new String[]{name, email, password});
	    } catch (SQLException e1){
	    		response.setStatus(403);
	    		e1.printStackTrace();
	    } catch (ClassNotFoundException e2) {
			e2.printStackTrace();
			response.setStatus(400);
		} 
	    response.setStatus(200);
	    
	}
	
	
	
}
