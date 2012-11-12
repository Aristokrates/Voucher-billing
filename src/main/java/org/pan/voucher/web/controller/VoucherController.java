package org.pan.voucher.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.pan.voucher.exception.UserNotEligibleToBuyException;
import org.pan.voucher.mapper.ProductMapperService;
import org.pan.voucher.mapper.VendorMapperService;
import org.pan.voucher.model.Product;
import org.pan.voucher.model.User;
import org.pan.voucher.model.Vendor;
import org.pan.voucher.model.VendorNotice;
import org.pan.voucher.remoting.model.Voucher;
import org.pan.voucher.rest.model.ProductRestModel;
import org.pan.voucher.rest.model.VendorNoticeModel;
import org.pan.voucher.rest.model.VendorRestModel;
import org.pan.voucher.rest.model.VoucherRestModel;
import org.pan.voucher.service.ProductService;
import org.pan.voucher.service.TransactionService;
import org.pan.voucher.service.UserService;
import org.pan.voucher.service.VendorService;
import org.pan.voucher.web.AbstractWebController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/voucher")
public class VoucherController extends AbstractWebController {
	
	@Resource private UserService userService;
	@Resource private VendorService vendorService;
	@Resource private TransactionService transactionService;
	@Resource private VendorMapperService vendorMapper;
	@Resource private ProductMapperService productMapper;
	@Resource private ProductService productService;
	
	
	@RequestMapping(value="/home.do", method=RequestMethod.GET)
	public String showVoucherPage(HttpSession session, @RequestParam(value = "device", required = true) String device,
			ModelMap modelMap) {
		
		User loginUser = userService.getUserById(getUserId());
		setSessionData(session, loginUser);
		
		setupMenuPrivilegues(modelMap);
		
		List<Vendor> vendors = vendorService.getAllVendors();
		List<VendorRestModel> vendorListModel = vendorMapper.buildVendorListModel(vendors);
		modelMap.addAttribute("vendorList", vendorListModel);
		modelMap.addAttribute("userBalance", loginUser.getBalance());
		return BuyProductViewPage.VOUCHER.getPageName(device);
	}
	
	@RequestMapping(value="/buy_product.do", method=RequestMethod.GET)
	public String buyProduct(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value = "device", required = true) String device,
			@RequestParam(value = "id", required = false) Integer productId, ModelMap modelMap) {
		
		User loginUser = userService.getUserById(getUserId());
		Product product = productService.getProductById(productId);
		
		Vendor vendor = product.getVendor();
				
		Voucher voucher = null;
		String errorCode = null;
		try {
			voucher = transactionService.buyProduct(loginUser, productId);
		} catch (UserNotEligibleToBuyException e) {
			errorCode = e.getMessage();
		} catch (Exception e) {
			errorCode = "General exception: " + e.getMessage();
		}
		
		if (errorCode != null) {
				return "redirect:/" + BuyProductViewPage.VOUCHER_PRODUCT.getActionUrl(device) + "/" + vendor.getId() + "?errorCode=" + errorCode;	
		} else {
			if (voucher != null) {
				return "redirect:/" + BuyProductViewPage.VOUCHER_PRODUCT.getActionUrl(device) + "/" + vendor.getId() + "?pin=" + voucher.getPin() + "&serial=" + voucher.getSerial();	
			}
		}
		
		return "redirect:/" + BuyProductViewPage.VOUCHER_PRODUCT.getActionUrl(device) + "/" + vendor.getId();
 	}
	
	@RequestMapping(value="/show_products.do", method=RequestMethod.GET)
	public String showProductsByVendorPage(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value = "device", required = true) String device,
			@RequestParam(value = "vendorId") Integer vendorId, 
			@RequestParam(value="pin", required = false) String pin, @RequestParam(value="serial", required = false) String serial,
			@RequestParam(value="errorCode", required = false) String errorCode,
			ModelMap modelMap) {
		
		setupMenuPrivilegues(modelMap);
		Vendor vendor = vendorService.getVendorByVendorId(vendorId);
		List<Product> products = vendorService.getProductsByVendorId(vendorId);
		List<VendorNotice> vendorNotices = vendorService.getVendorNoticesByVendorId(vendorId);
		
		List<ProductRestModel> productListModel = productMapper.buildModelListFromProducts(products);
		List<VendorNoticeModel> vendorNoticeListModel = vendorMapper.buildVendorNoticeListModel(vendorNotices);
		
		modelMap.put("productList", productListModel);
		modelMap.put("noticeList", vendorNoticeListModel);
		modelMap.addAttribute("vendor", vendor.getName());
		
		if (pin != null && serial != null) {
			modelMap.addAttribute("voucher", new VoucherRestModel(false, null, pin, serial , null));
		}
		if (errorCode != null) {
			modelMap.addAttribute("errorCode", errorCode);
		}
		
		return BuyProductViewPage.VOUCHER_PRODUCT.getPageName(device);
	}
	
	@RequestMapping(value="/get_products.do", method=RequestMethod.GET)
	public @ResponseBody List<ProductRestModel> getProductsByVendor(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value = "vendorId") Integer vendorId, ModelMap modelMap) {
		
		List<Product> products = vendorService.getProductsByVendorId(vendorId);
		List<ProductRestModel> productListModel = productMapper.buildModelListFromProducts(products);
		
		return productListModel;
		
	}
	
	/**
	 * Page view names and action urls constants.
	 */
	public static enum BuyProductViewPage {
		
		VOUCHER("page.voucher", "voucher"),
		VOUCHER_PRODUCT("page.voucher.product", "home/products");

		/**
		 * Enumeration value.
		 */
		private String pageName; // Correspondents to tiles mapping
		
		private String actionUrl; // Correspondents to url-rewrite mapping

		/**
		 * Default constructor.
		 */
		private BuyProductViewPage(String pageName, String actionUrl) {
			this.pageName = pageName;
			this.actionUrl = actionUrl;
		}

		/**
		 * Gets the pageName.
		 *
		 * @return Returns the pageName.
		 */
		public String getPageName(String device) {
			return device + '.' + pageName;
		}
		
		public String getActionUrl(String device) {
			return device + '/' + actionUrl;
		}
	};

}
