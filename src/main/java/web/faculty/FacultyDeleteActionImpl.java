package web.faculty;

import service.FacultyService;
import service.ServiceException;
import web.Action;
import web.ActionResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FacultyDeleteActionImpl implements Action {
	private FacultyService service;

	@Override
	public ActionResult exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getParameterValues("id") == null)
			return new ActionResult("/faculty/index.html");
		
		for(String idStr : req.getParameterValues("id")) {
			Long id = null;
			try {
				id = Long.parseLong(idStr);
				try {
					service.delete(id);
				} catch(ServiceException e) {
					throw new ServletException(e);
				}
			} catch(NumberFormatException e) {};
		}         
		return new ActionResult("/faculty/index.html");
	}

	public void setFacultyService(FacultyService service) {
		this.service = service;
	}

}
