<!DOCTYPE html>
<html>
<head><title>Create User</title>
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
<body>
<h2 class="container">Create User(Admin)</h2>
<form:form method="POST" modelAttribute="CreateuserForm">
    <form:label path="username">Username : </form:label><br/>
    <form:input type="text" path="username"/><br/><br/>
    <form:label path="password">Password : </form:label><br/>
    <form:input type="text" path="password"/><br/><br/>
    <form:label path="tel">Phone Number : </form:label><br/>
    <form:input type="text" path="tel"/><br/><br/>
    <form:label path="email">Email address : </form:label><br/>
    <form:input type="text" path="email"/><br/><br/>
    <form:label path="description">Description : </form:label><br/>
    <form:textarea path="description" rows="5" cols="30"/><br/><br/>
    <form:checkbox path="roles" value="ROLE_USER"/>ROLE_USER
    <form:checkbox path="roles" value="ROLE_ADMIN"/>ROLE_ADMIN
    </br>
    <input type="submit" value="Create User"/><br/>
</form:form>

</body>
</html>