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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FacultySaveActionImpl implements Action {
	private FacultyService service;

	@Override
	public ActionResult exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Faculty faculty = getFaculty(req);
			try {
				service.save(faculty);
				return new ActionResult("/faculty/index.html");
			} catch(ServiceException e) {
				throw new ServletException(e);
			}
		} catch(IllegalArgumentException e) {
			req.setAttribute("message", e.getMessage());
			return new ActionResult("/error", ActionResultType.FORWARD);
		}
	}

	public void setFacultyService(FacultyService service) {
		this.service = service;
	}

	private Faculty getFaculty(HttpServletRequest req) {
		Faculty faculty = new Faculty();
		String errorMessage = new String();
		
		try {
			faculty.setId(Long.parseLong(req.getParameter("id")));
		} catch(NumberFormatException e) {}
		
		faculty.setTitle(req.getParameter("title"));
		faculty.setFirstName(req.getParameter("firstName"));
		faculty.setLastName(req.getParameter("lastName"));
		faculty.setPhoneNumber(req.getParameter("phoneNumber"));
		faculty.setDekanatCabinet(req.getParameter("dekanatCabinet"));
		
		try {
			faculty.setStudentsCount(Integer.parseInt(req.getParameter("studentsCount")));
		} catch(NumberFormatException e) {}
        
		if(faculty.getTitle() == null || faculty.getTitle().isEmpty()) 
			errorMessage += "Не заполнено поле \"Название\"<br>";
		if(faculty.getFirstName() == null || faculty.getFirstName().isEmpty()) 
			errorMessage += "Не заполнено поле \"Имя\"<br>";
		if(faculty.getLastName() == null || faculty.getLastName().isEmpty()) 
			errorMessage += "Не заполнено поле \"Отчество\"<br>";
		if(faculty.getPhoneNumber() == null || faculty.getPhoneNumber().isEmpty()) 
			errorMessage += "Не заполнено поле \"Номер телефона\"<br>";
		if(faculty.getDekanatCabinet() == null || faculty.getDekanatCabinet().isEmpty()) 
			errorMessage += "Не заполнено поле \"Номер кабинета деканата\"<br>";
		
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
        	faculty.setDOC(new Date(dateOfCreate.getTime())); // преобразуем util.Date в sql.Date
        
        if(errorMessage.length() != 0)
			throw new IllegalArgumentException(errorMessage);
		return faculty;
	}
}
