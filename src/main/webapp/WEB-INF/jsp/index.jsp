<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="text/html;charset=utf8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title>JTechLog Spring Security tutorial</title>
        <link href="<c:url value='css/jtechlog.css' />" rel="stylesheet" type="text/css" />
    </head>

    <body>
        <h1>JTechLog Spring Security tutorial</h1>
        
        <p>Bejelentkezett felhasználó: <security:authentication property="principal.username" /></p>

        <p>Felhasználó XML-ből:</p>
        <ul>
            <li>jtechlog</li>
        </ul>
        
        <p>Felhasználók adatbázisból:</p>

        <ul>
            <c:forEach var="user" items="${users}">
                <li>${user.username}</li>
            </c:forEach>
        </ul>

        <security:authorize ifAllGranted="ROLE_ADMIN">
            <form method="post" action="/">
                Felhasználónév: <input type="text" name="username" value="" />
                Jelszó: <input type="text" name="password" value="" />
                Szerepkörök: <input type="text" name="roles" value="" /><br />
                <input type="submit" value="Felvesz" />
            </form>
        </security:authorize>

        <p><a href="<c:url value='/j_spring_security_logout'/>">Kijelentkezés</a></p>
    </body>
</html>