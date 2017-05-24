package edu.msg.flightManagement.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "FManagerFilter", urlPatterns = { "/flighttemplate.xhtml", "/manageflights.xhtml",
		"/flightHistory.xhtml", "/crewassignment.xhtml"})
public class FManagerFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		if (session != null && session.getAttribute("userType") != null) {
			String userType = session.getAttribute("userType").toString();
			if (userType.equals("company manager") || userType.equals("administrator")
					|| userType.equals("flight manager")) {
				chain.doFilter(request, response);
			} else {
				res.sendRedirect(req.getContextPath() + "/index.xhtml");
			}
		} else {
			res.sendRedirect(req.getContextPath() + "/login.xhtml");
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
