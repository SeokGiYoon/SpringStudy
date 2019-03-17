package net.seokgi.web;

import javax.servlet.http.HttpSession;

import net.seokgi.domain.User;

public class HttpSessionUtills {
	public static final String USER_SESSION_KEY = "sessionUser";
	
	public static boolean isLoginUser(HttpSession session) {
		Object sessionUser = session.getAttribute(USER_SESSION_KEY);
		if(sessionUser == null) {
			return false;
		}
		return true;
	}
	
	public static User getUserFromSession(HttpSession session) {
		if(!isLoginUser(session)) {
			return null;
		}
		
		return (User)session.getAttribute(USER_SESSION_KEY);
	}
}
