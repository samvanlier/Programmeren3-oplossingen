package be.kdg.blog.servlet;

import com.sun.deploy.net.HttpResponse;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

//@WebFilter(urlPatterns = {"/blog", "/api/blog/posts"}, filterName = "limitingFilter")
public class BlogRateLimitingFilter implements Filter {

    private HashMap<String, Long> lastCallPerIp;

    private final int TIMEFRAME = 3000; //tijd in ms


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        lastCallPerIp = new HashMap<>();
        long currentTime = System.currentTimeMillis();
        String ipClient = request.getRemoteAddr();
        long lastCallTime = lastCallPerIp.get(ipClient);

        if ((currentTime - lastCallTime)  <= TIMEFRAME){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(429);
        }

        lastCallPerIp.put(ipClient, currentTime);
    }

    @Override
    public void destroy() {
        //
    }
}
