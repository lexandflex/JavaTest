package web.user;

import domain.User;
import service.ServiceException;
import service.UserService;
import util.UserSortState;
import web.Action;
import web.ActionResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserIndexActionImpl implements Action {
	private UserService service;

	@Override
	public ActionResult exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String sortStateStr = req.getParameter("sortState");
			UserSortState sortState = null;
			if(sortStateStr != null) {
				try {
					sortState = UserSortState.valueOf(sortStateStr);
				} catch (IllegalArgumentException e) {};
			}
			
			req.setAttribute("loginSort", sortState == UserSortState.LoginAsc ? UserSortState.LoginDesc : UserSortState.LoginAsc);
			req.setAttribute("roleSort", sortState == UserSortState.RoleAsc ? UserSortState.RoleDesc : UserSortState.RoleAsc);			
			req.setAttribute("currentSort", null);
			
			List<User> users;
			if(sortState != null) {				
				req.setAttribute("currentSort", sortState);
				users = service.findAll(sortState);		
			} 
			else
				users = service.findAll(null);
			req.setAttribute("users", users);
			return null;
		} catch(ServiceException e) {
			throw new ServletException(e);
		}
	}

	public void setUserService(UserService service) {
		this.service = service;
	}
}