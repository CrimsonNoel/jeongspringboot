package login;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;



@Component
//public class LoginAdmin extends HandlerInterceptorAdapter {
public class LoginAdmin implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String login = (String) request.getSession().getAttribute("id");
			
		if (login == null || !login.equals("admin")) {
			response.sendRedirect(request.getContextPath()+"/member/alert?id=admin");
			return false;
		} else {
			return true;
		}
	}
		

}
