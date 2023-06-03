<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>방명록 앱</title>
</head>
<link rel="stylesheet" type="text/css" href="board/board.css"/>
<body>
    <h2>방명록 목록</h2>
    <div>
    	<table>
			<tr bgcolor="WhiteSmoke">
				<th>번호</th>	<th>작성자</th><th>이메일</th><th>작성일</th><th>제목</th>
			</tr>
			<c:forEach var="notice" items="${noticelist}" varStatus="status">
			<tr>
				<td>${notice.aid}</td>
				<td>${notice.name}</td>
				<td>${notice.email}</td>
				<td>${notice.date}</td>
				<td><a href="/jwbook/upload_notice?action=getNotice&aid=${notice.aid}">${notice.title}</a></td>
			</tr>
			</c:forEach>
		</table>
		<div id="btnBox"><button onclick="location.href='/jwbook/board/write.jsp' "> 등록</button></div>
    </div>	
</body>
</html>