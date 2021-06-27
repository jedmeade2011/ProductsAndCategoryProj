<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main Page</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>	
	<div class="container-fluid">
		<div class="jumbotron jumbotron-fluid">
			<h1 class="text-center">Welcome ${user.firstName} to Products and Categories!</h1>
			<p class="text-center">Here you are able to create and add categories to products and vice versa</p>
		</div>
		<ul class="nav nav-pills">
			<li class="nav-item">
			   <a class="nav-link" href="/products/new">Create Product</a>
			</li>
			<li class="nav-item">
			  <a class="nav-link" href="/categories/new">Create Category</a>
			</li>
			<li class="nav-item">
			  <a class="nav-link active" href="/mainPage">Main Page</a>
			</li>
			<li class="nav-item">
			  <a class="nav-link" href="/logout">Logout</a>
			</li>
		</ul>
		<hr>
		<h1 class="text-center">Products</h1>
		<table class="table table-dark table-striped">
			<thead>
				<tr>
					<th>Product Name</th>
					<th>Product Description</th>
					<th>Product Price</th>
					<th>Product Uploaded by</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${prods}" var="prod">
					<tr>
						<td><a href="/products/${prod.id}">${prod.name}</a></td>
						<td>${prod.description }</td>
						<td>${prod.price }</td>
						<td>${prod.userss.firstName}</td>
						<td><a href="delete/product/${prod.id}">Delete</a>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<h1 class="text-center">Categories</h1>
		<hr>
		<table class="table table-dark table-striped">
			<thead>
				<tr>
					<th>Category Name</th>
					<th>Category Uploaded by</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${cat}" var="cate">
					<tr>
						<td><a href="/category/${cate.id}">${cate.name}</a></td>
						<td>${cate.usered.firstName}</td>
						<td><a href="delete/category/${cate.id}">Delete</a>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>