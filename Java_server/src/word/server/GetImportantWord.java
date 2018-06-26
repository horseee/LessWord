package word.server;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;



@WebServlet("/ImportantWord")
public class GetImportantWord extends HttpServlet {
	
	public GetImportantWord() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name =new String(request.getParameter("name"));
        
        String[] wordres = null;
        String[] userres = null;
        boolean statusRes = false;
        String ResInfo = null;
        int wordnumber = 0;
        int add_wordnumber = 0;
		try {
			wordres = SQL.get_important_word_for_user(name);
			wordnumber = wordres.length  / 3;
			userres = SQL.get_define_word_for_user(name);
			add_wordnumber = userres.length / 7;
		} catch (Exception e) {
			statusRes = false;
			e.printStackTrace();
		} 
		
		try {
			response.setContentType("application/json;charset=utf-8");
			response.setStatus(200);
			PrintWriter out = response.getWriter();
			
			JSONObject jsonObject = new JSONObject(); 
			List<Object> list = new ArrayList<Object>();  
			try {
				for (int i=0; i<wordnumber; i++) {
					String[] wordinfo = SQL.get_word_info(wordres[3 * i]);
					statusRes = true;
		            
		            Map<String , Object> map = new HashMap<String ,Object>();  
		            map.put("word", wordres[3 * i]); 
		            map.put("wordset", wordres[3 * i + 1]);
		            map.put("wordid", wordres[3 * i + 2]);
		            map.put("pron", wordinfo[0]);
		            map.put("pronlink", wordinfo[1]);
		            
		            List<Object> deflist = new ArrayList<Object>();
		            for (int j=0; j<4; j++) {
		            		Map<String , Object> defmap = new HashMap<String ,Object>();  
		            		defmap.put("form", wordinfo[2*j + 2]);
		            		defmap.put("meaning", wordinfo[2*j + 3]);
		            		deflist.add(defmap);
		            }
		            map.put("definition", deflist);
		            
		            map.put("sample_english", wordinfo[10]);
		            map.put("sample_chinese", wordinfo[11]);
		            map.put("sample_link", wordinfo[12]);
		            list.add(map); 
				}
				
				for (int i=0; i<add_wordnumber; i++) {
					statusRes = true;
		            
		            Map<String , Object> map = new HashMap<String ,Object>();  
		            map.put("word", userres[i * 7]); 
		            map.put("wordset", -1);
		            map.put("wordid", -1);
		            map.put("pron", userres[i * 7 + 1]);
		            map.put("pronlink", userres[i * 7 + 2]);
		            
		            List<Object> deflist = new ArrayList<Object>();
		            
		            		Map<String , Object> defmap = new HashMap<String ,Object>();  
		            		defmap.put("form", userres[i * 7 + 3]);
		            		defmap.put("meaning", userres[i * 7 + 4]);
		            		deflist.add(defmap);
		            
		            map.put("definition", deflist);
		            
		            map.put("sample_english", userres[i * 7 + 5]);
		            map.put("sample_chinese", userres[i * 7 + 6]);
		            map.put("sample_link", "");
		            list.add(map); 
				}
				
				
				jsonObject.put("wordlist", list);  
			} catch (Exception e) {
				statusRes = false;
				e.printStackTrace();
			} 
			
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
