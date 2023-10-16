<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html><html><head>
<meta charset="UTF-8"><title>Insert title here</title></head><body>
<div class="w3-container">
<form   method="post"  action="${pageContext.request.contextPath}/board/boardUpdatePro" 
  enctype="multipart/form-data" >
  <input type="hidden"   name="num"  value="${board.num}">
  <input type="hidden"   name="file1"  value="${board.file1}">
<h3   class="w3-center">${boardName} 수정</h3>
<br><p>      
<label class="w3-text-grey">작성자</label>
<input class="w3-input w3-border" type="text"  name="name"  readonly="readonly"  value="${board.name }">
</p><p>      
<label class="w3-text-grey">비밀번호</label>
<input class="w3-input w3-border" type="password"  name="pass">
</p><p>      
<label class="w3-text-grey">제목</label>
<input class="w3-input w3-border" type="text"  name="subject" value="${board.subject}">
</p><p>      
<label class="w3-text-grey">내용</label>
<textarea class="w3-input w3-border" style="resize:none"  name="content">${board.content}</textarea>
</p><p>      
<label class="w3-text-grey">파일[${board.file1}] </label>
<input class="w3-input w3-border" type="file" name="f" >
</p>


  <p  class="w3-center">
  <button type="submit" class="w3-btn w3-padding w3-teal" 
  style="width:120px">수정</button></p>
</form>
</div>
</body>
</html>