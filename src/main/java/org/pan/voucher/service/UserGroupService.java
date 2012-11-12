package org.pan.voucher.service;

import java.util.List;

import org.pan.voucher.model.User;
import org.pan.voucher.model.UserGroup;

public interface UserGroupService {
	
	void updateUserGroup(UserGroup userGroup, String name);
	
	UserGroup getGroupById(Integer userGroupId);
	
	UserGroup getGroupByName(String userGroupName);
	
	void addUsersToUserGroup(UserGroup userGroup, List<User> users);
	
	void addNonUsersToUserGroup(UserGroup userGroup, List<String> nonRegisteredUsers);

	void addUserGroup(String groupName, List<Integer> registeredUserIds, List<String> nonRegisteredUsers, Integer ownerUserId);

}
