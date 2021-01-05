package web.chair;

import service.ChairService;
import service.ServiceException;
import web.Action;
import web.ActionResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class ChairDeleteActionImpl implements Action {
	private ChairService service;

	@Override
	public ActionResult exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String facultyId = req.getParameter("facultyId");
		if(req.getParameterValues("id") == null) {
			if(facultyId != null)
	    		return new ActionResult("/chair/index.html?facultyId=" + URLEncoder.encode(facultyId, "UTF-8"));
			return new ActionResult("/chair/index.html");
		}
		
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
		
    	if(facultyId != null)
    		return new ActionResult("/chair/index.html?facultyId=" + URLEncoder.encode(facultyId, "UTF-8"));
		return new ActionResult("/chair/index.html");
	}

	public void setChairService(ChairService service) {
		this.service = service;
	}
}
