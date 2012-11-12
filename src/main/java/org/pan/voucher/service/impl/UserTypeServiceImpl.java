package org.pan.voucher.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.pan.voucher.dao.UserTypeDao;
import org.pan.voucher.model.UserType;
import org.pan.voucher.service.UserTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userTypeService")
@Transactional(propagation = Propagation.REQUIRED, readOnly=true)
public class UserTypeServiceImpl implements UserTypeService {
	
	@Resource private UserTypeDao userTypeDao;

	@Override
	public List<UserType> getAllUserTypes() {
		return userTypeDao.findAll();
	}

	@Override
	public UserType getUserTypeByName(String name) {
		return userTypeDao.findUserTypeByName(name);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public void addUserType(UserType userType) {

		userType.setDateCreated(new Date());
		userType.setLastModifiedDate(new Date());
		userTypeDao.save(userType);
	}

}
