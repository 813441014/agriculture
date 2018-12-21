package com.qpp.eureka.filter;

import javax.servlet.*;
import java.io.IOException;


public class RequestAuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        //业务实现，根据请求的IP或者参数判断是否可以执行注册或者访问
        filterChain.doFilter(request, response);
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}