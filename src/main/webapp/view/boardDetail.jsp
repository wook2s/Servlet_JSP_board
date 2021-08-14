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
	<table border="1" style="width: 100%;">
		<tr>
			<td width="3%">작성자</td>
			<td width="10%">내용</td>
			<td width="3%">날짜</td>
			<td width="1%">수정</td>
			<td width="1%">삭제</td>
		</tr>
	<c:choose>
		<c:when test="${empty replyList}">
			<tr>
				<td style="text-align: center" colspan="3">댓글이 없습니다</td>
			</tr>
		</c:when>
		<c:when test="${!empty replyList}">
		<c:forEach var="reply" items="${replyList}">
			<tr>
				<td>${reply.id}</td>
				<td>${reply.content}</td>
				<td>${reply.writeDate}</td>
				<td>
					<c:if test="${id == reply.id}">
					<a href="${contextPath}/board/modifyReplyForm?boardNO=${board.boardNO}&replyNO=${reply.replyNO}">수정</a>
					</c:if>
				</td>
				<td>
					<c:if test="${id == reply.id}">
					<a href="${contextPath}/board/deleteReply?boardNO=${board.boardNO}&replyNO=${reply.replyNO}">삭제</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</c:when>
	</c:choose>
	</table>
	<br>
	<form action="${contextPath}/board/addReply" method="post">
		<table border="1">
			<tr>
				<td width="3%">ID</td>
				<td width="20%">내용</td>
			</tr>
			<tr>
				<td><input type="text" name="id" style="width:95%; height:18px; border: 0;" value="${id}" readonly="readonly"/></td>
				<td><input type="text" name="content" style="width:99%; height:18px; border: 0;"/></td>
			</tr>
		</table>
		<input type="hidden" name="boardNO" value="${board.boardNO}"/>
		<input type="submit" value="답글쓰기"/>
		<input type="reset" value="취소"/>
	</form>
	
	
	<hr>
</div>
<div style="padding-left: 10%">
<a href="${contextPath}/board/list">뒤로가기</a><br>
	<c:if test="${id == board.id}">
		<a href="${contextPath}/board/modifyForm?boardNO=${board.boardNO}">수정</a><br>
		<a href="${contextPath}/board/deleteBoard?boardNO=${board.boardNO}">삭제</a><br>
	</c:if>
</div>
<jsp:include page="../include/footer.jsp" flush="true"/>
</body>
</html>