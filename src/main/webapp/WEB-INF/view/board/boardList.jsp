<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html><html><head> 
<meta charset="UTF-8">
<title>Insert title here</title>
</head><body>
<div class="w3-container">
<h3  class="w3-center">${boardName}</h3>
<a class="w3-button   w3-right  w3-grey"   
    href="${pageContext.request.contextPath}/board/boardForm" >게시판 입력</a>
<table class="w3-table-all" style="color:#000">
 <tbody> <tr>
  <th>번호</th>
  <th>이름</th>
  <th>제목</th>
   <th>파일</th>
   <th>입력일</th>
  <th>조회수</th> </tr>
  <c:set var="boardNum"  value="${boardNum}"/>
  <c:forEach  var="b"  items="${list }">
  <tr>
   <td>${boardNum}</td>
   <c:set var="boardNum"  value="${boardNum-1}"/>
  <td>${b.name}</td>
  <td><a  href="${pageContext.request.contextPath}/board/boardComment?num=${b.num}">${b.subject}</a></td>
  <td>${b.file1}</td>
  <td>${b.regdate}</td>
  <td>${b.readcnt}</td>
   </tr>
  
  </c:forEach>
  </tbody>


</table> 
<br>

<div class="w3-bar w3-center w3-small">
 <c:if test="${start < bottomLine }" > 
 <a href="#"  class="w3-button w3-disabled">[이전]</a>
 </c:if>
 <c:if test="${start > bottomLine }" > 
 <a href="${pageContext.request.contextPath}/board/boardList?pageNum=${start-bottomLine}" 
  class="w3-button">[이전]</a>
 </c:if>
 
 
 <c:forEach  var="p"  begin="${start}"  end="${end}">
 <a href="${pageContext.request.contextPath}/board/boardList?pageNum=${p}" 
  class="w3-button <c:if test="${pageInt==p}">  w3-red  </c:if> ">${p}</a>
 </c:forEach>
 
 <c:if test="${end >= maxPage }" >
  <a href="#"  class="w3-button  w3-disabled">[다음]</a>
  </c:if>
<c:if test="${end <  maxPage }" >
  <a href="${pageContext.request.contextPath}/board/boardList?pageNum=${start+bottomLine}" 
   class="w3-button ">[다음]</a>
  </c:if>
</div>







</div>
</body></html>