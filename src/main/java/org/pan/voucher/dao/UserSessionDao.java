package org.pan.voucher.dao;

import org.pan.voucher.model.User;
import org.pan.voucher.model.UserSession;

public interface UserSessionDao extends BaseDao<UserSession, Integer> {

	void deleteIfExists(User user);
	
	UserSession findBySessionToken(String sessionToken);

}
