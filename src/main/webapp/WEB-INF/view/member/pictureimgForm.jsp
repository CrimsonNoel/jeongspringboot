<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>회원 사진 등록</title>

</head>
<body>  
<h3>업로드</h3>
<form class="w3-container"   method="post"  
action="${pageContext.request.contextPath}/member/picturePro"
enctype="multipart/form-data">
  <input class="w3-input"  type="file" name="picture">
  <input class="w3-input"  type="submit" value="사진등록">
</form>
</body>
</html>