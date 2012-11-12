package org.pan.voucher.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.pan.voucher.exception.InvalidDataException;
import org.pan.voucher.exception.InvalidSessionTokenException;
import org.pan.voucher.exception.InvalidUserCredentials;
import org.pan.voucher.exception.UserNotEligibleToBuyException;
import org.pan.voucher.exception.UserPermissionException;
import org.pan.voucher.exception.VoucherNotFoundException;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserTypeName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public abstract class AbstractWebController {
	
	private static final Logger log = LoggerFactory.getLogger(AbstractWebController.class);

	@ExceptionHandler({UserNotEligibleToBuyException.class})
	@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="User not eligible to buy the product")
	public void handleUserNotEligibleToBuyException(Throwable exception, HttpServletResponse response) { 
		log.debug("User not eligible to buy the product: " + exception);
	}

	@ExceptionHandler({VoucherNotFoundException.class})
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Voucher not found")
	public void handleVoucherNotFoundException(Throwable exception, HttpServletResponse response) { 
		log.debug("Voucher not found: " + exception);
	}

	@ExceptionHandler({InvalidUserCredentials.class})
	@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="Invalid user credentials")
	public void handleInvalidUserCredentialsException(Throwable exception, HttpServletResponse response) { 
		log.debug("Invalid user credentials: " + exception);
	}
	
	@ExceptionHandler({InvalidSessionTokenException.class})
	@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="Invalid session token")
	public void handleInvalidSessionTokenException(Throwable exception, HttpServletResponse response) { 
		log.debug("Invalid session token: " + exception);
	}
	
	@ExceptionHandler({UserPermissionException.class})
	@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="User does not have necessary permision")
	public void handleUserPermissionException(Throwable exception, HttpServletResponse response) { 
		log.debug("User does not have necessary permision: " + exception);
	}
	
	@ExceptionHandler({InvalidDataException.class})
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid data sent")
	public void handleInvalidUserDataException(Throwable exception, HttpServletResponse response) { 
		log.debug("Invalid data: " + exception);
	}
	
	@ExceptionHandler({IllegalArgumentException.class})
	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Ilegal argument exception")
	public void handleIllegalArgumentExceptionException(Throwable exception, HttpServletResponse response) { 
		log.debug("Ilegal argument exception: " + exception);
	}
	
	@ExceptionHandler({RuntimeException.class})
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Server error")
	public void handleGeneralException(Throwable exception, HttpServletResponse response) { 
		log.debug("Internal server error: " + exception);
	}
	
	protected void setupMenuPrivilegues(ModelMap modelMap) {
		User loginUser = getUser();
		if (UserTypeName.getAdminUserTypes().contains(loginUser.getUserType().getName())) {
			modelMap.addAttribute("admin", true);
		}
		
		if (UserTypeName.getSuperAdminUserTypes().contains(loginUser.getUserType().getName())) {
			modelMap.addAttribute("superadmin", true);
		}
		
		modelMap.addAttribute("userBalance", loginUser.getBalance());
	}

    protected User getUser() {    	
		return (User) getSessionAttribute(WebConstants.USER);    	
    }
    
    protected Integer getUserId() {    	
		return (Integer) getSessionAttribute(WebConstants.USER_ID);    	
    }

    protected Object getSessionAttribute(String name) {
    	return RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
    }

    protected void setSessionAttribute(String name, Object value) {
    	RequestContextHolder.getRequestAttributes().setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
    }
    
    protected List<String> getListOfCountries() {
	    List<String> countries = new ArrayList<String>();

	    Locale[] locales = Locale.getAvailableLocales();
	    for (Locale locale : locales) {
	      String name = locale.getDisplayCountry();

	      if (!"".equals(name)) {
	        countries.add(name);
	      }
	    }

	    Collections.sort(countries);
	    return countries;
    }
    

	protected void setSessionData(HttpSession session, User user, String deviceShortType) {

		session.setAttribute(WebConstants.USER, user);
		session.setAttribute(WebConstants.USER_ID, user.getId());
		session.setAttribute(WebConstants.DEVICE_TYPE, deviceShortType);
	}
	
	protected void setSessionData(HttpSession session, User user) {

		session.setAttribute(WebConstants.USER, user);
		session.setAttribute(WebConstants.USER_ID, user.getId());
	}
}
