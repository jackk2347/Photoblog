<!DOCTYPE html>
<html>
<head>
    <title>Photo Page</title>
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
<h2>Photos</h2>

<c:choose>
    <c:when test="${fn:length(PhotoDatabase) == 0}">
        <i>There are no Photo in the system.</i>
    </c:when>
    <c:otherwise>
        <c:forEach items="${PhotoDatabase}" var="entry">
            <a href="<c:url value="/photo/photopagebyid/${entry.id}" />">
                <img src="<c:url value="/photo/photodownload/${entry.id}"/>" width="300" height="300"/></a>
            </br>Upload by : ${entry.customer.username}
            </br>Description : ${entry.description}
            </br>
        </c:forEach>
    </c:otherwise>
</c:choose>
</body>
</html>
