package org.pan.voucher.test.service;

import java.util.Arrays;

import javax.annotation.Resource;

import org.junit.Test;
import org.pan.voucher.service.UserGroupService;
import org.pan.voucher.service.UserService;
import org.pan.voucher.test.BaseTestCase;

public class UserGroupServiceTestCase extends BaseTestCase {
	
	@Resource private UserGroupService userGroupService;
	@Resource private UserService userService;
	
	@Test
	public void testAddGroup() {
		
		userGroupService.addUserGroup("PanceTestGroup", Arrays.asList(1, 2, 3), Arrays.asList("+38970563236"), 5);
	}

}
