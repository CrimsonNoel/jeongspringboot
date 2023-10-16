<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form class="w3-container" 
	action="${pageContext.request.contextPath}/member/memberDeletePro" method="post">
		<p>
			<label>비밀번호</label> <input class="w3-input" type="password"
				name="pass">
		</p>
		<p>
			<input class="w3-input" type="submit" value="확인">
		</p>
	</form>

</body>
</html>