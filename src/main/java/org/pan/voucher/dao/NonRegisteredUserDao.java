package org.pan.voucher.dao;

import org.pan.voucher.model.NonRegisteredUser;

public interface NonRegisteredUserDao extends BaseDao<NonRegisteredUser, Integer> {

	NonRegisteredUser getNonRegisteredUserByNumber(Long number, Integer countryCode);
	
}
