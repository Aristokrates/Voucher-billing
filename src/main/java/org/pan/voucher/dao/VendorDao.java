package org.pan.voucher.dao;


import java.util.List;

import org.pan.voucher.model.Vendor;

public interface VendorDao extends BaseDao<Vendor, Integer> {
	
	List<Vendor> getActiveVendors();

	Vendor getVendorWithNotices(Integer vendorId);

	Vendor getVendorByVendorId(Integer vendorId);

}
