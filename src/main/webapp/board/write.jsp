<!-- 대충 복붙해왔음 -->
<!-- 방명록 작성 html, css, js -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ì¤ìµ3-1</title>
</head>
<style>
	h2 {
		border-radius: 5px;
		background-color: wheat;
		text-align: center;
		padding: 15px 0;
	}
	
	#regform, #result {
		padding: 15px 20px;
		border-radius: 10px;
		margin: auto;
		width: 50%;
		background-color: SandyBrown;
	}
</style>
<script>
	function signUp() {
		alert("ì ë§ë¡ ê°ì íìê² ìµëê¹?");
		document.getElementById("regform").style.display = "none";
		document.getElementById("rname").innerHTML = document.form1.name.value;
		document.getElementById("remail").innerHTML = document.form1.email.value;
		document.getElementById("result").setAttribute("style","display: block; background-color:KhaKi;");
	}
</script>
<body>
	<h2>íì ê°ì</h2>
	<hr>
	<div id="regform">
		<!-- 아래 주소, 파라미터로 post로 보내줘!  -->
		<form name="form1" method="post" action="/webP_noticeBoard/upload_notice?action=addNotice">
			<label>ì´ë¦</label><br>
			<input type="text" name="name" size="40"><br>
			<hr>
			<label>ì´ë©ì¼</label><br>
			<input type="email" name="email" size="40"><br>
			<button type="button" onClick="signUp()">ê°ì</button>
		</form>
	</div>
	<div id="result" class="result">
		<h3>ê°ì ì ë³´</h3>
		<hr>
		ì´ë¦: <span id="rname"></span><br>
		ì´ë©ì¼: <span id="remail"></span><br>
	</div>
	<script>
		document.getElementById("result").style.display = "none";
	</script>
</body>
</html>