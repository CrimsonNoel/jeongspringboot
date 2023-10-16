<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html><html><head>
<meta charset="UTF-8"><title>Insert title here</title>
<script>
img = opener.document.getElementById("pic");  //<img  src=
img.src = "${pageContext.request.contextPath}/view/member/picture/${filename}";  
opener.document.f.picture.value="${filename}"
self.close()
</script>

</head><body>
${filename}
</body>
</html>