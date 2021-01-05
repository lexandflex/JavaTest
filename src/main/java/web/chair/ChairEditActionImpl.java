package web.chair;

import domain.Chair;
import domain.Faculty;
import service.ChairService;
import service.FacultyService;
import service.ServiceException;
import web.Action;
import web.ActionResult;
import web.ActionResultType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ChairEditActionImpl implements Action {
	private ChairService chairService;
	private FacultyService facultyService;

	@Override
	public ActionResult exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = null;
		Long facultyId = null;
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch(NumberFormatException e) {}
		try {
			facultyId = Long.parseLong(req.getParameter("facultyId"));
		} catch(NumberFormatException e) {}
		
		try {		
			// Редактирование кафедры
			if(id != null) {
				List<Faculty> faculties = facultyService.findAll(null);
				req.setAttribute("faculties", faculties);
				
				Chair chair = chairService.findById(id);
				if(chair == null) {
					String message = "Не найдена кафедра с таким id";
					req.setAttribute("message", message);
					return new ActionResult("/error", ActionResultType.FORWARD);
				}
				req.setAttribute("chair", chair);
			}
			// Если добавление то передается id факультета
			else if (facultyId != null) {
				Faculty faculty = facultyService.findById(facultyId);
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

	public void setChairService(ChairService service) {
		this.chairService = service;
	}
	
	public void setFacultyService(FacultyService service) {
		this.facultyService = service;
	}
}
