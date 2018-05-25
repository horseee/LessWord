package word.server;

import java.io.IOException;
import word.server.SQL;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        
        String res = null;
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
		
		if (res == null) {
			ResInfo = "Username(email)/Password wrong";
			statusRes = false;
		}
		else {
			statusRes = res.equals(password);
			if (statusRes == true) {
				ResInfo = "Login Success";
			} else {
				ResInfo = "Username(email)/Password wrong";
			}
		}
		
		System.out.println(ResInfo);
		
        response.setContentType("application/json;charset=utf-8");
		response.setStatus(200);
		PrintWriter out = response.getWriter();
		
		String JsonStr = "{\"success\" : " + statusRes +", \"info\":\"" + ResInfo + "\"}";
		
		out.write(JsonStr);
		out.close();
        
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}