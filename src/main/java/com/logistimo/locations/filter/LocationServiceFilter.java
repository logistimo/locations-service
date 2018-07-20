package com.logistimo.locations.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by kumargaurav on 17/07/17.
 */
@Component
public class LocationServiceFilter extends GenericFilterBean {

    private static final String[] APPS = {"logistimo", "tusker", "gateway"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        //check app-name header atleast
        String security = req.getHeader("x-app-name");
        if (security == null || !Arrays.asList(APPS).contains(security)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json");
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Required headers not specified in the request");
            return;
        }
        chain.doFilter(request, response);
    }
}
