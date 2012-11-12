package org.pan.voucher.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.pan.voucher.dao.VendorNoticeDao;
import org.pan.voucher.model.VendorNotice;
import org.pan.voucher.service.VendorNoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vendorNoticeService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class VendorNoticeServiceImpl implements VendorNoticeService {
	
	@Resource private VendorNoticeDao vendorNoticeDao;
	
	@Override
	public void updateVendorNotice(VendorNotice vendorNotice, VendorNotice vendorNoticeModel) {
		
		vendorNotice.updateStateFromVendorNoticeModel(vendorNoticeModel);
		vendorNoticeDao.merge(vendorNotice);
	}

	@Override
	public void addVendorNotice(VendorNotice vendorNotice) {
		vendorNotice.setDateCreated(new Date());
		vendorNotice.setLastModifiedDate(new Date());
		vendorNoticeDao.save(vendorNotice);
	}

	@Override
	public void deleteVendorNotice(Integer vendorNoticeId) {
		vendorNoticeDao.remove(vendorNoticeId);
	}

}
