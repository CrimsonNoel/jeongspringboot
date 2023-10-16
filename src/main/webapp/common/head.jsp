<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<title>Insert title here</title>
</head>
<body>
<div class="w3-bar w3-light-grey">
    <a href="${pageContext.request.contextPath}/member/index" class="w3-bar-item w3-button w3-green">KIC Campus</a>
  <% if (session.getAttribute("id")==null) { %>
    <a href="${pageContext.request.contextPath}/member/joinForm" class="w3-bar-item w3-button w3-hide-small">회원가입</a>
     <a href="${pageContext.request.contextPath}/member/loginForm" class="w3-bar-item w3-button w3-hide-small">로그인</a>
     <% } else { %>
      <a href="${pageContext.request.contextPath}/member/memberInfo" class="w3-bar-item w3-button w3-hide-small">
      회원정보확인[<%=session.getAttribute("id") %>]</a>
     <a href="${pageContext.request.contextPath}/member/logout" class="w3-bar-item w3-button w3-hide-small">로그아웃</a>
 <%} %>
 <%
 String admin = (String)session.getAttribute("id");
 if (admin!=null && admin.equals("admin")) { 
 %>
  <a href="${pageContext.request.contextPath}/member/memberList" class="w3-bar-item w3-button w3-hide-small">회원리스트</a>
 <% } %>
 
    <div class="w3-dropdown-hover">
      <button class="w3-button">
        게시판 <i class="fa fa-caret-down"></i>
      </button>
      <div class="w3-dropdown-content w3-bar-block w3-white w3-card-4">
        <a href="${pageContext.request.contextPath}/board/boardList?boardid=1" class="w3-bar-item w3-button w3-text-black">공지사항</a>
        <a href="${pageContext.request.contextPath}/board/boardList?boardid=2" class="w3-bar-item w3-button w3-text-black">자유게시판</a>
        <a href="${pageContext.request.contextPath}/board/boardList?boardid=3" class="w3-bar-item w3-button w3-text-black">QnA</a>
      </div>
    </div>
    <a href="javascript:void(0)" class="w3-bar-item w3-button w3-right"><i class="fa fa-search"></i></a>
  </div>
</body>
</html>