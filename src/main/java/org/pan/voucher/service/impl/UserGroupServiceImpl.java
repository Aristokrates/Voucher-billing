package org.pan.voucher.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.pan.voucher.dao.NonRegisteredUserDao;
import org.pan.voucher.dao.UserDao;
import org.pan.voucher.dao.UserGroupDao;
import org.pan.voucher.exception.InvalidDataException;
import org.pan.voucher.model.NonRegisteredUser;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserGroup;
import org.pan.voucher.service.UserGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

@Service("userGroupService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class UserGroupServiceImpl implements UserGroupService {
	
	@Resource private NonRegisteredUserDao nonRegisteredUserDao;
	@Resource private UserGroupDao userGroupDao;	
	@Resource private UserDao userDao;	
	@Resource private PhoneNumberUtil phoneNumberUtil;

	@Override
	public void addUserGroup(String groupName, List<Integer> registeredUserIds,
			List<String> nonRegisteredUsers, Integer ownerUserId) {
		
		UserGroup existingUserGroup = userGroupDao.getUserGroupByName(groupName);
		
		if (existingUserGroup != null) {
			throw new InvalidDataException("Group with that name already exist");
		}
		
		UserGroup group = new UserGroup();
		group.setName(groupName);
		group.setDateCreated(new Date());
		for (Integer userId : registeredUserIds) {
			User user = userDao.getUserById(userId);
			if (user != null) {
				group.addGroupMember(user);
			}
		}
		
		for (String nonRegUser : nonRegisteredUsers) {
			PhoneNumber numero = isMobileNumber(nonRegUser);
			if (numero != null) {
				NonRegisteredUser nonRegisteredUser = nonRegisteredUserDao.getNonRegisteredUserByNumber(numero.getNationalNumber(), numero.getCountryCode());
				if (nonRegisteredUser == null) {
					nonRegisteredUser = new NonRegisteredUser(numero.getNationalNumber(), numero.getCountryCode(), false);
					nonRegisteredUserDao.save(nonRegisteredUser);
				}
				group.addNonRegisteredMember(nonRegisteredUser);
			}
		}
		
		User owner = userDao.getUserById(ownerUserId);
		group.setOwnerUser(owner);
		
		userGroupDao.save(group);

	}

	@Override
	public void updateUserGroup(UserGroup userGroup, String name) {
		
		userGroup.setName(name);
		userGroupDao.merge(userGroup);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UserGroup getGroupById(Integer userGroupId) {

		UserGroup userGroup = userGroupDao.getUserGroupById(userGroupId);
		
		if (userGroup == null) {
			throw new InvalidDataException("User group not found: " + userGroupId);
		}
		return userGroup;
	}

	@Override
	public void addUsersToUserGroup(UserGroup userGroup, List<User> users) {
		
		for (User user : users) {
			userGroup.addGroupMember(user);
		}
		userGroupDao.merge(userGroup);
		
	}

	@Override
	public void addNonUsersToUserGroup(UserGroup userGroup,
			List<String> nonRegisteredUsers) {

		for (String nonUser : nonRegisteredUsers) {
			PhoneNumber numero = isMobileNumber(nonUser);
			if (numero != null) {
				userGroup.addNonRegisteredMember(new NonRegisteredUser(numero.getNationalNumber(), numero.getCountryCode(), false));
			}
		}
		userGroupDao.merge(userGroup);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UserGroup getGroupByName(String userGroupName) {
		
		UserGroup userGroup = userGroupDao.getUserGroupByName(userGroupName);
		
		if (userGroup == null) {
			throw new InvalidDataException("User group not found: " + userGroupName);
		}
		return userGroup;
	}
	
	private PhoneNumber isMobileNumber(String nonUser) {

		try {
			return phoneNumberUtil.parse(nonUser, "FR");
		} catch (NumberParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
