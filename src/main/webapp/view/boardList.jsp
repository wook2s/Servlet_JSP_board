<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
	#tableTitle td{
		background-color: yellow;
	}
	table tr td{
		text-align: center;
	}

</style>

</head>
<body>
<h1>list page</h1>
<table border="1" align="center" width="80%">
	<tr id="tableTitle">
		<td width="4%">글번호</td>
		<td width="4%">작성자</td>
		<td width="15%">제목</td>
		<td width="4%">날짜</td>
	</tr>
	<c:choose>
		<c:when test="${boardList == null }">
		<tr >
			<td colspan="4" align="center">등록된 글이 없습니다.</td>
		</tr>
		</c:when>
		<c:when test="${boardList != null}">
		<c:forEach var="board" items="${boardList}">
		<tr>
			<td>${board.boardNO}</td>
			<td>${board.id}</td>
			<td><a href="${contextPath}/board/detail?boardNO=${board.boardNO}">${board.title}</a></td>
			<td>${board.writeDate}</td>
		</tr>
		</c:forEach>
		</c:when>
	</c:choose>
</table>
<div style="padding-left: 10%">
<a href="${contextPath}/board/insertForm">글쓰기</a><br>
</div>
</body>
</html>