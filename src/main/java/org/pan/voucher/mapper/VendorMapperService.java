package org.pan.voucher.mapper;

import java.util.ArrayList;
import java.util.List;

import org.pan.voucher.model.Vendor;
import org.pan.voucher.model.VendorNotice;
import org.pan.voucher.rest.model.VendorNoticeModel;
import org.pan.voucher.rest.model.VendorRestModel;

public class VendorMapperService {

	public List<VendorRestModel> buildVendorListModel(List<Vendor> vendors) {
		
		List<VendorRestModel> vendorListModel = new ArrayList<VendorRestModel>();
		
		for (Vendor vendor : vendors) {
			vendorListModel.add(buildModelFromVendor(vendor));
		}
		
		return vendorListModel;
	}
	
	public List<VendorNoticeModel> buildVendorNoticeListModel(List <VendorNotice> vendorNotices) {
		
		List<VendorNoticeModel> vendorNoticeListModel = new ArrayList<VendorNoticeModel>();
		
		for (VendorNotice notice : vendorNotices) {
			vendorNoticeListModel.add(buildVendorNoticeModelFromNotice(notice));
		}
		
		return vendorNoticeListModel;
	}
	
	private VendorNoticeModel buildVendorNoticeModelFromNotice(VendorNotice notice) {
		return new VendorNoticeModel(notice.getName(), notice.getDescription());
	}

	public VendorRestModel buildModelFromVendor(Vendor vendor) {
		
		VendorRestModel vendorModel = new VendorRestModel();
		vendorModel.setName(vendor.getName());
		vendorModel.setVendorId(vendor.getId());
	    return vendorModel;
	}
	
	public Vendor buildVendorFromModel(VendorRestModel model) {
		
		Vendor vendor = new Vendor();
		vendor.setName(model.getName());
		vendor.setId(model.getVendorId());
	    return vendor;
	}
}
