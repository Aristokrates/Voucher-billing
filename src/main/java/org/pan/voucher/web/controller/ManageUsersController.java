package org.pan.voucher.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.pan.voucher.mapper.UserMapperService;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserType;
import org.pan.voucher.model.UserTypeName;
import org.pan.voucher.rest.model.UserRegisterModel;
import org.pan.voucher.rest.model.UserRestModel;
import org.pan.voucher.rest.model.UserTypeModel;
import org.pan.voucher.service.TransactionService;
import org.pan.voucher.service.UserService;
import org.pan.voucher.service.UserTypeService;
import org.pan.voucher.web.AbstractWebController;
import org.pan.voucher.web.WebErrors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/manage_user")
public class ManageUsersController extends AbstractWebController {
	
	@Resource private UserService userService;
	@Resource private UserTypeService userTypeService;
	@Resource private UserMapperService userMapperService;
	@Resource private TransactionService txnService;
	
	@RequestMapping(value="/home.do", method=RequestMethod.GET)
	public String showManageUsersHomePage(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "device", required = true) String device, ModelMap modelMap) {
		
		setupMenuPrivilegues(modelMap);
		
		User user = getUser();
		
		List<User> userList = new ArrayList<User>();
		
		boolean isAdmin = false;
		boolean isSuperAdmin = false;
		
		if (user.getUserType().getName().equals(UserTypeName.Admin)) {
			isAdmin = true;
			userList = userService.getAllNonAdminUsers();
		} else {
			isSuperAdmin = true;
			userList = userService.getAllNonSuperAdminUsers();
		}
		
		modelMap.addAttribute("userModelList", userMapperService.buildModelListFromUsers(userList));
		modelMap.addAttribute("admin", isAdmin);
		modelMap.addAttribute("superadmin", isSuperAdmin);
		
		return ManageUsersViewPage.MANAGE_USERS_HOME.getPageName(device);
		
		
	}
	
	@RequestMapping(value="/activate.do", method=RequestMethod.GET)
	public @ResponseBody Boolean activateUser(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "device", required = true) String device, 
			@RequestParam(value = "userId", required = true) Integer userId,
			@RequestParam(value = "status", required = true) Boolean status,
			ModelMap modelMap) {
		
		setupMenuPrivilegues(modelMap);		
		
		userService.activateUser(userId, status);
		
		return true;
		
	}
	
	@RequestMapping(value="/topup.do", method=RequestMethod.GET)
	public String showTopupUserPage(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "device", required = true) String device, 
			@RequestParam(value = "userId", required = false) Integer userId,
			ModelMap modelMap) {
		
		setupMenuPrivilegues(modelMap);		
		
		User user = userService.getUserById(userId);
		
		UserRestModel model = userMapperService.buildRestModelFromUser(user);
		
		modelMap.addAttribute("userModel", model);
		
		return ManageUsersViewPage.TOPUP_USER.getPageName(device);
		
	}
	
	@RequestMapping(value="/topup.do", method = RequestMethod.POST)
	public String topupUserAccount(HttpSession session, @RequestParam(value = "device", required = true) String device, 
			@ModelAttribute(value="userModel") UserRestModel userModel, 
			@RequestParam(value="ammount", required=true) BigDecimal ammount, ModelMap modelMap) {
		
		User user = userService.getUserById(userModel.getUserId());
		
		txnService.topUpAccount(user, ammount);
		
		return "redirect:/" + ManageUsersViewPage.MANAGE_USERS_HOME.getActionUrl(device);
		
	}
	
	@RequestMapping(value="/register_admin.do", method = RequestMethod.GET)
	public String showRegisterAdminPage(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "device", required = true) String device, ModelMap modelMap) {
		
		setupMenuPrivilegues(modelMap);
		
		List<UserType> userTypes = userTypeService.getAllUserTypes();
		
		List<UserTypeModel> userTypeModel = userMapperService.buildUserTypeListModel(userTypes);
		
		modelMap.addAttribute("userBean", new UserRegisterModel());
		modelMap.addAttribute("countries", getListOfCountries());
		modelMap.addAttribute("userTypeListModel", userTypeModel);
		
		return ManageUsersViewPage.REGISTER_ADMIN.getPageName(device);
		
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
	@RequestMapping(value = "/register_admin.do", method = RequestMethod.POST)
	public String registerAdmin(HttpSession session, @RequestParam(value = "device", required = true) String device,
			@ModelAttribute("userBean") UserRegisterModel userRestModel, 
			@RequestParam(value = "confirmPassword", required = true) String passwordConfirm, ModelMap modelMap) {
		
		String password = userRestModel.getPassword();

        if ((password == null) || (!password.equals(passwordConfirm))) {
            modelMap.addAttribute("errorType", WebErrors.INVALID_PASSWORD_CONFIRM);
            return ManageUsersViewPage.REGISTER_ADMIN.getPageName(device);
        }
		
		User user = userMapperService.buildUserFromRegisterModel(userRestModel, true);
		userService.addUser(user);
		
		modelMap.addAttribute("userBean", user);

		// redirect to home or to login
		return "redirect:/" + ManageUsersViewPage.MANAGE_USERS_HOME.getActionUrl(device);
	}
	
	
	@RequestMapping(value = "/delete.do", method = RequestMethod.POST)
	public String deleteUserProfile(HttpSession session, @RequestParam(value = "device", required = true) String device,
			@RequestParam(value="id", required=true) Integer userId, ModelMap modelMap) {

		//userService.deleteUser(userId);

		// redirect to user profile url
		return "redirect:/" +  ManageUsersViewPage.MANAGE_USERS_HOME.getActionUrl(device);
	}
	
	/**
	 * Page view names and action urls constants.
	 */
	public static enum ManageUsersViewPage {
		
		MANAGE_USERS_HOME("manage.user", "manage/user"),
		TOPUP_USER("topup.user", "topup/user"),
		REGISTER_ADMIN("admin.register", "admin/register");

		/**
		 * Enumeration value.
		 */
		private final String pageName; // Correspondents to tiles mapping
		
		private final String actionUrl; // Correspondents to url-rewrite mapping

		/**
		 * Default constructor.
		 */
		private ManageUsersViewPage(String pageName, String actionUrl) {
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
