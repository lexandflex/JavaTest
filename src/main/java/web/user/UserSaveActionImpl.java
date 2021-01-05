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

public class UserSaveActionImpl implements Action {
	private UserService userService;
	private RoleService roleService;

	@Override
	public ActionResult exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			User user = getUser(req);
			userService.save(user);
			return new ActionResult("/user/index.html");
		} catch(ServiceException e) {
			throw new ServletException(e);
		} catch(IllegalArgumentException e) {
			req.setAttribute("message", e.getMessage());
			return new ActionResult("/error", ActionResultType.FORWARD);
			
			//String url = /*req.getContextPath() + */ "/error.html?message="
			//		+ URLEncoder.encode(e.getMessage(), "UTF-8");
			//return new ActionResult(url, ActionResultType.REDIRECT);
		}
	}

	public void setUserService(UserService service) {
		this.userService = service;
	}
	
	public void setRoleService(RoleService service) {
		this.roleService = service;
	}

	private User getUser(HttpServletRequest req) throws ServiceException {
		User user = new User();
		String errorMessage = new String();
		
		try {
			user.setId(Long.parseLong(req.getParameter("id")));
		} catch(NumberFormatException e) {}
		
		user.setLogin(req.getParameter("login"));
		user.setPassword(req.getParameter("password"));	
		try {
			user.setRole(roleService.findById(Long.parseLong(req.getParameter("roleId"))));
		} catch (NumberFormatException e) {}
		
		if(user.getLogin() == null || user.getLogin().isEmpty())
			errorMessage += "Не заполнено поле \"Логин\"<br>";
		if(user.getPassword() == null || user.getPassword().isEmpty())
			errorMessage += "Не заполнено поле \"Пароль\"<br>";
		if(user.getRole() == null)
			errorMessage += "Не заполнено поле \"Роль\"<br>";
		
		if(errorMessage.length() != 0)
			throw new IllegalArgumentException(errorMessage);		
		return user;
	}
}