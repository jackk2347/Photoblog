<!DOCTYPE html>
<html>
<head><title> User Management</title>
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
<br/><br/>



<h2>Users</h2>

<a href="<c:url value="/user/createuser" />">Create a User</a><br/><br/>

<c:choose>
  <c:when test="${fn:length(Userlist) == 0}">
    <i>There are no users in the system.</i>
  </c:when>
  <c:otherwise>
    <table>
      <tr>
        <th>Username</th>
        <th>Password</th>
        <th>Tel</th>
        <th>Email</th>
        <th>Description</th>
        <th>Roles</th>
        <th>Edit</th>
        <th>Comment</th>
        <th>Delete</th>
      </tr>
      <c:forEach items="${Userlist}" var="user">
        <tr>
          <td>${user.username}</td>
          <td>${fn:substringAfter(user.password, '{noop}')}</td>
          <td>${user.tel}</td>
          <td>${user.email}</td>
          <td>${user.description}</td>
          <td>
            <c:forEach items="${user.roles}" var="role" varStatus="status">
              <c:if test="${!status.first}">, </c:if>
              ${role.role}
            </c:forEach>
          </td>
          <td>
            [<a href="<c:url value="/user/profile/editprofile/admin/${user.username}" />">Edit</a>]
          </td>
          <td>
            [<a href="<c:url value="/user/comment/${user.username}" />">Comment</a>]
          </td>
          <td>
            [<a href="<c:url value="/user/deleteuser/${user.username}" />">Delete</a>]
          </td>
        </tr>
      </c:forEach>
    </table>
  </c:otherwise>
</c:choose>
</body>
</html>