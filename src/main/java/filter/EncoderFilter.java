package filter;

import javax.servlet.*;
import java.io.IOException;

public class EncoderFilter implements Filter {
    private String encoding = null;

    @Override
    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("http-request-encoding");
        if(encoding == null) {
            throw new ServletException("init parameter \"http-request-encoding\" is absent");
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding(encoding);
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {}
}
