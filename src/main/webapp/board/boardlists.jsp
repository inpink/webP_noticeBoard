<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="board.css"> 
<title>방명록 앱</title>
</head>
   <body>
   <div>
    <h2 >방명록 목록</h2> 
    
   
	<button type="button" onclick="location.href='/jwbook/board/edit.jsp' ">수정하러ㄱㄱ</button>
	<a href="/jwbook/upload_notice?action=listNotice">클릭하면 리스트 가져옴</a>
	
    
	<button> 등록</button>
	</div>
</body>
</html>