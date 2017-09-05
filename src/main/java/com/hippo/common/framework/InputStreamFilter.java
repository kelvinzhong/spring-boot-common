package com.hippo.common.framework;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class InputStreamFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

		if (!StringUtils.isEmpty(request.getParameter("sign")))
			chain.doFilter(request, response);

		ServletRequest requestWrapper = new InputStreamHttpServletRequestWrapper((HttpServletRequest) request);

		chain.doFilter(requestWrapper, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
