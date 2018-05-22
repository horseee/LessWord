<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title>Save Product</title>
<style type="text/css">@import url(css/main.css);</style>
</head>
<body>
<div id="global">
	<h4>The product has been saved.</h4>
	<p>
		<h5>Details:</h5>
		Product Name: <s:property value="name"/><br/>
		Description: <s:property value="description"/><br/>
		Price: $<s:property value="price"/>
	</p>
</div>
</body>
</html>
