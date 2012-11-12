package org.pan.voucher.test.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.pan.voucher.model.Product;
import org.pan.voucher.model.Vendor;
import org.pan.voucher.model.VendorNotice;
import org.pan.voucher.service.VendorNoticeService;
import org.pan.voucher.service.VendorService;
import org.pan.voucher.test.BaseTestCase;

public class VendorServiceTestCase extends BaseTestCase {
	
	@Resource private VendorService vendorService;
	@Resource private VendorNoticeService vendorNoticeService;
	
	@Test
	public void addVendor() {
		
		Vendor vendor = new Vendor();
		vendor.setName("Vendor2");
		vendorService.addVendor(vendor);
	}
	
	@Test
	public void addNotice() {
		
		VendorNotice notice = new VendorNotice();
		notice.setName("Notice200");
		notice.setVendor(vendorService.getVendorByVendorId(1));
		vendorNoticeService.addVendorNotice(notice);
		
	}
	
	
	@Test
	public void testGetProductsByVendor() {
		
		List<Product> products = vendorService.getProductsByVendorId(1);
		System.out.println(products);
	}
	
	@Test
	public void testGetNoticesByVendor() {
		
		List<VendorNotice> notices = vendorService.getVendorNoticesByVendorId(1);
		System.out.println(notices);
	}

}
