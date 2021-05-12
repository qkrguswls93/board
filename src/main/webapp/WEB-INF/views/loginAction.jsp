<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<form method="get" action="logout">
	${msg}<br>
		<button type="submit">로그아웃</button>
	로그인 사용자 : ${user}<br>
	<%=session.getAttribute("user")%>
	<br>
	${sessionScope.user }
</form>