package web;

import ioc.IoCConfigurer;
import ioc.IoCException;
import pool.ConnectionPool;
import pool.PoolException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationStartListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			IoCConfigurer.configure();
			ServletContext context = servletContextEvent.getServletContext();
			String jdbcDriver = context.getInitParameter("jdbc-driver");
			String jdbcUrl = context.getInitParameter("jdbc-url");
			String jdbcUser = context.getInitParameter("jdbc-user");
			String jdbcPassword = context.getInitParameter("jdbc-password");
			int poolMinSize = Integer.parseInt(context.getInitParameter("pool-min-size"));
			int poolMaxSize = Integer.parseInt(context.getInitParameter("pool-max-size"));
			int poolConnectionValidationTimeout = Integer.parseInt(context.getInitParameter("pool-connection-validation-timeout"));
			ConnectionPool.getInstance().init(jdbcDriver, jdbcUrl, jdbcUser, jdbcPassword, poolMinSize, poolMaxSize, poolConnectionValidationTimeout);
		} catch(PoolException | IoCException | NumberFormatException e) {
			e.printStackTrace(System.out);
			//throw new ServletException(e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ConnectionPool.getInstance().destroy();
	}
}
