package com.kenti.antezana.sistema_de_gestion_de_reservas.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenti.antezana.sistema_de_gestion_de_reservas.exception.TokenNotFoundException;
import com.kenti.antezana.sistema_de_gestion_de_reservas.exception.handler.GlobalExceptionHandler;
import com.kenti.antezana.sistema_de_gestion_de_reservas.security.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtAuthFilter extends OncePerRequestFilter {
    private final String[] whiteListedUrls;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try{
            if(shouldNotFilter(request)){
                filterChain.doFilter(request, response);
                return;
            }
            String token = getToken(request);
            if(token == null)
                throw new TokenNotFoundException("Token not found");

            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtService.extractUsername(token));
            boolean tokenExists = tokenService.exists(token);

            if(jwtService.isValid(userDetails,token) && tokenExists && SecurityContextHolder.getContext().getAuthentication() == null){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch(Exception e){
            sendErrorResponse(e, response, 401);
            return;
        }
        filterChain.doFilter(request, response);
    }


    public String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer "))
            return header.substring(7);
        return null;
    }

    public void sendErrorResponse(Exception e, HttpServletResponse response, int errorCode) throws IOException {
        String description = null;
        if(e instanceof io.jsonwebtoken.ExpiredJwtException)
            description = "Token is expired";
        else if (e instanceof io.jsonwebtoken.MalformedJwtException)
            description = "Token is malformed";
        else if (e instanceof io.jsonwebtoken.UnsupportedJwtException)
            description = "Token is unsupported";
        else if (e instanceof TokenNotFoundException)
            description = "Token not found";

        log.error(e.getMessage());

        GlobalExceptionHandler.ExceptionRes<?> bodyResponse = new GlobalExceptionHandler.ExceptionRes<>(401,"Access Denied",description, null);
        response.setStatus(errorCode);
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(bodyResponse));
    }

    public boolean shouldNotFilter(HttpServletRequest request) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        String uri = request.getRequestURI();
        for (String path : whiteListedUrls) {
            if (pathMatcher.match(path, uri)) {
                return true;
            }
        }
        return false;
    }


}
