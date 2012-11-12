package org.pan.voucher.dao;

import org.pan.voucher.model.UserType;

public interface UserTypeDao extends BaseDao<UserType, Integer> {

	UserType findUserTypeByName(String name);

}
