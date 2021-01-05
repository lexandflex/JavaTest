package filter;

import domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

public class SecurityFilter implements Filter {
    private static Map<String, Set<String>> permissions = new HashMap<>();
    static {
        permissions.put("/index.html", null);
        permissions.put("/login.html", null);
        
        permissions.put("/user/index.html", new HashSet<>(Arrays.asList("admin")));
        permissions.put("/user/edit.html", new HashSet<>(Arrays.asList("admin")));
        permissions.put("/user/save.html", new HashSet<>(Arrays.asList("admin")));
        permissions.put("/user/delete.html", new HashSet<>(Arrays.asList("admin")));
        
        permissions.put("/teacher/index.html", null);
        permissions.put("/teacher/edit.html", new HashSet<>(Arrays.asList("methodist")));
        permissions.put("/teacher/save.html", new HashSet<>(Arrays.asList("methodist")));
        permissions.put("/teacher/delete.html", new HashSet<>(Arrays.asList("methodist")));
        
        permissions.put("/course/index.html", null);
        permissions.put("/course/edit.html", new HashSet<>(Arrays.asList("methodist")));
        permissions.put("/course/save.html", new HashSet<>(Arrays.asList("methodist")));
        permissions.put("/course/delete.html", new HashSet<>(Arrays.asList("methodist")));
    }

    @Override
    public void init(FilterConfig config) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        
        if(hasAccess(request, response)) {
            chain.doFilter(req, resp);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.html?message=" + URLEncoder.encode("Доступ запрещен. У вас нету доступа к этому ресурсу.", "UTF-8"));
        }
    }

    private boolean hasAccess(HttpServletRequest req, HttpServletResponse resp) {
        String url = req.getRequestURI().substring(req.getContextPath().length());
        Set<String> roles = permissions.get(url);
        if(roles != null) {
            HttpSession session = req.getSession(false);
            if(session != null) {
                User currentUser = (User)session.getAttribute("currentUser");
                return currentUser != null && roles.contains(currentUser.getRole().getName());
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void destroy() {}
}
