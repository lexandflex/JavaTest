package web.auth;

import domain.User;
import service.ServiceException;
import service.UserService;
import web.Action;
import web.ActionResult;
import web.ActionResultType;
import web.Menu;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

public class LoginActionImpl implements Action {
	private UserService userService;
	
	@Override
	public ActionResult exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String login = req.getParameter("login");
        String password = req.getParameter("password");
        if(login != null && password != null) {
        	/* условие выполняется, если сервлету была передана
             * форма авторизации */
            try {
				User user = userService.find(login, password);
                if(user != null) {
                    HttpSession session = req.getSession();
					session.setAttribute("currentUser", user);
                    session.setAttribute("menu", Menu.getMenu(user.getRole().getName()));
                    return new ActionResult("/index.html", ActionResultType.REDIRECT);
                } else {
                	String message = "Username or password not recognized";
                	// JSP: param['message']
                    //String url = /*req.getContextPath() + */ "/login.html?message="
                    //           + URLEncoder.encode(message, "UTF-8");
                    //return new ActionResult(url, ActionResultType.REDIRECT);
                	req.setAttribute("message", message);               	
                	return null;
                }
            } catch(ServiceException e) {
    			throw new ServletException(e);
    		}
        } else {
        	String queryString = req.getQueryString();
        	if(queryString != null) {    	
        		String queryStringEncoded = URLDecoder.decode(queryString, "UTF-8");        
        		String message = null;
        		if(queryStringEncoded.indexOf("message") != -1) message = queryStringEncoded.substring(queryStringEncoded.indexOf("message=") + 8, queryStringEncoded.indexOf("&") == -1 ? queryStringEncoded.length() :  queryStringEncoded.indexOf("&"));    	
        		req.setAttribute("message", message);
        	}   	
			// req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            // URLDecoder.decode(req.getParameter("message"), "UTF-8");
        	return null;
        }
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
