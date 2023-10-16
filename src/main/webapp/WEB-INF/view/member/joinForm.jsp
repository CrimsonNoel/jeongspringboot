<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function win_upload() {
		// alert("ok")   //WEB-INF/lib/cos.jar  copy
		var op = "width=500, height=150, left=50, top=150";
		open("${pageContext.request.contextPath}/member/pictureimgForm", "", op);
	}
</script>

</head>
<body>
	<form class="w3-container" name="f"
		action="${pageContext.request.contextPath}/member/joinPro"
		method="post">
		<table class="w3-table-all">
			<caption>MODEL1으로 구현한 회원가입</caption>
			<tr>
				<td rowspan="4" valign="bottom" width="100px"><img src=""
					width="100" height="120" id="pic"><br> <font size="1">
	<a class="w3-button w3-green" href="javascript:win_upload()">사진등록</a>
	<input type="hidden"   name="picture" >
				</font></td>
				<td>아이디</td>
				<td><input type="text" name="id"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="pass"></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>성별</td>
				<td><input type="radio" name="gender" value="1" checked>
					남 <input type="radio" name="gender" value="2">여</td>
			</tr>
			<tr>
				<td>전화번호</td>
				<td colspan="2"><input type="text" name="tel"></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td colspan="2"><input type="text" name="email"></td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit" value="회원가입"></td>
			</tr>
		</table>
	</form>
</body>
</html>

