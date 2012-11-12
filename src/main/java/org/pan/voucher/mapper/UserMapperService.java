package org.pan.voucher.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.pan.voucher.exception.InvalidDataException;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserAddress;
import org.pan.voucher.model.UserCredential;
import org.pan.voucher.model.UserProfile;
import org.pan.voucher.model.UserType;
import org.pan.voucher.model.UserTypeName;
import org.pan.voucher.rest.model.UserRegisterModel;
import org.pan.voucher.rest.model.UserRestModel;
import org.pan.voucher.rest.model.UserTypeModel;
import org.pan.voucher.service.UserTypeService;

public class UserMapperService {

	@Resource private UserTypeService userTypeService;
	
	public List<UserRestModel> buildModelListFromUsers(List<User> users) {
		
		List<UserRestModel> userRestModelList = new ArrayList<UserRestModel>();
		
		for (User user : users) {
			userRestModelList.add(buildRestModelFromUser(user));
		}
		return userRestModelList;
	}

	public UserRestModel buildRestModelFromUser(User user) {

		UserRestModel userRestModel = new UserRestModel();
		if (user.getUserType() != null) {
			userRestModel.setUserType(user.getUserType().getName().toString());
		}

		userRestModel.setUserId(user.getId());
		userRestModel.setFirstName(user.getFirstName());
		userRestModel.setMiddleName(user.getMiddleName());
		userRestModel.setLastName(user.getLastName());
		userRestModel.setUserBalance(user.getBalance());
		userRestModel.setActive(user.getIsActive());

		if (user.getUserProfile() != null) {
			UserProfile profile = user.getUserProfile();
			userRestModel.setProfileDesc(profile.getDescription());
			userRestModel.setProfileEmail(profile.getEmail());
			userRestModel.setProfileFacebook(profile.getFacebook());
			userRestModel.setProfileFixedTel(profile.getFixedTel());
			userRestModel.setProfileMobile2(profile.getMobile2());
			userRestModel.setProfileTwitter(profile.getTwitter());
			userRestModel.setProfilePhoto(profile.getPhoto());
		}

		if (user.getUserAddress() != null) {
			UserAddress address = user.getUserAddress();
			userRestModel.setAddress1(address.getAddress1());
			userRestModel.setAddress2(address.getAddress2());
			userRestModel.setAddress3(address.getAddress3());
			userRestModel.setAddress4(address.getAdddress4());
			userRestModel.setCountry(address.getCountry());
			userRestModel.setPostcode(address.getPostcode());
			userRestModel.setTown(address.getTown());
		}
		
		if (user.getUserCredential() != null) {
			UserCredential credentials = user.getUserCredential();
			userRestModel.setUsername(credentials.getUsername());
			userRestModel.setMobile(credentials.getMobile());
		}

		return userRestModel;
	}

	public User buildUserFromRegisterModel(UserRegisterModel userRegisterModel, boolean allowAdmins) throws IllegalArgumentException {

		User user = new User();	
		buildUserPersonalData(user, new UserRestModel(userRegisterModel));

		String userTypeName = userRegisterModel.getUserType();		
		UserType userType = userTypeService.getUserTypeByName(userTypeName);	
		if (userType == null) {
			throw new InvalidDataException("User type must be chosen");
		}
		if (UserTypeName.getAdminUserTypes().contains(userType.getName()) && !allowAdmins) {
			throw new InvalidDataException("Not allowed to register admin users");
		}
		user.setUserType(userType);

		UserAddress address = new UserAddress();		
		buildUserAddress(address, new UserRestModel(userRegisterModel));		
		address.setDateCreated(new Date());
		address.setLastModifiedDate(new Date());
		address.setUser(user);
		user.setUserAddress(address);

		UserCredential credential = new UserCredential();		
		buildUserCredential(credential, userRegisterModel);		
		credential.setDateCreated(new Date());
		credential.setLastModifiedDate(new Date());
		credential.setUser(user);
		user.setUserCredential(credential);

		UserProfile profile = new UserProfile();		
		buildUserProfile(profile, new UserRestModel(userRegisterModel));
		profile.setDateCreated(new Date());
		profile.setLastModifiedDate(new Date());
		profile.setUser(user);
		user.setUserProfile(profile);

		return user;		
	}

	public User buildUserFromRestModel(UserRestModel userRestModel) throws IllegalArgumentException {

		User user = new User();	
		buildUserPersonalData(user, userRestModel);

		UserAddress address = new UserAddress();		
		buildUserAddress(address, userRestModel);		
		user.setUserAddress(address);

		UserProfile profile = new UserProfile();		
		buildUserProfile(profile, userRestModel);
		user.setUserProfile(profile);

		return user;		
	}

	private void buildUserCredential(UserCredential credential, UserRegisterModel userRestModel) {

		credential.setUsername(userRestModel.getUsername());
		credential.setPassword(userRestModel.getPassword());		
		credential.setMobile(userRestModel.getMobile());
	}

	private void buildUserProfile(UserProfile profile, UserRestModel userRestModel) {

		profile.setDescription(userRestModel.getProfileDesc());
		profile.setPhoto(userRestModel.getProfilePhoto());
		profile.setMobile2(userRestModel.getProfileMobile2());
		profile.setFixedTel(userRestModel.getProfileFixedTel());
		profile.setEmail(userRestModel.getProfileEmail());
		profile.setFacebook(userRestModel.getProfileFacebook());
		profile.setTwitter(userRestModel.getProfileTwitter());
	}

	private void buildUserAddress(UserAddress address, UserRestModel userRestModel) {

		address.setAddress1(userRestModel.getAddress1());
		address.setAddress2(userRestModel.getAddress2());
		address.setAddress3(userRestModel.getAddress3());
		address.setAdddress4(userRestModel.getAddress4());
		address.setTown(userRestModel.getTown());
		address.setCountry(userRestModel.getCountry());
		address.setPostcode(userRestModel.getPostcode());
	}

	private void buildUserPersonalData(User user, UserRestModel userRestModel) {

		user.setFirstName(userRestModel.getFirstName());
		user.setMiddleName(userRestModel.getMiddleName());
		user.setLastName(userRestModel.getLastName());
		user.setBalance(userRestModel.getUserBalance());
		user.setDateCreated(new Date());
	}

	public List<UserTypeModel> buildUserTypeListModel(List<UserType> userTypes) {
		List<UserTypeModel> modelList = new ArrayList<UserTypeModel>();
		for (UserType userType : userTypes) {
			modelList.add(new UserTypeModel(userType.getName().toString(), 
					userType.getDescription()));
		}
		return modelList;
	}

}
