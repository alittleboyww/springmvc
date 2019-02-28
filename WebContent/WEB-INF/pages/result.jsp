<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table>
	<td>
		<tr>名字</tr>
		<tr>${name }</tr>
	</td>
	<td>
		<tr>年龄</tr>
		<tr>${age }</tr>
	</td>
	<td>
		<tr>id</tr>
		<tr>${id }</tr>
	</td>
</table>
</body>
</html>