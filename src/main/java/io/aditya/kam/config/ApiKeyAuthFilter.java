package io.aditya.kam.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;


public class ApiKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {
  private String principalRequestHeader;

  public ApiKeyAuthFilter(String principalRequestHeader) {
    this.principalRequestHeader = principalRequestHeader;
  }

  // This returns the value of API key in the header
  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    return request.getHeader(principalRequestHeader);
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return null;
  }
}
