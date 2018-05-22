<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<title>Product List</title>
<style type="text/css">@import url(css/main.css);</style>
</head>
<body>
<div id="global">
    <h3>Product List</h3>
    <table>
    <tr>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
    </tr>
    <c:forEach var="product" items="${requestScope.products}">
        <tr>
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td>${product.price}</td>
        </tr>
    </c:forEach>
    </table>
    <p>
        <a href="product_input">Add Product</a>
    </p>
</div>
</body>
</html>