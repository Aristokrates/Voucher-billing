package org.pan.voucher.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.pan.voucher.dao.UserSessionDao;
import org.pan.voucher.exception.InvalidSessionTokenException;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserSession;
import org.pan.voucher.service.UserSessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userSessionService")
@Transactional(propagation = Propagation.REQUIRED, readOnly=true)
public class UserSessionServiceImpl implements UserSessionService {
	
	@Resource private UserSessionDao userSessionDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public UserSession startUserSession(User user) {
		
		UserSession session = new UserSession();
		session.setSessionToken(UUID.randomUUID().toString());
		session.setTokenIssueDate(new Date());
		session.setUser(user);
		
		userSessionDao.deleteIfExists(user);
		userSessionDao.save(session);
		return session;
	}

	@Override
	public User getUserForSession(String sessionToken) throws InvalidSessionTokenException {
		
		UserSession userSession = userSessionDao.findBySessionToken(sessionToken);			
		if (userSession == null) {
			throw new InvalidSessionTokenException(sessionToken);
		}
		
		return userSession.getUser();
	}
}
