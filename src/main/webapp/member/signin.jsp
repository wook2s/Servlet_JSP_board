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
   <title>회원 등록창</title>
<body>
<form method="post" action="${contextPath}/board/login">
<h1  style="text-align:center">로그인 하세요</h1>
	<table  align="center">
	    <tr>
	       <td width="200"><p align="right">아이디</td>
	       <td width="400"><input type="text" name="id"></td>
	    </tr>
	    <tr>
	        <td width="200"><p align="right">비밀번호</td>
	        <td width="400"><input type="password"  name="pwd"></td>
	    </tr>
	    <tr>
	        <td width="200"><p>&nbsp;</p></td>
	        <td width="400">
			<input type="submit" value="로그인">
			<input type="reset" value="다시입력">
	  		</td>
	    </tr>
	</table>
	<br><br>
	
	<div align="center">
    <a href="signup.jsp">회원가입</a>
    </div>
</form>
</body>
</html>
