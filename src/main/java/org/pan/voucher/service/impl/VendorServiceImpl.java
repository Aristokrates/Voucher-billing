package org.pan.voucher.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.pan.voucher.dao.ProductDao;
import org.pan.voucher.dao.VendorDao;
import org.pan.voucher.exception.InvalidDataException;
import org.pan.voucher.model.Product;
import org.pan.voucher.model.Vendor;
import org.pan.voucher.model.VendorNotice;
import org.pan.voucher.service.VendorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("vendorService")
@Transactional(propagation = Propagation.REQUIRED, readOnly=true)
public class VendorServiceImpl implements VendorService {

	@Resource private VendorDao vendorDao;
	@Resource private ProductDao productDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public void addVendor(Vendor vendor) {
		
		vendor.setDateCreated(new Date());
		vendor.setLastModifiedDate(new Date());
		vendorDao.save(vendor);
	}		
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteVendor(Integer vendorId) {
		
		Vendor vendor = vendorDao.findById(vendorId);
		vendor.setIsDeleted(true);
		vendorDao.merge(vendor);
	}
	
	@Override
	public List<Vendor> getAllVendors() {
		return vendorDao.getActiveVendors();
	}

	@Override
	public List<Product> getProductsByVendorId(Integer vendorId) {
		
		Vendor vendor = vendorDao.getVendorByVendorId(vendorId);
		
		if (vendor == null) {
			throw new InvalidDataException("Vendor does not exists");
		}
		
		List<Product> vendorProducts = productDao.getProductsByVendor(vendor);
		return vendorProducts;
	}

	@Override
	public List<VendorNotice> getVendorNoticesByVendorId(Integer vendorId) {
		
		Vendor vendor = vendorDao.getVendorWithNotices(vendorId);
		
		if (vendor == null) {
			throw new InvalidDataException("Vendor does not exists");
		}
		return new ArrayList<VendorNotice>((vendor.getVendorNotices()));
	}

	@Override
	public Vendor getVendorByVendorId(Integer vendorId) {
		
		Vendor vendor = vendorDao.getVendorByVendorId(vendorId);
		if (vendor == null) {
			throw new InvalidDataException("Vendor does not exists");
		}
		return vendor;
	}
}
