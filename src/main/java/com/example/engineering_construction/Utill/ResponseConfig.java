package com.example.engineering_construction.Utill;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ResponseConfig extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.addHeader("X-XSS-Protection", "1;mode=block");
        httpServletResponse.addHeader("X-Frame-Options","sameorign");
        httpServletResponse.addHeader("X-Content-Type-Options","nosniff");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
