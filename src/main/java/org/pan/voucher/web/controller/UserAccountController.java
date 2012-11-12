package org.pan.voucher.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.pan.voucher.exception.InvalidUserCredentials;
import org.pan.voucher.mapper.UserMapperService;
import org.pan.voucher.model.User;
import org.pan.voucher.rest.model.UserRegisterModel;
import org.pan.voucher.service.UserService;
import org.pan.voucher.web.AbstractWebController;
import org.pan.voucher.web.WebConstants;
import org.pan.voucher.web.WebErrors;
import org.pan.voucher.web.controller.VoucherController.BuyProductViewPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Pance Isajeski
 */
@Controller
@RequestMapping("/user_account")
public class UserAccountController extends AbstractWebController {
	
	private static final Logger log = LoggerFactory.getLogger(UserAccountController.class);
	
	@Resource private UserService userService;
	@Resource private UserMapperService userHelperService;

	/**
	 * Show the login page with an optional error message.
	 *
	 * @param error Error mark which specify that error message will be displayed.
	 * @param model Model map with data to display on the page.
	 * @return Name of the view to render.
	 */
	@RequestMapping(value = "/error.do", method = RequestMethod.GET)
	public String connectError(HttpSession session, @RequestParam(value = "device", required = true) String device,
			@RequestParam(value = "errorType", required = true) Errors errorType, ModelMap modelMap) {

		modelMap.addAttribute("errorMessage", errorType.toString());

		return UserActionViewPage.ERROR_PAGE.getPageName(device);
	}

	/**
	 * Show the login page with an optional error message.
	 *
	 * @param error Error mark which specify that error message will be displayed.
	 * @param model Model map with data to display on the page.
	 * @return Name of the view to render.
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String showLogin(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "device", required = true) String device,
			@RequestParam(value = "lost_username", required = false) String lostUsername,
			ModelMap modelMap) {

		String view = UserActionViewPage.LOGIN_PAGE.getPageName(device);
		return view;
	}
	
	
    @RequestMapping(value = "/login_mobile.do", method = RequestMethod.GET)
    public String loginMobile(HttpSession session, @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "password", required = true) String password, ModelMap modelMap) {

    	User loginUser;
    	
        try {
        	
        	loginUser = userService.getUserByCredentials(username, password);
        	setSessionData(session, loginUser, type);
            
        } catch (InvalidUserCredentials e) {
            log.error("Exception", e);
			throw new BadCredentialsException("Bad credentials");
        } catch (IllegalArgumentException e) {
            log.error("Ilegal argument", e);
			throw new BadCredentialsException("Ilegal argument exception");
        }    
    	
        return "redirect:/" + BuyProductViewPage.VOUCHER.getActionUrl(type);
    }	
	
	/**
	 * Show the page after logout.
	 *
	 * @return Name of the view to render.
	 */
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session) {

		String deviceType = (String) session.getAttribute(WebConstants.DEVICE_TYPE);

		session.removeAttribute(WebConstants.DEVICE_TYPE);
		SecurityContextHolder.clearContext();

		session.invalidate();

		return UserActionViewPage.LOGIN_PAGE.getPageName(deviceType);
	}

	/**
	 * Show the register page.
	 *
	 * @param model Model map with data to display on the page.
	 * @return Name of the view to render.
	 */
	@RequestMapping(value = "/register.do", method = RequestMethod.GET)
	public String showRegister(HttpSession session, @RequestParam(value = "device", required = true) String device,
			ModelMap modelMap) {

		modelMap.addAttribute("userBean", new UserRegisterModel());
		modelMap.addAttribute("countries", getListOfCountries());
		
		return UserActionViewPage.REGISTER_PAGE.getPageName(device);
	}

	/**
	 * Register user.
	 *
	 * @param device String
	 * @param username String
	 * @param password String
	 * @param model Model map with data to display on the page.
	 * @return Name of the view to render.
	 */
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public String register(HttpSession session, @RequestParam(value = "device", required = true) String device,
			@ModelAttribute("userBean") UserRegisterModel userRegisterModel, 
			@RequestParam(value = "confirmPassword", required = true) String passwordConfirm, ModelMap modelMap) {
		
		String password = userRegisterModel.getPassword();

        if ((password == null) || (!password.equals(passwordConfirm))) {
            modelMap.addAttribute("errorType", WebErrors.INVALID_PASSWORD_CONFIRM);
            return UserActionViewPage.REGISTER_PAGE.getPageName(device);
        }
		
		userRegisterModel.setUserType("Reseller");
		User user = userHelperService.buildUserFromRegisterModel(userRegisterModel, false);
		userService.addUser(user);
		
		modelMap.addAttribute("userBean", user);

		// redirect to home or to login
		return UserActionViewPage.REGISTER_PAGE_SUCCESS.getPageName(device);
	}
	
	@RequestMapping(value = "/username_availability.do", method = RequestMethod.GET)
	public @ResponseBody Boolean isUserAvailable(HttpSession session, @RequestParam(value = "device", required = true) String device,
			@RequestParam(value = "username", required = false) String username, ModelMap modelMap) {
		
		Long usernameCount = userService.countByUsername(username);
		return usernameCount > 0 ? false : true;
		
	}
	
	@RequestMapping(value = "/mobile_availability.do", method = RequestMethod.GET)
	public @ResponseBody Boolean isMobileNumberAvailable(HttpSession session, @RequestParam(value = "device", required = true) String device,
			@RequestParam(value = "mobile", required = false) String mobile, ModelMap modelMap) {
		
		Long mobileCount = userService.countByMobile(mobile);
		return mobileCount > 0 ? false : true;		
	}	

	/**
	 * Page view names and action urls constants.
	 */
	public static enum UserActionViewPage {
		
		ERROR_PAGE("page.error", "error"), 
		LOGIN_PAGE("page.login", "login"), 
		REGISTER_PAGE("page.register", "register"), 
		REGISTER_PAGE_SUCCESS("page.register.success", "register");

		/**
		 * Enumeration value.
		 */
		private String pageName; // Correspondents to tiles mapping
		
		private String actionUrl; // Correspondents to url-rewrite mapping

		/**
		 * Default constructor.
		 */
		private UserActionViewPage(String pageName, String actionUrl) {
			this.pageName = pageName;
			this.actionUrl = actionUrl;
		}

		/**
		 * Gets the pageName.
		 *
		 * @return Returns the pageName.
		 */
		public String getPageName(String device) {
			return device + '.' + pageName;
		}
		
		public String getActionUrl(String device) {
			return device + '/' + actionUrl;
		}
	};
}
