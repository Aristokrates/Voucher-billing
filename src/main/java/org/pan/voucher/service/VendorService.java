package org.pan.voucher.service;

import java.util.List;

import org.pan.voucher.model.Product;
import org.pan.voucher.model.Vendor;
import org.pan.voucher.model.VendorNotice;

public interface VendorService {
	
	void addVendor(Vendor vendor);

	void deleteVendor(Integer vendorId);
	
	Vendor getVendorByVendorId(Integer vendorId);
	
	List<Vendor> getAllVendors();

	List<Product> getProductsByVendorId(Integer vendorId);
	
	List<VendorNotice> getVendorNoticesByVendorId(Integer vendorId);
}
