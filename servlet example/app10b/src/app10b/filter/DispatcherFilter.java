package app10b.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import app10b.action.SaveProductAction;
import app10b.form.ProductForm;
import app10b.model.Product;

@WebFilter(filterName = "DispatcherFilter",
        urlPatterns = { "/*" })
public class DispatcherFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        /*
         * uri is in this form: /contextName/resourceName, for
         * example /app01b/product_input. However, in the
         * case of a default context, the context name is empty,
         * and uri has this form /resourceName, e.g.:
         * /product_input
         */
        // action processing
        int lastIndex = uri.lastIndexOf("/");
        String action = uri.substring(lastIndex + 1);
        if (action.equals("product_input")) {
            // do nothing
        } else if (action.equals("product_save")) {
            // create form
            ProductForm productForm = new ProductForm();
            // populate action properties
            productForm.setName(request.getParameter("name"));
            productForm.setDescription(
                    request.getParameter("description"));
            productForm.setPrice(request.getParameter("price"));

            // create model
            Product product = new Product();
            product.setName(productForm.getName());
            product.setDescription(productForm.getDescription());
            try {
            	product.setPrice(Float.parseFloat(
            			productForm.getPrice()));
            } catch (NumberFormatException e) {
            }
            // execute action method
            SaveProductAction saveProductAction =
            		new SaveProductAction();
            saveProductAction.save(product);

            // store model in a scope variable for the view
            request.setAttribute("product", product);
        }

        // forward to a view
        String dispatchUrl = null;
        if (action.equals("product_input")) {
            dispatchUrl = "/jsp/ProductForm.jsp";
        } else if (action.equals("product_save")) {
            dispatchUrl = "/jsp/ProductDetails.jsp";
        }
        if (dispatchUrl != null) {
            RequestDispatcher rd = request
                    .getRequestDispatcher(dispatchUrl);
            rd.forward(request, response);
        } else {
            // let static contents pass
            filterChain.doFilter(request, response);
        }
    }
}
