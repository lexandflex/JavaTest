package web.faculty;

import domain.Faculty;
import service.FacultyService;
import service.ServiceException;
import web.Action;
import web.ActionResult;
import web.ActionResultType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FacultyEditActionImpl implements Action {
	private FacultyService service;

	@Override
	public ActionResult exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = null;
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch(NumberFormatException e) {}
		
		try {
			if(id != null) {
				Faculty faculty = service.findById(id);
				if(faculty == null) {
					String message = "Не найден факультет с таким id";
					req.setAttribute("message", message);
					return new ActionResult("/error", ActionResultType.FORWARD);
				}
				req.setAttribute("faculty", faculty);
			}
		} catch(ServiceException e) {
			throw new ServletException(e);
		}
		return null;
	}

	public void setFacultyService(FacultyService service) {
		this.service = service;
	}
}
