package org.pan.voucher.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.pan.voucher.dao.UserDao;
import org.pan.voucher.exception.InvalidDataException;
import org.pan.voucher.exception.InvalidUserCredentials;
import org.pan.voucher.exception.UserPermissionException;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserType;
import org.pan.voucher.model.UserTypeName;
import org.pan.voucher.service.UserService;
import org.pan.voucher.utils.EncryptorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service("userService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class UserServiceImpl implements UserService {
	
	@Resource private UserDao userDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addUser(User user) throws InvalidDataException, IllegalArgumentException{
		
		Assert.notNull(user.getUserCredential());
		Assert.notNull(user.getUserCredential().getUsername());
		Assert.hasLength(user.getUserCredential().getUsername());
		Assert.notNull(user.getUserCredential().getPassword());
		Assert.hasLength(user.getUserCredential().getPassword());
		Assert.notNull(user.getUserType());
		
		Long countByUsername = userDao.countByUsername(user.getUserCredential().getUsername());
		if (countByUsername > 0) {
			throw new InvalidDataException("Username already exist");
		}
		
		Long countByMobile = userDao.countByMobile(user.getUserCredential().getMobile());
		if (countByMobile > 0) {
			throw new InvalidDataException("Mobile already in use");
		}
		
		user.setDateCreated(new Date());
		user.setLastModifiedDate(new Date());
		user.setBalance(BigDecimal.valueOf(0.00));
		user.getUserCredential().setPassword(
				EncryptorService.encryptUsingOneWayHash(user.getUserCredential().getPassword()));
		userDao.save(user);		
		
		//TODO send notification/email/confirmation
	}
	
	@Override
	public Long countByUsername(String username) {
		return userDao.countByUsername(username);
	}
	
	@Override
	public Long countByMobile(String mobileNumber) {
		return userDao.countByMobile(mobileNumber);
	}
	
	@Override
	public User getUserByCredentials(String username, String password) throws InvalidUserCredentials, IllegalArgumentException {
		
		Assert.notNull(username);
		Assert.notNull(password);
		
		String encryptedPassword = EncryptorService.encryptUsingOneWayHash(password);		
		User user = userDao.findUserByMobileAndPassword(username, encryptedPassword);
		
		if (user == null) {
			throw new InvalidUserCredentials(username);
		}
		
		//FIXME (pai) temporary check
		if (Boolean.FALSE.equals(user.getIsActive())) {
			throw new InvalidUserCredentials(username);
		}
		
		return user;
	}

	@Override
	public User getUserById(Integer userId) {

		User user = userDao.getUserById(userId);
		if (user == null) {
			throw new InvalidDataException("User with id: " + userId + " does not exist");
		}
		return user;
	}

	@Override
	public List<User> getAllNonAdminUsers() {
		
		return userDao.getUsersByType(UserTypeName.Reseller, UserTypeName.Vendor);
	}

	
	@Override
	public List<User> getAllNonSuperAdminUsers() throws UserPermissionException {

		return userDao.getUsersByType(UserTypeName.Admin, UserTypeName.Vendor, UserTypeName.Reseller);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateUser(User loginUser, User modelUser) {

		loginUser.updateStateFromUserModel(modelUser);
		userDao.merge(loginUser);			
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void changeUserType(User requestedUser, UserType newUserType) throws UserPermissionException {

		requestedUser.setUserType(newUserType);
		userDao.merge(requestedUser);
	}

	@Override
	public List<User> getAllUsers() {
		
		return userDao.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void activateUser(Integer userId, boolean activateStatus) {
	
		User user = userDao.getUserById(userId);
		if (user == null) {
			throw new InvalidDataException("User with id: " + userId + " does not exist");
		}
		user.setIsActive(activateStatus);
		userDao.save(user);
	}
}
