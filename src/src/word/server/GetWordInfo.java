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

@WebServlet("/GetWordInfo")
public class GetWordInfo extends HttpServlet {
	
	public GetWordInfo() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String word =new String(request.getParameter("word"));
		String USER = new String(request.getParameter("mysql_user"));
		String PASS = new String(request.getParameter("mysql_pass"));
		String DB_URL = new String(request.getParameter("mysql_url"));
        
        String[] wordres = null;
        boolean statusRes = false;
		try {
			wordres = SQL.get_word_info(USER, PASS, DB_URL,word);
			statusRes = true;
		} catch (Exception e) {
			statusRes = false;
			e.printStackTrace();
		} 
		
		try {
			response.setContentType("application/json;charset=utf-8");
			response.setStatus(200);
			PrintWriter out = response.getWriter();
			
			String JsonStr = "{\"word\" :  \"" + word +"\",";
			JsonStr = JsonStr +  "\"pron\" :  \"" + wordres[0] +"\",";
			JsonStr = JsonStr +  "\"pronlink\" :  \"" + wordres[1] +"\",";
			
			JsonStr = JsonStr +  "{\"definition\": [ ";
			for (int i=0; i<4; i++) {
				JsonStr = JsonStr + "\"form\" :  \"" + wordres[2 + i * 2 ] +"\",";
				JsonStr = JsonStr + "\"meaning\" :  \"" + wordres[2 + i * 2 + 1] +"\",";
			}
			JsonStr = JsonStr + "],";
			
			JsonStr = JsonStr +  "\"sample_english\" :  \"" + wordres[10] +"\",";
			JsonStr = JsonStr +  "\"sample_chinese\" :  \"" + wordres[11] +"\",";
			JsonStr = JsonStr +  "\"sample_link\" :  \"" + wordres[12] +"\"";
			JsonStr = JsonStr + "}";
			System.out.println(JsonStr);
			out.write(JsonStr);
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(403);
		}
	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    
	}
	
	
	
}
