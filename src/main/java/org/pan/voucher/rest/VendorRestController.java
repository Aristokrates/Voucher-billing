package org.pan.voucher.rest;

import java.util.List;

import javax.annotation.Resource;

import org.pan.voucher.mapper.ProductMapperService;
import org.pan.voucher.mapper.VendorMapperService;
import org.pan.voucher.model.Product;
import org.pan.voucher.model.Vendor;
import org.pan.voucher.rest.model.ProductRestModel;
import org.pan.voucher.rest.model.VendorRestModel;
import org.pan.voucher.service.UserSessionService;
import org.pan.voucher.service.VendorService;
import org.pan.voucher.web.AbstractWebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/vendor")
public class VendorRestController extends AbstractWebController {
	
	private static final Logger log = LoggerFactory.getLogger(VendorRestController.class);
	
	@Resource private VendorService vendorService;
	@Resource private UserSessionService userSessionService;
	@Resource private VendorMapperService vendorMapper;
	@Resource private ProductMapperService productMapperService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public @ResponseBody List<VendorRestModel> listVendors(@RequestParam("sessionToken") String sessionToken) {
		
		Assert.notNull(sessionToken);
		userSessionService.getUserForSession(sessionToken);
		
		List<Vendor> vendors = vendorService.getAllVendors();
		
		return vendorMapper.buildVendorListModel(vendors);
	}
	
	@RequestMapping(value = "/{vendorId}/products", method=RequestMethod.GET)
	public @ResponseBody List<ProductRestModel> getProductsByVendor(@PathVariable("vendorId") Integer vendorId, 
			@RequestParam("sessionToken") String sessionToken) {
		
		Assert.notNull(sessionToken);
		userSessionService.getUserForSession(sessionToken);
		
		List<Product> vendorProducts = vendorService.getProductsByVendorId(vendorId);
		
		return productMapperService.buildModelListFromProducts(vendorProducts);
	}
}
