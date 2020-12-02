package gr.nikolis.handlers;

import gr.nikolis.mappings.login.LoginMappings;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoggingAuthenticationHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        if (exception.getClass().equals(DisabledException.class))
            response.sendRedirect(request.getContextPath() + LoginMappings.USER_DISABLED);
        else
            response.sendRedirect(request.getContextPath() + LoginMappings.LOGIN);

        /*Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("status", HttpStatus.FORBIDDEN.value());
        data.put("message", "Access Denied");
        data.put("path", request.getRequestURL().toString());
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, data);
        out.flush();
        response.sendRedirect(request.getContextPath());*/
    }
}
