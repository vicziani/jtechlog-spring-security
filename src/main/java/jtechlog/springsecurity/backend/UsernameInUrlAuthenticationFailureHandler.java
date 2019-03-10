package jtechlog.springsecurity.backend;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UsernameInUrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public static final String LAST_USERNAME_KEY = "LAST_USERNAME";

    public UsernameInUrlAuthenticationFailureHandler() {
        super("/login?error");
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {

        String usernameParameter =
                UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;
        String lastUserName = request.getParameter(usernameParameter);

        HttpSession session = request.getSession(false);
        if (session != null || isAllowSessionCreation()) {
            request.getSession().setAttribute(LAST_USERNAME_KEY, lastUserName);
        }

        super.onAuthenticationFailure(request, response, exception);
    }
}
