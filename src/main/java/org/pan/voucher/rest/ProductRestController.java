package org.pan.voucher.rest;

import java.util.List;

import javax.annotation.Resource;

import org.pan.voucher.mapper.ProductMapperService;
import org.pan.voucher.model.ProductType;
import org.pan.voucher.model.User;
import org.pan.voucher.remoting.model.Voucher;
import org.pan.voucher.rest.model.ProductTypeModel;
import org.pan.voucher.rest.model.VoucherRestModel;
import org.pan.voucher.service.ProductTypeService;
import org.pan.voucher.service.TransactionService;
import org.pan.voucher.service.UserSessionService;
import org.pan.voucher.web.AbstractWebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductRestController extends AbstractWebController {
	
	private static final Logger log = LoggerFactory.getLogger(ProductRestController.class);

	@Resource private UserSessionService sessionService;
	@Resource private ProductTypeService productTypeService;
	@Resource private TransactionService transactionService;
	@Resource private ProductMapperService productMapperService;
	
	@RequestMapping(value="/productType/list", method = RequestMethod.GET)
	public @ResponseBody List<ProductTypeModel> listProductTypes() {
		
		List<ProductType> productTypes = productTypeService.getAllProductTypes();
		return productMapperService.buildProductTypeListModel(productTypes);		
	}

	@RequestMapping(value="/product/buy/{productId}", method = RequestMethod.GET)
	public @ResponseBody VoucherRestModel buyProduct(@PathVariable("productId") Integer productId, 
			@RequestParam("sessionToken") String sessionToken) {

		User user = sessionService.getUserForSession(sessionToken);
		Voucher voucher = transactionService.buyProduct(user, productId);
		return new VoucherRestModel(false, null, voucher.getPin(), voucher.getSerial(), null);
	}	
}
