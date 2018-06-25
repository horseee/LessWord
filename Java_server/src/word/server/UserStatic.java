package word.server;

import java.io.IOException;
import word.server.SQL;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/UserStatic")
public class UserStatic extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UserStatic() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String name =new String(request.getParameter("name"));
        
        int[] res = null;
        String statusRes = "success";
        List<int[]> list_res = null; 
		try {
			res = SQL.select_user_static(name);
			list_res = SQL.select_user_days(name);
		} catch (Exception e) {
			e.printStackTrace();
			statusRes = "Fail";
		} 
		
		String[] time_res = new String[7];
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -7);
		for (int i=0; i<7; i++) {
			calendar.add(Calendar.DATE, 1);
    			time_res[i]= new SimpleDateFormat("MM-dd").format(calendar.getTime());
		}
			
		JSONObject jsonObject = new JSONObject(); 
	
		try {
			Map<String , Object> map = new HashMap<String ,Object>();  
			map.put("success", statusRes);
			
			map.put("word_total", res);
			map.put("recite", list_res.get(1));
			map.put("review", list_res.get(0));
			map.put("timelabel", time_res);
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