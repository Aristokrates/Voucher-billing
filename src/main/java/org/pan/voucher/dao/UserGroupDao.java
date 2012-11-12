package org.pan.voucher.dao;

import org.pan.voucher.model.UserGroup;

public interface UserGroupDao extends BaseDao<UserGroup, Integer> {

	UserGroup getUserGroupById(Integer userGroupId);
	
	UserGroup getUserGroupByName(String name);

}
