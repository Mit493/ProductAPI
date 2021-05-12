<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@page import="model.Product"%>
<%
//Save---------------------------------
if (request.getParameter("proCode") != null)
{
	Product proObj = new Product();
String stsMsg = "";
//Insert--------------------------
if (request.getParameter("hidProductIDSave") == "")
{
stsMsg = proObj.insertProduct(request.getParameter("proCode"),
request.getParameter("proName"),
request.getParameter("proPrice"),
request.getParameter("proQty"),
request.getParameter("proDesc"));
}
else  
	
//Update----------------------
{
stsMsg = proObj.updateProduct(request.getParameter("hidProductIDSave"),
request.getParameter("proCode"),
request.getParameter("proName"),
request.getParameter("proPrice"),
request.getParameter("proQty"),
request.getParameter("proDesc"));
}
session.setAttribute("statusMsg", stsMsg);
}


//Delete-----------------------------
if (request.getParameter("hidProductIDDelete") != null)
{
	Product proObj = new Product();
String stsMsg =
proObj.deleteProduct(request.getParameter("hidProductIDDelete"));
session.setAttribute("statusMsg", stsMsg);
}

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="Views/bootstrap.min.css">

<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/products.js"></script>
<title>Product Management</title>
</head>
<body>
<h1>Product Management</h1>

<form id="formProduct" name="formProduct" method="post" action="products.js">
 Product code:
<input id="proCode" name="proCode" type="text"
 class="form-control form-control-sm">
<br> Product name:
<input id="proName" name="proName" type="text"
 class="form-control form-control-sm">
<br> Product price:
<input id="proPrice" name="proPrice" type="text"
 class="form-control form-control-sm">
 <br>Product quantity:
 <input id="proQty" name="proQty" type="text"
 class="form-control form-control-sm">
 <br> Product description:
<input id="proDesc" name="proDesc" type="text"
 class="form-control form-control-sm">
<br>
<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
<input type="hidden" id="hidProductIDSave" name="hidProductIDSave" value="">
</form>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>

<%
 out.print(session.getAttribute("statusMsg"));
%>
<br>
<div id="divProductsGrid">
<%

Product proObj = new Product();
 out.print(proObj.readProduct());
%>
</div>
</body>
</html>