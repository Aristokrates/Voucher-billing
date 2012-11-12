package org.pan.voucher.service;

import java.util.List;

import org.pan.voucher.exception.InvalidDataException;
import org.pan.voucher.exception.InvalidUserCredentials;
import org.pan.voucher.exception.UserPermissionException;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserType;

public interface UserService {
	
	void addUser(User user) throws InvalidDataException;
	
	void updateUser(User loginUser, User modelUser);	
	
	Long countByUsername(String username);
	
	Long countByMobile(String mobileNumber);

	User getUserByCredentials(String username, String password) throws InvalidUserCredentials;
	
	User getUserById(Integer userId) throws UserPermissionException;

	List<User> getAllNonAdminUsers() throws UserPermissionException;
	
	List<User> getAllNonSuperAdminUsers() throws UserPermissionException;

	void changeUserType(User requestedUser, UserType newUserType) throws UserPermissionException;

	void activateUser(Integer userId, boolean activateStatus);
	
	List<User> getAllUsers();

}
