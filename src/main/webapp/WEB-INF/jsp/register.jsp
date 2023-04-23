<!DOCTYPE html>
<html>
<head><title>User Register</title></head>

<body>
  <h2 class="container">User Register</h2>
  <form:form method="POST" modelAttribute="RegisterForm">
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
    <input type="submit" value="Register"/><br/>
  </form:form>
  <a href="<c:url value="/login" />">Login</a>
  </br>
  <a href="<c:url value="/" />">Main Page</a>

</body>
</html>