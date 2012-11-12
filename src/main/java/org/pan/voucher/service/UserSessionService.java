package org.pan.voucher.service;

import org.pan.voucher.exception.InvalidSessionTokenException;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserSession;

public interface UserSessionService {

	UserSession startUserSession(User user);
	
	User getUserForSession(String sessionToken) throws InvalidSessionTokenException;

}
