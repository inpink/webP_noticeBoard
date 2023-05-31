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
<style>
	h2 {
		text-align: center;
		padding: 15px 0;
	}
		
	table {
		border : 1px solid lightgrey;
		border-collapse: collapse;
		margin : auto;
		width:95%;
		background-color: #36BC9B;
		
	}
	th, td {
		border : 1px solid lightgrey;	
		padding : 5px;
	}
	th, #btnBox {
		text-align : center;
	}
	button {
		width:15%;
		border : 1px solid rgba(0, 0, 0, 0);
		border-radius : 5px;
		background-color : DodgerBlue;
		color : white;
		font-size : 1em;
		text-align : center;
		padding : 7px 10px;
		margin : 10px;
	}
</style>
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
				<td onclick="location.href='/jwbook/board/edit.jsp' ">${notice.title}</td>
			</tr>
			</c:forEach>
		</table>
		<div id="btnBox"><button onclick="location.href='/jwbook/board/write.jsp' "> 등록</button></div>
    </div>	
</body>
</html>