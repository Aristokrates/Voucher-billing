package org.pan.voucher.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.pan.voucher.model.User;
import org.pan.voucher.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private static final Logger log = LoggerFactory.getLogger(LoginAuthenticationSuccessHandler.class);
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
    
        super.onAuthenticationSuccess(request, response, authentication);

        User user = (User) authentication.getDetails();
        String username = authentication.getName();

        HttpSession session = request.getSession();
        setSessionData(session, user, username);

        log.debug("User {} has been successfully authenticated, user Id: {}", username, user);
    
    }

	private void setSessionData(HttpSession session, User user, String username) {
	
        session.setAttribute(WebConstants.USER, user);    
        session.setAttribute(WebConstants.USER_ID, user.getId());  
	}
}
