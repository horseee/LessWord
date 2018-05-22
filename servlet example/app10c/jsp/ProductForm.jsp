<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<title>Add Product Form</title>
<style type="text/css">@import url(css/main.css);</style>
</head>
<body>
<div id="global">
    <h3>Add a product</h3>
    <c:if test="${requestScope.errors != null}">
        <p id="errors">
        Error(s)!
        <ul>
        <c:forEach var="error" items="${requestScope.errors}">
            <li>${error}</li>
        </c:forEach>
        </ul>
        </p>
    </c:if>
    <form method="post" action="product_save">
    <table>
    <tr>
        <td>Product Name:</td>
        <td><input type="text" name="productName"
                value="${form.name}"/></td>
    </tr>
    <tr>
        <td>Description:</td>
        <td><input type="text" name="description" 
                value="${form.description}"/></td>
    </tr>
    <tr>
        <td>Price:</td>
        <td><input type="text" name="price" 
                value="${form.price}"/></td>
    </tr>
    <tr>
        <td><input type="reset"/></td>
        <td><input type="submit" value="Add Product"/></td>
    </tr>
    </table>
    </form>
</div>
</body>
</html>
