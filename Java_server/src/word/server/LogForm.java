package word.server;

import java.io.IOException;
import word.server.SQL;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/log")
public class LogForm extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LogForm() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		
        request.setCharacterEncoding("utf-8");
        String name =new String(request.getParameter("name"));
        String password = new String(request.getParameter("password"));
        
        String[] res = null;
        boolean statusRes = false;
        String ResInfo = null;
		try {
			res = SQL.select_get_one(0, name);
			if (res == null) {
				res = SQL.select_get_one(1, name);
			}
		} catch (Exception e) {
			statusRes = false;
			e.printStackTrace();
		} 
		
		int review_count = 0;
		if (res == null) {
			ResInfo = "Username(email)/Password wrong";
			statusRes = false;
		}
		else {
			statusRes = res[1].equals(password);
			if (statusRes == true) {
				ResInfo = "Login Success";
			} else {
				ResInfo = "Username(email)/Password wrong";
			}
			try {
				review_count = SQL.select_review_count(name);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(ResInfo);
		JSONObject jsonObject = new JSONObject(); 
	
		try {
			Map<String , Object> map = new HashMap<String ,Object>();  
			map.put("success", statusRes);
			map.put("info", ResInfo);
			map.put("name", res[0]);
			map.put("portrait", res[2]);
			map.put("recite", res[3]);
			map.put("review", review_count);
			jsonObject.put("result", map);
		} catch (JSONException e) {
			e.printStackTrace();
		}  
	
        response.setContentType("application/json;charset=utf-8");
		response.setStatus(200);
		PrintWriter out = response.getWriter();
		
		out.write(jsonObject.toString());
		out.close();
        
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}