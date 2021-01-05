package web.auth;

import web.Action;
import web.ActionResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutActionImpl implements Action {
	
	@Override
	public ActionResult exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
        if(session != null) {
            session.removeAttribute("user");
			session.removeAttribute("menu");
            session.invalidate();
        }
        return new ActionResult("/index.html");
	}
}
