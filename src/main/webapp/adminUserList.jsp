<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Admin - User List</title>
    <style>
		body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
	}
		header {
            display: flex;
            justify-content: space-between;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
		}
		h1 {
            text-align: center;
            color: #007bff;
		}
	
		.user-container {
            width: 100%;
            max-width: 500px;
            margin: 10px auto;
            display: flex;
            flex-direction: column;
            gap: 10px;
        }
        .user {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
		.user:hover {
            transform: scale(1.05);
		}
	    a {
            text-decoration: none;
            color: #333;
		}
	    .delete-button {
	        background-color: #dc3545;
	        color: white;
	        border: none;
	        padding: 5px 10px;
	        border-radius: 5px;
	        cursor: pointer;
	        transition: background-color 0.3s ease;
	    }
	
	    .delete-button:hover {
	        background-color: #c82333;
	    }
	
	    .edit-button {
	        background-color: #007bff;
	        color: white;
	        border: none;
	        padding: 5px 10px;
	        border-radius: 5px;
	        cursor: pointer;
	        transition: background-color 0.3s ease;
	    }
	
	    .edit-button:hover {
	        background-color: #0056b3;
	    }
    </style>
</head>
<body>
	<header>
		<h2>Admin - User List</h2>
		<form action="admin.jsp" method="get">
            <button type="submit" style="background-color:#ffc107; border:none; color:#333; padding:10px; border-radius:5px; cursor:pointer;">
                Back to Dashboard
            </button>
        </form>
	</header>
	<h1>Select a User to Edit</h1>
	<div class="user-container">
		<c:forEach var="user" items="${users}">
			<div class="user">
				<div class="id">${user.id}</div>
				<div class="username">${user.username}</div>
				<form action="AdminUserController" method="get" style="display:inline;">
					<input type="hidden" name="action" value="edit">
                	<input type="hidden" name="id" value="${user.id}">
                	<button type="submit" class="edit-button">Edit</button>
				</form>
			</div>
		</c:forEach>
	</div>
</body>
</html>