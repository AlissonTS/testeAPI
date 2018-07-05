<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:if test="${not empty consultaFeita}">
        <c:if test="${not empty jsonResultado}">
            <pre id="jsonResultado">${jsonResultado}</pre>
        </c:if>
        <c:if test="${empty jsonResultado}">
            <h1>Resultado não encontrado</h1>
            <c:if test="${not empty tipoErro}">
                <h2>${tipoErro}</h2>
            </c:if>
        </c:if>
    </c:if>
    <c:if test="${empty consultaFeita}">
        <h1>Pesquisar receitas colocando: "/?i=ingredient1,ingredient2" após endereço da API.</h1>
        <h2>Pode ser colocado até 3 ingredientes.</h2>
    </c:if>
</body>
</html>
