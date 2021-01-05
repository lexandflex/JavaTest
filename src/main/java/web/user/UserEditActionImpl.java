package web.user;

import domain.User;
import service.RoleService;
import service.ServiceException;
import service.UserService;
import web.Action;
import web.ActionResult;
import web.ActionResultType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserEditActionImpl implements Action {
	private UserService userService;
	private RoleService roleService;

	@Override
	public ActionResult exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = null;
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch(NumberFormatException e) {}
		
		try {
			req.setAttribute("roles", roleService.findAll());
			if(id != null) {
				User user = userService.findById(id);
				if(user == null) {
					String message = "Не найден пользователь с таким id";
					req.setAttribute("message", message);
					return new ActionResult("/error", ActionResultType.FORWARD);
					
					//String url = /*req.getContextPath() + */ "/error.html?message="
					//		+ URLEncoder.encode(message, "UTF-8");
					//return new ActionResult(url, ActionResultType.REDIRECT);
				}
				req.setAttribute("user", user);
			}
		} catch(ServiceException e) {
			throw new ServletException(e);
		}
		return null;
	}

	public void setUserService(UserService service) {
		this.userService = service;
	}
	
	public void setRoleService(RoleService service) {
		this.roleService = service;
	}
}

