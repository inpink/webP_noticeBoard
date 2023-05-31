<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록 수정</title>
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
		
	}
	td {
		border : 1px solid lightgrey;	
		text-align : center;
		color: #36BC9B;
		font-weight: bold;
		padding : 5px;
	}
	input { width : 99%; }
	#contentBox {
		text-align : center;
	}
	#content {
		width : 95%;
		background-color: WhiteSmoke;
		border : 1px solid lightgrey;
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
<script>
	function fixNotice() {
		const name = document.getElementById("name").value;
		const email = document.getElementById("email").value;
		const title = document.getElementById("title").value;
		const pwd = document.getElementById("pwd").value;
		const content = document.getElementById("content").value;
		
		if (!(name && email && title && pwd && content)) {
			alert("입력하지 않은 내용이 있습니다. 전체 내용을 채워주세요.");
			return false;
		} else { return true; }
	}
	function deleteAll() {
		document.getElementById("name").value = "";
		document.getElementById("email").value = "";
		document.getElementById("title").value = "";
		document.getElementById("pwd").value = "";
		document.getElementById("content").value = "";
	}
</script>
<body>
	<h2>방명록 수정</h2>
	<div>
		<form name="form2" method="post" action="/jwbook/upload_notice?action=fixNotice&aid=${notice.aid}"
		onsubmit="return fixNotice();">
			<table>
				<tr bgcolor="Gainsboro"><td>작성자</td>
				<td><input type="text" id="name" size="10" name="name" class="form-control" value="${notice.name}"/></td>
				</tr>
				<tr><td>이메일</td>
				<td><input type="text" id="email" size="10" name="email" class="form-control" value="${notice.email}"/></td>
				</tr>
				<tr><td>제목</td>
				<td><input type="text" id="title"  size="10" name="title" class="form-control" value="${notice.title}"/></td>
				</tr>
				<tr><td>비밀번호</td>
				<td><input type="text" id="pwd"  size="10" name="pwd" class="form-control" value="${notice.pwd}"/></td>
				</tr>
			</table>
			<br>
			<div id="contentBox">
				<textarea rows="10" cols="20" id="content" name="content" class="form-control">${notice.content}</textarea>
				<button type="submit">수정</button>
				<button type="button" onClick="deleteAll()">삭제</button>
				<button type="button" onclick="location.href='/jwbook/upload_notice' ">목록</button>
			</div>
		</form>
	</div>
</body>
</html>