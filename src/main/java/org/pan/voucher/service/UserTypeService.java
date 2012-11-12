package org.pan.voucher.service;

import java.util.List;

import org.pan.voucher.model.UserType;

public interface UserTypeService {
	
	List<UserType> getAllUserTypes();
	
	UserType getUserTypeByName(String name);
	
	void addUserType(UserType userType);

}
