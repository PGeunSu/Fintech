package com.zerobase.Fintech.jwt.filter;

import com.zerobase.Fintech.jwt.config.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    //Jwt 토큰 추출
    String token = resolveToken((HttpServletRequest) request);

    //유효성 검사
    if (token != null && jwtTokenProvider.validateToken(token)){
      //유효할 경우 객체를 가지고와서 SecurityContext 에 저장
      Authentication authentication = jwtTokenProvider.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    chain.doFilter(request, response);
  }

  private String resolveToken(HttpServletRequest request){
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
      return bearerToken.substring(7);
    }

    return null;
  }
}
