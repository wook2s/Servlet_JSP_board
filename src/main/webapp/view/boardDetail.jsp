<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
<jsp:include page="../include/header.jsp" flush="true"/>
<table border="1" align="center" width="60%">
	<tr>
		<td width="3%">글 번호</td>
		<td width="10%">${board.boardNO}</td>
	</tr>
	<tr>
		<td>글 제목</td>
		<td>${board.title}</td>
	</tr>
	<tr>
		<td>글 작성자</td>
		<td>${board.id}</td>
	</tr>
	<tr>
		<td>글 날짜</td>
		<td>${board.writeDate}</td>
	</tr>
	<tr>
		<td>첨부 파일</td>
		<td><a href="${contextPath}/board/downloadImage?imageName=${board.imageName}&boardNO=${board.boardNO}">${board.imageName}</a></td>
	</tr>
	<tr>
		<td height="200px">글 내용</td>
		<td>${board.content}</td>
	</tr>
	
</table>
<div style="padding-left: 20%">
<a href="${contextPath}/board/list">뒤로가기</a><br>
<a href="${contextPath}/board/modifyForm?boardNO=${board.boardNO}">수정</a><br>
<a href="${contextPath}/board/deleteBoard?boardNO=${board.boardNO}">삭제</a><br>
</div>
<jsp:include page="../include/footer.jsp" flush="true"/>
</body>
</html>