package org.example.configuration;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Инициализация не требуется
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Разрешаем запросы с любого домена
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");

        // Разрешаем определенные HTTP-методы
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        // Разрешаем определенные заголовки
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // Разрешаем кеширование предварительных запросов (preflight) на 1 час
        httpResponse.setHeader("Access-Control-Max-Age", "3600");

        // Продолжаем цепочку фильтров
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Очистка не требуется
    }
}
