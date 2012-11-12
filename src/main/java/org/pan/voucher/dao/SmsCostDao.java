package org.pan.voucher.dao;

import org.pan.voucher.model.SmsCost;

public interface SmsCostDao extends BaseDao<SmsCost, Integer> {
	
	SmsCost getSmsCostByCountryCode(Integer countryCode);

}
