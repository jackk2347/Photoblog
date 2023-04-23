<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Comment History</title>
    <style>
        .topnav {
            background-color: #333;
            overflow: hidden;
        }

        /* Style the links inside the navigation bar */
        .topnav a {
            float: left;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 17px;
        }

        /* Change the color of links on hover */
        .topnav a:hover {
            background-color: #ddd;
            color: black;
        }

        /* Add a color to the active/current link */
        .topnav a.active {
            background-color: #04AA6D;
            color: white;
        }
    </style>
</head>
<body>
<div class="topnav">
    <security:authorize access="!isAuthenticated()">
    <a href="<c:url value="/login" />">Login</a>
    <a href="<c:url value="/user/register" />">Register</a>
    </security:authorize>
    <a href="<c:url value="/" />">Photo List</a>
    <security:authorize access="hasRole('ADMIN') or hasRole('USER') ">
        <a href="<c:url value="/photo/uploadphoto" />">Upload Photo</a>
        <a href="<c:url value="/user/profile" />">Profile page</a>
    </security:authorize>
    <security:authorize access="hasRole('ADMIN')">
        <a href="<c:url value="/user/list" />">User Management</a>
        <a href="<c:url value="/user/createuser"/>">Create User</a>
    </security:authorize>
</div>
<security:authorize access="hasRole('ADMIN') or hasRole('USER') ">
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post" align="right">
    <input type="submit" value="Log out"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</security:authorize>
<h2>Comment History by ${username}</h2>
<table border="3px">
    <tr>
        <th>Photo ID :</th>
        <th>Comment</th>
        <th>Create Time</th>
    </tr>

    <c:choose>
        <c:when test="${fn:length(comment) == 0}">
            <i>There are no Comment in this user.</i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${comment}" var="entry">
                <tr>
                    <td>  <a href="<c:url value="/photo/photopagebyid/${entry.photoId}" />">${entry.photoId}</a></td>
                    <td>${entry.content}</td>
                    <td><fmt:formatDate value="${entry.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</table>