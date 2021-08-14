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
	a{
		text-decoration: none;
	}
	#container{
		display: inline-block;
	}

</style>

</head>
<body>
<jsp:include page="../include/header.jsp" flush="true"/>
<div style="height: 360px">
	<c:if test="${!empty id }">
		<p style="text-align: center;">'${id}'님 접속을 환영합니다. <a href="${contextPath}/board/logout">[로그아웃하기]</a></p>
		
	</c:if>
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
				<td style="text-align: left; padding-left: 15px"><a href="${contextPath}/board/detail?boardNO=${board.boardNO}">${board.title} &nbsp [${board.reCnt}]</a></td>
				<td>${board.writeDate}</td>
			</tr>
			</c:forEach>
			</c:when>
		</c:choose>
	</table>
</div>
<div style="padding-left: 10%">
<a href="${contextPath}/board/insertForm">글쓰기</a><br>
</div>

<div id="pager" style="padding-left: 50%">
	<span style="display: inline-block; width: 21px ">
	<c:if test="${page != 1}">
		<span><a href="${contextPath}/board/list?page=${page-1}">[&lt]</a></span>
	</c:if>
	</span>
	
	<span  style="width: 70px; display:inline-block;">
	<fmt:parseNumber var="boardCount" value="${boardCount}"/>
	<c:set var="start" value="${(((page-1)/5) - (((page-1)/5)%1))*5+1}" />
	<c:forEach begin="${start}" end="${start+4}" varStatus="cnt">
		<c:if test="${cnt.current <= lastPage }">
			<c:if test="${page != cnt.current}">
				<a href="${contextPath}/board/list?page=${cnt.current}">${cnt.current}</a>
			</c:if>
			<c:if test="${page == cnt.current}">
				<a href="${contextPath}/board/list?page=${cnt.current}" style="color: red;">${cnt.current}</a>
			</c:if>
		</c:if>
	</c:forEach>
	</span>
	
	<span style="display: inline-block; width: 21px">
	<c:if test="${page != lastPage}">
		<span><a href="${contextPath}/board/list?page=${page+1}">[&gt]</a></span> 
	</c:if>
	</span>
</div>
<div style="padding-left: 80%">
	<p>총 게시물 : ${boardCount}</p>
</div>
<jsp:include page="../include/footer.jsp" flush="true"/>
</body>
</html>






