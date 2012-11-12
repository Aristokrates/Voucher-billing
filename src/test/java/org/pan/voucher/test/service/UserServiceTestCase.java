package org.pan.voucher.test.service;

import javax.annotation.Resource;

import org.pan.voucher.service.UserService;
import org.pan.voucher.service.UserTypeService;
import org.pan.voucher.test.BaseTestCase;

public class UserServiceTestCase extends BaseTestCase {
	
	@Resource private UserTypeService userTypeService;
	@Resource private UserService userService;


}
