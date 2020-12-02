package gr.nikolis.handlers;

import gr.nikolis.mappings.login.LoginMappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoggingAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Handles an access denied failure.
     * If a user is trying to access a protected page and has no wrights,
     * this exception is thrown
     *
     * @param request that resulted in an <code>AccessDeniedException</code>
     * @param response so that the user agent can be advised of the failure
     * @param ex that caused the invocation
     *
     * @throws IOException in the event of an IOException
     * @throws ServletException in the event of a ServletException
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException ex) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            log.info(auth.getName()
                    + " was trying to access protected resource: "
                    + request.getRequestURI());
        }
        //redirect user to specified page
        response.sendRedirect(request.getContextPath() + LoginMappings.ACCESS_DENIED);
    }
}
