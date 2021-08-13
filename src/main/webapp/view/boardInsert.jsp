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
</head>
<body>
<jsp:include page="../include/header.jsp" flush="true"/>
<form action="${contextPath}/board/insertBoard" method="post" enctype="multipart/form-data">
	<table border="1" align="center" width="60%">
		<tr>
			<td width="5%">글 제목</td>
			<td width="15%"><input type="text" name="title" style="width:100%; height:18px; border: 0;"></td>
		</tr>
		<tr>
			<td>글 작성자</td>
			<td><input type="text" name="id" style="width:100%; height:18px; border: 0;"></td>
		</tr>
		<tr>
			<td>사진 첨부</td>
			<td><input type="file" name="imageName" accept="image/*"></td>
		</tr>
		
		<tr>
			<td height="200px">글 내용</td>
			<td><textarea name="content" rows="18" style="width:100%;  border: 0;"></textarea></td>
		</tr>
	</table>
	<div style="padding-left: 20%">
	<a href="${contextPath}/board/list">뒤로가기</a><br>
	<input type="submit" value="저장">
	<input type="reset" value="초기화"><br>
	<a href="${contextPath}/board/insertBoard">저장</a><br>
	<a href="${contextPath}/board/insertForm">취소</a><br>
	</div>
</form>

<jsp:include page="../include/footer.jsp" flush="flase"/>
</body>
</html>