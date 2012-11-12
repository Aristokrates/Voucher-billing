package org.pan.voucher.dao.impl;

import org.pan.voucher.dao.VendorNoticeDao;
import org.pan.voucher.model.VendorNotice;
import org.springframework.stereotype.Repository;

@Repository("vendorNoticeDao")
public class VendorNoticeDaoImpl extends BaseDaoImpl<VendorNotice, Integer> implements VendorNoticeDao {
	
}
