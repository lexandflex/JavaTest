package web;

import ioc.IoCContainer;
import ioc.IoCException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		String context = req.getContextPath();
		url = url.substring(context.length());
		int position = url.lastIndexOf(".html");
		if(position != -1) {
			url = url.substring(0, position);
		}
		else if (url.compareTo("/") == 0) {
			url = "/index";
		}
		ActionResult actionResult = null;
		try(IoCContainer ioc = new IoCContainer()) {
			Action action = ioc.get(Action.class, url);
			if(action != null) {
				actionResult = action.exec(req, resp);
			}
		} catch(IoCException e) {
			throw new ServletException(e);
		}
		if(actionResult != null && actionResult.getType() == ActionResultType.REDIRECT) {
			resp.sendRedirect(new StringBuilder(context).append(actionResult.getUrl()).toString());
		} else {
			if(actionResult != null) {
				url = actionResult.getUrl();
			}
			req.getRequestDispatcher(new StringBuilder("/WEB-INF/jsp").append(url).append(".jsp").toString()).forward(req, resp);
		}
	}
}
