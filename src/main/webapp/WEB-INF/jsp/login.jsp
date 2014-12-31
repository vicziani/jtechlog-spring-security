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

        <p>Bejelentkezni az admin/admin12 vagy a user/user12 felhasználónév/jelszó
            párossal lehetséges.</p>

        <c:if test="${not empty param.login_error}">
            Sikertelen bejelentkezés
        </c:if>

        <form action="<c:url value='/j_spring_security_check'/>" method="POST">
            <input type="text" name="j_username" value='<c:if test="${not empty param.login_error}"><c:out value="${sessionScope.LAST_USERNAME}"/></c:if>'/>
            <input type="password" name="j_password" value="" />
            <input type="submit" value="Bejelentkezés"/>
        </form>

    </body>
</html>