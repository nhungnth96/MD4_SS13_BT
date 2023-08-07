<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 8/6/2023
  Time: 12:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
</head>
<body>
<center>
    <h1>User Management</h1>
    <h2>
        <a href="/users?action=CREATE">Add New User</a>
    </h2>
    <form action="/users">
        <input type="text" name="search" placeholder="search" value="${searchName}" >
        <input type="submit" value="SEARCH" name="action">

    </form>
    <form action="/users">
    <select name="selectSort">
        <option value="ASC">Name ASC</option>
        <option value="DESC">Name DESC</option>
    </select>
    <input type="submit" value="SORT" name="action">
    </form>
</center>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2>List of Users</h2></caption>
        <tr>
            <th>STT</th>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Country</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="user" items="${userList}" varStatus="iterator">
            <tr>
                <td><c:out value="${iterator.count}"/></td>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.country}"/></td>
                <td>
                    <a href="/users?action=EDIT&id=${user.id}">Edit</a>
                    <a href="/users?action=DELETE&id=${user.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
