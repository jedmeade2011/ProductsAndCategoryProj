<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>New Category</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body id="catP">
	<div class="container-fluid">
		<div class="jumbotron jumbotron-fluid">
			<h1 class="text-center">Welcome ${user.firstName} to Products and Categories!</h1>
			<p class="text-center">On this page you are able to create a new category to add to your database.</p>
		</div>
		<ul class="nav nav-pills">
			<li class="nav-item">
			   <a class="nav-link" href="/products/new">Create Product</a>
			</li>
			<li class="nav-item">
			  <a class="nav-link active" href="/categories/new">Create Category</a>
			</li>
			<li class="nav-item">
			  <a class="nav-link" href="/mainPage">Main Page</a>
			</li>
			<li class="nav-item">
			  <a class="nav-link" href="/logout">Logout</a>
			</li>
		</ul>
		<hr>
		<h1 class="text-center">New Category Entry</h1>
		<form:form action="/category/create" method="post" modelAttribute="category">
			<div class="form-group">
				<form:label path="name">Name: </form:label>
				<form:errors path="name"/>
				<form:input path="name" class="form-control"/>
			</div>
			<button type="submit" class="btn btn-outline-primary center">Submit</button>
		</form:form>
	</div>
</body>
</html>