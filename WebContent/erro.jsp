<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Tela Erros</title>
</head>
<body>

<h1>Erro: Entre em contato com a equipe de suporte do sistem.</h1>

<%
	out.print(request.getAttribute("msg"));
%>
</body>
</html>