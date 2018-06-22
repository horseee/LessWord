package word.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/ChangeWord")
public class WordStatus extends HttpServlet {
	
	public WordStatus() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name =new String(request.getParameter("name"));
        String wordset = new String(request.getParameter("wordset"));
        String wordid = new String(request.getParameter("wordid"));
        String status = new String(request.getParameter("status"));
        String success = "false";
        
		try {
			SQL.change_word_status(name, Integer.parseInt(wordset), Integer.parseInt(wordid), Integer.parseInt(status));
			success = "true";
		} catch (Exception e) {
			e.printStackTrace();
			success = "false";
		} 
		
		try {
			response.setContentType("application/json;charset=utf-8");
			response.setStatus(200);
			PrintWriter out = response.getWriter();
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("success", success);  
			out.write(jsonObject.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(403);
		}
	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	
	
}
