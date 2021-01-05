package web.chair;

import domain.Chair;
import domain.Faculty;
import service.ChairService;
import service.FacultyService;
import service.ServiceException;
import util.ChairSortState;
import web.Action;
import web.ActionResult;
import web.ActionResultType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ChairIndexActionImpl implements Action {
	private FacultyService facultyService;
	private ChairService chairService;

	@Override
	public ActionResult exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long facultyId = null;
		try {
			facultyId = Long.parseLong(req.getParameter("facultyId"));
		} catch(NumberFormatException e) {
			req.setAttribute("message", "Не указан id факультета");
			return new ActionResult("/error", ActionResultType.FORWARD);
		}
			
		try {
			// Получаем факультет
			Faculty faculty = facultyService.findById(facultyId);
			if(faculty == null) {
				req.setAttribute("message", "Не найден факультет с таким id");
				return new ActionResult("/error", ActionResultType.FORWARD);
			}
			
			req.setAttribute("faculty", faculty);
			// В select добавляем факультеты
			req.setAttribute("faculties", facultyService.findAll(null));
			
			String sortStateStr = req.getParameter("sortState");
			ChairSortState sortState = null;
			if(sortStateStr != null) {
				try {
					sortState = ChairSortState.valueOf(sortStateStr);
				} catch (IllegalArgumentException e) {};
			}
			
			req.setAttribute("titleSort", sortState == ChairSortState.TitleAsc ? ChairSortState.TitleDesc : ChairSortState.TitleAsc);
			req.setAttribute("fullNameSort", sortState == ChairSortState.FullNameAsc ? ChairSortState.FullNameDesc : ChairSortState.FullNameAsc);
			req.setAttribute("phoneNumberSort", sortState == ChairSortState.PhoneNumberAsc ? ChairSortState.PhoneNumberDesc : ChairSortState.PhoneNumberAsc);
			req.setAttribute("cabinetNumberSort", sortState == ChairSortState.CabinetNumberAsc ? ChairSortState.CabinetNumberDesc : ChairSortState.CabinetNumberAsc);
			req.setAttribute("teachersCountSort", sortState == ChairSortState.TeachersCountAsc ? ChairSortState.TeachersCountDesc : ChairSortState.TeachersCountAsc);
			req.setAttribute("releaseSort", sortState == ChairSortState.ReleaseAsc ? ChairSortState.ReleaseDesc : ChairSortState.ReleaseAsc);
			req.setAttribute("dateOfCreateSort", sortState == ChairSortState.DateOfCreateAsc ? ChairSortState.DateOfCreateDesc : ChairSortState.DateOfCreateAsc);
			req.setAttribute("currentSort", null);
				
			List<Chair> chairs;
			if(sortState != null) {				
				req.setAttribute("currentSort", sortState);
				chairs = chairService.findByFaculty(faculty.getId(), sortState);		
			} 
			else
				chairs = chairService.findByFaculty(faculty.getId(), null);
			req.setAttribute("chairs", chairs);
			return null;
		} catch(ServiceException e) {
			throw new ServletException(e);
		}
	}

	public void setChairService(ChairService service) {
		this.chairService = service;
	}
	
	public void setFacultyService(FacultyService service) {
		this.facultyService = service;
	}
}
