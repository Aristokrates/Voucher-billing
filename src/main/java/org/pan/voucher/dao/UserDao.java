package org.pan.voucher.dao;

import java.util.List;

import org.pan.voucher.model.User;
import org.pan.voucher.model.UserTypeName;

public interface UserDao extends BaseDao<User, Integer> {

	@Deprecated
	User findUserByUsernameAndPassword(String username, String encryptedPassword);
	
	User findUserByMobileAndPassword(String mobile, String encryptedPassword);

	Long countByUsername(String username);

	List<User> getUsersByType(UserTypeName... type);

	User getUserById(Integer userId);

	User findUserByUsername(String messageTo);

	Long countByMobile(String mobile);

}
