package org.pan.voucher.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.pan.voucher.mapper.UserMapperService;
import org.pan.voucher.model.User;
import org.pan.voucher.rest.model.UserRestModel;
import org.pan.voucher.service.UserService;
import org.pan.voucher.web.AbstractWebController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user_profile")
public class UserProfileController extends AbstractWebController {
	
	@Resource private UserService userService;
	@Resource private UserMapperService userHelperService;
	
	/**
	 * Show user profile page.
	 *
	 * @param device String
	 * @param username String
	 * @param password String
	 * @param model Model map with data to display on the page.
	 * @return Name of the view to render.
	 */
	@RequestMapping(value = "/profile.do", method = RequestMethod.GET)
	public String showUserProfile(HttpSession session, @RequestParam(value = "device", required = true) String device, ModelMap modelMap) {

		setupMenuPrivilegues(modelMap);
		
		User loginUser = userService.getUserById(getUserId());
	
		UserRestModel model = userHelperService.buildRestModelFromUser(loginUser);
		modelMap.addAttribute("userBean", model);
		modelMap.addAttribute("countries", getListOfCountries());

		return UserProfileViewPage.USER_PROFILE_PAGE.getPageName(device);
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
	@RequestMapping(value = "/profile.do", method = RequestMethod.POST)
	public String updateUserProfile(HttpSession session, @RequestParam(value = "device", required = true) String device,
			@ModelAttribute("userBean") UserRestModel userRestModel, ModelMap modelMap) {

		User loginUser = userService.getUserById(getUserId());
		User user = userHelperService.buildUserFromRestModel(userRestModel);
		userService.updateUser(loginUser, user);

		modelMap.addAttribute("userBean", loginUser);

		return "redirect:/" + UserProfileViewPage.USER_PROFILE_PAGE.getActionUrl(device);
	}
	
	/**
	 * Page view names and action urls constants.
	 */
	public static enum UserProfileViewPage {
			
		USER_PROFILE_PAGE("page.profile", "profile");

		/**
		 * Enumeration value.
		 */
		private String pageName; // Correspondents to tiles mapping
		
		private String actionUrl; // Correspondents to url-rewrite mapping

		/**
		 * Default constructor.
		 */
		private UserProfileViewPage(String pageName, String actionUrl) {
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
