package org.pan.voucher.web.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.pan.voucher.exception.InvalidUserCredentials;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserType;
import org.pan.voucher.model.UserTypeName;
import org.pan.voucher.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class UserAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private static final Logger log = LoggerFactory.getLogger(UserAuthenticationProvider.class);
	  
	@Resource private UserService userService;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {	
	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

        Assert.hasText(username);
        Assert.notNull(authentication.getCredentials());
        
        User loginUser;

        try {
        	
        	loginUser = userService.getUserByCredentials(username, (String) authentication.getCredentials());
        	authentication.setDetails(loginUser);

        } catch (InvalidUserCredentials e) {
            log.error("Exception", e);
			throw new BadCredentialsException("Bad credentials");
        } catch (IllegalArgumentException e) {
            log.error("Ilegal argument", e);
			throw new BadCredentialsException("Ilegal argument exception");
        }       

        return new org.springframework.security.core.userdetails.User(username, 
        		(String) authentication.getCredentials(), true, true, true, true, getAuthorities(loginUser.getUserType()));

	}
	
    private List<GrantedAuthority> getAuthorities(UserType userType) {
    	
    	String role;
    	if (UserTypeName.getAdminUserTypes().contains(userType.getName())) {
    		role = Roles.ADMIN.getRoleName();
    	} else {
    		role = Roles.USER.getRoleName();
    	}
    	
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthorityImpl(role));
        return authorities;
    }

}
