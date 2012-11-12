package org.pan.voucher.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.pan.voucher.mapper.UserMapperService;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserSession;
import org.pan.voucher.model.UserType;
import org.pan.voucher.rest.model.UserLoginRestModel;
import org.pan.voucher.rest.model.UserRegisterModel;
import org.pan.voucher.rest.model.UserRestModel;
import org.pan.voucher.rest.model.UserTypeModel;
import org.pan.voucher.service.TransactionService;
import org.pan.voucher.service.UserService;
import org.pan.voucher.service.UserSessionService;
import org.pan.voucher.service.UserTypeService;
import org.pan.voucher.web.AbstractWebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class UserRestController extends AbstractWebController {
	
	private static final Logger log = LoggerFactory.getLogger(UserRestController.class);
	
	@Resource private UserService userService;
	@Resource private UserSessionService userSessionService;
	@Resource private UserTypeService userTypeService;
	@Resource private TransactionService txnService;
	@Resource UserMapperService userHelperService;
	
	@RequestMapping(value="/userType/list", method = RequestMethod.GET)
	public @ResponseBody List<UserTypeModel> listUserTypes() {
		
		List<UserType> userTypes = userTypeService.getAllUserTypes();
		return userHelperService.buildUserTypeListModel(userTypes);
		
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	public void registerUser(@RequestBody UserRegisterModel userRestModel) {
		
		Assert.notNull(userRestModel.getUsername());
		Assert.notNull(userRestModel.getPassword());
		
		User user = userHelperService.buildUserFromRegisterModel(userRestModel, false);
		userService.addUser(user);	
	}	
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	public @ResponseBody String login(@RequestBody UserLoginRestModel restModel) {	
		
		Assert.notNull(restModel.getUsername());
		Assert.notNull(restModel.getPassword());

		User existingUser = userService.getUserByCredentials(restModel.getUsername(), restModel.getPassword());
		UserSession session = userSessionService.startUserSession(existingUser);	
		
		return session.getSessionToken();		
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value="/user/update/me", method=RequestMethod.POST)
	public void updateUser(
			@RequestBody UserRestModel userModel, 
			@RequestParam("sessionToken") String sessionToken) {
		
		User loginUser = userSessionService.getUserForSession(sessionToken);
		User user = userHelperService.buildUserFromRestModel(userModel);
		userService.updateUser(loginUser, user);
	}	


	@RequestMapping(value="/user/me", method=RequestMethod.GET)
	public @ResponseBody UserRestModel getCurrentUser(@RequestParam("sessionToken") String sessionToken) {
		
		User currentUser = userSessionService.getUserForSession(sessionToken);
		return userHelperService.buildRestModelFromUser(currentUser);
	}	
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value="/admin/topupUser/{userId}", method=RequestMethod.POST)
	public void popupAccount(
			@RequestBody BigDecimal ammount, 
			@PathVariable("userId") Integer userId, 
			@RequestParam("sessionToken") String sessionToken) {
		
		userSessionService.getUserForSession(sessionToken);
		User topupedUser = userService.getUserById(userId);
		txnService.topUpAccount(topupedUser, ammount);
	}
}
