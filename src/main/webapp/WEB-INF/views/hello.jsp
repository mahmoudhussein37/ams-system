<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Spring 실습</title>
</head>
<body>
<%@ include file="headerBar.jsp" %>
<img src="/resources/images/spring.png"/><br/>
<sec:authorize access="isAuthenticated()">
    <a href="/user/list">회원 리스트 보기</a>
</sec:authorize>

</body>
</html>
