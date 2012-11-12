package org.pan.voucher.service;

import org.pan.voucher.model.VendorNotice;

public interface VendorNoticeService {
	
	void addVendorNotice(VendorNotice vendorNotice);
	
	void updateVendorNotice(VendorNotice vendorNotice, VendorNotice vendorNoticeModel);

	void deleteVendorNotice(Integer vendorNoticeId);
}
