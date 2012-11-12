package org.pan.voucher.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.pan.voucher.mapper.ProductMapperService;
import org.pan.voucher.mapper.VendorMapperService;
import org.pan.voucher.model.Product;
import org.pan.voucher.model.ProductType;
import org.pan.voucher.model.Vendor;
import org.pan.voucher.rest.model.ProductRestModel;
import org.pan.voucher.rest.model.ProductTypeModel;
import org.pan.voucher.rest.model.VendorRestModel;
import org.pan.voucher.service.ProductService;
import org.pan.voucher.service.ProductTypeService;
import org.pan.voucher.service.VendorService;
import org.pan.voucher.web.AbstractWebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@RequestMapping(value="/manage_product")
public class ManageProductController extends AbstractWebController {
	
	private static final Logger log = LoggerFactory.getLogger(ManageVendorController.class);
	
	@Resource private ProductService productService;
	@Resource private ProductTypeService productTypeService;
	@Resource private VendorService vendorService;
	@Resource private ProductMapperService productMapperService;
	@Resource private VendorMapperService vendorMapperService;
	
	@RequestMapping(value="/home.do", method=RequestMethod.GET)
	public String showProductHome(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "device", required = true) String device, 
			@RequestParam(value = "vendorId", required = false) Integer vendorId, ModelMap modelMap) {
	
		setupMenuPrivilegues(modelMap);
		
		List<Vendor> vendors = vendorService.getAllVendors();
		List<VendorRestModel> vendorListModel = vendorMapperService.buildVendorListModel(vendors);
		modelMap.addAttribute("vendorList", vendorListModel);	

		if (vendorId != null) {

			List<Product> products = vendorService.getProductsByVendorId(vendorId);
			List<ProductRestModel> productModelList = productMapperService.buildModelListFromProducts(products);

			Vendor vendor = vendorService.getVendorByVendorId(vendorId);

			modelMap.addAttribute("productList", productModelList);
			modelMap.addAttribute("vendorModel", vendorMapperService.buildModelFromVendor(vendor));
		}
		
		return ProductActionViewPage.HOME_PRODUCT_PAGE.getPageName(device);
	}
	
	@RequestMapping(value="/add_product.do", method=RequestMethod.GET)
	public String showAddProductPage(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "device", required = true) String device, 
			@RequestParam(value = "productId", required = false) Integer productId, 
			ModelMap modelMap) {
				
		setupMenuPrivilegues(modelMap);
		List<ProductType> productTypes = productTypeService.getAllProductTypes();
		
		List<ProductTypeModel> productTypeListModel = productMapperService.buildProductTypeListModel(productTypes);
		
		List<Vendor> vendors = vendorService.getAllVendors();
		List<VendorRestModel> vendorListModel = vendorMapperService.buildVendorListModel(vendors);
		modelMap.addAttribute("vendorListModel", vendorListModel);
		modelMap.addAttribute("productTypeListModel", productTypeListModel);
		
		if (productId != null) {
			Product existingProduct = productService.getProductById(productId);
			modelMap.addAttribute("productModel", productMapperService.buildModelFromProduct(existingProduct));
		} else {			
			modelMap.addAttribute("productModel", new ProductRestModel());
		}
		

		return ProductActionViewPage.ADD_PRODUCT_PAGE.getPageName(device);
	}

	@RequestMapping(value="/add_product.do", method=RequestMethod.POST)
	public String addProduct(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value = "device", required = true) String device,
			@ModelAttribute(value = "productModel") ProductRestModel productRestModel, ModelMap modelMap) {

		Product product = productMapperService.buildProductFromModel(productRestModel);
		
		if (product.getId() != null) {
			
			Product existingProduct = productService.getProductById(product.getId());
			productService.updateProduct(existingProduct, product);
		} else {
		
			productService.addProduct(product);
		}

		return "redirect:/" + ProductActionViewPage.HOME_PRODUCT_PAGE.getActionUrl(device);
	}
	
	@RequestMapping(value="/delete_product.do", method=RequestMethod.GET)
	public String deleteProduct(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value = "device", required = true) String device,
			@RequestParam(value = "productId", required = false) Integer productId, ModelMap modelMap) {

		productService.deleteProduct(productId);
		return "redirect:/" + ProductActionViewPage.HOME_PRODUCT_PAGE.getActionUrl(device);
	}
	
	/**
	 * Page view names and action urls constants.
	 */
	public static enum ProductActionViewPage {

		ERROR_PAGE("product.error", "product/error"), 
		HOME_PRODUCT_PAGE("product.home", "product/home"), 
		ADD_PRODUCT_PAGE("product.add", "product/add");

		/**
		 * Enumeration value.
		 */
		private final String pageName; // Correspondents to tiles mapping

		private final String actionUrl; // Correspondents to url-rewrite mapping

		/**
		 * Default constructor.
		 */
		private ProductActionViewPage(String pageName, String actionUrl) {
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
