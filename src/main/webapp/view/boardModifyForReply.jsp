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
<div style="display: inline-block; width: 80%; margin-left: 10%;">
	<table border="1" style="width: 100%" >
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
	</table >
	<hr>
	<p style="text-align: center">댓글</p>
	
	
	<form action="${contextPath}/board/modifyReply" method="post">
		<table border="1" style="width: 100%;">
			<tr>
				<td width="3%">작성자</td>
				<td width="10%">내용</td>
				<td width="3%">날짜</td>
				<td width="1%">확인</td>
				<td width="1%">취소</td>
			</tr>
		<c:choose>
			<c:when test="${empty replyList}">
				<tr>
					<td style="text-align: center" colspan="3">댓글이 없습니다</td>
				</tr>
			</c:when>
			<c:when test="${!empty replyList}">
			<c:forEach var="reply" items="${replyList}">
				<c:if test="${reply.replyNO != replyNO}">
				<tr>
					<td>${reply.id}</td>
					<td>${reply.content}</td>
					<td>${reply.writeDate}</td>
					<td><a>수정</a></td>
					<td><a>삭제</a></td>
				</tr>
				</c:if>
				<c:if test="${reply.replyNO == replyNO}">
					<td>${reply.id}</td>
					<td><input type="text" name="content" value="${reply.content}" style="width:99%; height:18px; border: 0;"></td>
					<td>${reply.writeDate}</td>
					<td><input type="submit" value="확인"/></td>
					<td><a href="${contextPath}/board/detail?boardNO=${board.boardNO}">취소</a></td>
				</c:if>
			</c:forEach>
			</c:when>
		</c:choose>
		</table>
	</form>	
	<hr>
</div>
<div style="padding-left: 10%">
<a href="${contextPath}/board/list">뒤로가기</a><br>
</div>
<jsp:include page="../include/footer.jsp" flush="true"/>
</body>
</html>