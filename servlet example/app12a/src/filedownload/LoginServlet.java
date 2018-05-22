package filedownload;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = -920L;

    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if (userName != null && userName.equals("ken") 
                && password != null && password.equals("secret")) {
            HttpSession session = request.getSession(true);
            session.setAttribute("loggedIn", Boolean.TRUE);
            response.sendRedirect("download");
            // must call return or else the code after this if 
            // block, if any, will be executed
            return;
        } else {
            RequestDispatcher dispatcher = 
                    request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}