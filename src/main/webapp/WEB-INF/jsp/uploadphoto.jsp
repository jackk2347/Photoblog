<!DOCTYPE html>
<html>
<head><title>Upload Photo</title>
    <title>User profile page</title>
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
<h2>Upload Photo</h2>
<form:form method="POST" enctype="multipart/form-data" modelAttribute="photoForm">
    <form:label path="description">Description</form:label><br/>
    <form:textarea path="description" rows="5" cols="30"/><br/><br/>
    <b>Photo</b><br/>
    <input type="file" name="photos" multiple="multiple" accept="image/*"/><br/><br/>
    <input type="submit" value="Submit"/>
</form:form>

</body>
</html>