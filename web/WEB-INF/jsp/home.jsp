<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:if test="${not empty jsonResultado}">
        <pre id="jsonResultado">${jsonResultado}</pre>
    </c:if>
    <c:if test="${empty jsonResultado}">
        <h1>Resultado não encontrado</h1>
    </c:if>
</body>
</html>
