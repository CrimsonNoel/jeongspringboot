
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html><html>
<head><meta charset="UTF-8">
<title>Insert title here</title>
</head><body><br>
<div class="w3-container">
<table class="w3-table-all">
<caption>회원정보확인[${sessionScope.id}]</caption>
<tr><td>아이디</td><td>${m.id}  </td></tr>
<tr><td>이름</td><td>${m.name} </td></tr>
<tr><td>성별</td><td>${m.gender==1 ? "남" : "여"}  </td></tr>
<tr><td>전화번호</td><td>${m.tel}  </td></tr>
<tr><td>이메일</td><td>${m.email} </td></tr>
<tr>
		<td colspan="2" class="w3-center">
		<a class="w3-button w3-black" 
		href="${pageContext.request.contextPath}/member/memberUpdateForm">회원정보수정</a>
		<a class="w3-button w3-black" 
		href="${pageContext.request.contextPath}/member/memberDeleteForm">회원탈퇴</a>
		<a class="w3-button w3-black" 
		href="${pageContext.request.contextPath}/member/memberPassForm">비밀번호수정</a>
		</td>
			</tr>
</table></div>
</body></html>