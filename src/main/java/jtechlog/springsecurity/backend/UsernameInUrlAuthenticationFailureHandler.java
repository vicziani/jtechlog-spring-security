package jtechlog.springsecurity.backend;

import org.springframework.beans.factory.annotation.Autowired;
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

    private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

    @Autowired
    public UsernameInUrlAuthenticationFailureHandler(UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter) {
        this.usernamePasswordAuthenticationFilter = usernamePasswordAuthenticationFilter;
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {

        super.onAuthenticationFailure(request, response, exception);

        String usernameParameter =
                usernamePasswordAuthenticationFilter.getUsernameParameter();
        String lastUserName = request.getParameter(usernameParameter);

        HttpSession session = request.getSession(false);
        if (session != null || isAllowSessionCreation()) {
            request.getSession().setAttribute(LAST_USERNAME_KEY, lastUserName);
        }
    }
}
