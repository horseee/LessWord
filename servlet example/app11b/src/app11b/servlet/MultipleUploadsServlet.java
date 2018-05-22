package app11b.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(urlPatterns = { "/multipleUploads" })
@MultipartConfig
public class MultipleUploadsServlet extends HttpServlet {

    private static final long serialVersionUID = 9991L;

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
        
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            if (part.getContentType() != null) {
                // save file Part to disk
                String fileName = getFilename(part);
                if (fileName != null && !fileName.isEmpty()) {
                    part.write(getServletContext().getRealPath(
                            "/WEB-INF") + "/" + fileName);
                    writer.print("<br/>Uploaded file name: " +
                            fileName);
                    writer.print("<br/>Size: " + part.getSize());
                }
            } else {
                // print field name/value
                String partName = part.getName();
                String fieldValue = request.getParameter(partName);
                writer.print("<br/>" + partName + ": " + 
                        fieldValue);
            }
        }        
    }
}