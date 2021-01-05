package web.chair;

import domain.Chair;
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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ChairSaveActionImpl implements Action {
	private ChairService chairService;
	private FacultyService facultyService;

	@Override
	public ActionResult exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Chair chair = getChair(req);
			chairService.save(chair);
			
			String returnUrl = req.getParameter("returnUrl");
			if(returnUrl != null)
				return new ActionResult(returnUrl);
		   	return new ActionResult("/chair/index.html?facultyId=" + chair.getFaculty().getId());
		} catch(ServiceException e) {
			throw new ServletException(e);
		} catch(IllegalArgumentException e) {
			req.setAttribute("message", e.getMessage());
			return new ActionResult("/error", ActionResultType.FORWARD);
		}
	}

	public void setChairService(ChairService service) {
		this.chairService = service;
	}
	
	public void setFacultyService(FacultyService service) {
		this.facultyService = service;
	}

	private Chair getChair(HttpServletRequest req) throws ServiceException {
		Chair chair = new Chair();
		String errorMessage = new String();
		
		try {
			chair.setId(Long.parseLong(req.getParameter("id")));
		} catch(NumberFormatException e) {}
		
		chair.setFaculty(facultyService.findById(Long.parseLong(req.getParameter("facultyId"))));
		chair.setTitle(req.getParameter("title"));
		chair.setFirstName(req.getParameter("firstName"));
		chair.setLastName(req.getParameter("lastName"));
		chair.setPhoneNumber(req.getParameter("phoneNumber"));
		chair.setCabinetNumber(req.getParameter("cabinetNumber"));
		
		try {
			chair.setTeachersCount(Integer.parseInt(req.getParameter("teachersCount")));	
		} catch(NumberFormatException e) {}
		
		chair.setRelease(req.getParameter("release") != null);
		
		if(chair.getTitle() == null || chair.getTitle().isEmpty()) 
			errorMessage += "Не заполнено поле \"Название\"<br>";
		if(chair.getFaculty() == null)
			errorMessage += "Не выбран факультет<br>";
		if(chair.getFirstName() == null || chair.getFirstName().isEmpty()) 
			errorMessage += "Не заполнено поле \"Имя\"<br>";
		if(chair.getLastName() == null || chair.getLastName().isEmpty()) 
			errorMessage += "Не заполнено поле \"Отчество\"<br>";
		if(chair.getPhoneNumber() == null || chair.getPhoneNumber().isEmpty()) 
			errorMessage += "Не заполнено поле \"Номер телефона\"<br>";
		if(chair.getCabinetNumber() == null || chair.getCabinetNumber().isEmpty()) 
			errorMessage += "Не заполнено поле \"Номер кабинета\"<br>";
		
		// Дата образования
        String dateOfCreateString = req.getParameter("dateOfCreate");
        if(dateOfCreateString.isEmpty()) errorMessage += "Не указана дата образования<br>";
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateOfCreate = null; 
        try {
        	if(!dateOfCreateString.isEmpty())
        		dateOfCreate = format.parse(dateOfCreateString);
		} catch (ParseException e) {
			errorMessage += "Неверная дата. Правильный формат даты: YYYY-MM-DD<br>";
		}
        
        if(dateOfCreate != null)
        	chair.setDOC(new Date(dateOfCreate.getTime())); // преобразуем util.Date в sql.Date
		
        if(errorMessage.length() != 0)
			throw new IllegalArgumentException(errorMessage);
        
        return chair;
	}
}
