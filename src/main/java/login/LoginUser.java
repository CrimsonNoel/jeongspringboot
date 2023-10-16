package login;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
// public class LoginUser extends HandlerInterceptorAdapter {
public class LoginUser implements HandlerInterceptor {    
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String login=(String)request.getSession().getAttribute("id");
		if(login==null) {
			response.sendRedirect(request.getContextPath()+"/member/alert?id=login");
			return false; 
		} else {
		return true; }
	}
	
	
	

}
