<html>
<body>
<h2>Hello World!</h2>
<h2>I'm Spring MVC-SECURITY application</h2>
<form action="/admin">
    <input type="submit" value="Login">
</form>
<form th:action="@{/logout}">
    <input type="submit" value="Logout">
</form>
</body>
</html>
