package app11a.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(urlPatterns = { "/singleUpload" })
@MultipartConfig
public class SingleUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 8593038L;

    private String getFilename(Part part) {
        String contentDispositionHeader = 
                part.getHeader("content-disposition");
        String[] elements = contentDispositionHeader.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1)
                        .trim().replace("\"", "");
            }
        }
        return null;
    }

    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {
        
        // save uploaded file to WEB-INF
        Part part = request.getPart("filename");
        String fileName = getFilename(part);
        if (fileName != null && !fileName.isEmpty()) {
            part.write(getServletContext().getRealPath(
                    "/WEB-INF") + "/" + fileName);
        }
        
        // write to browser
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.print("<br/>Uploaded file name: " + fileName);
        writer.print("<br/>Size: " + part.getSize());
        
        String author = request.getParameter("author");
        writer.print("<br/>Author: " + author);
    }
}