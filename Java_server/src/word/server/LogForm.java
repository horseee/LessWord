package word.server;

import java.io.IOException;
import java.io.PrintWriter;

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
        
        response.setContentType("text/html;charset=UTF-8");
        
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}