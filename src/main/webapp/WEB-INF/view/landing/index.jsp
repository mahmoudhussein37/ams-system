<%@include file="/WEB-INF/view/edu/include/top-tag.jsp"%>
<head>
    <title>Title</title>
</head>
<body>


//landing
<br/>
<sec:authorize ifNotGranted="ROLE_USER">
    <a href="/edu/signin">로그인</a><br/>
    <a href="/signup">회원가입</a><br/>
</sec:authorize>
<sec:authorize ifAllGranted="ROLE_USER">
    <a href="/edu/submit-edu">학원 생성</a><br/>
</sec:authorize>

</body>
</html>
