package org.pan.voucher.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.pan.voucher.mapper.VendorMapperService;
import org.pan.voucher.model.Vendor;
import org.pan.voucher.rest.model.VendorRestModel;
import org.pan.voucher.service.VendorNoticeService;
import org.pan.voucher.service.VendorService;
import org.pan.voucher.web.AbstractWebController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/manage_vendor")
public class ManageVendorController extends AbstractWebController {

	private static final Logger log = LoggerFactory.getLogger(ManageVendorController.class);

	@Resource private VendorService vendorService;
	@Resource private VendorNoticeService vendorNoticeService;
	@Resource private VendorMapperService vendorMapper;

	@RequestMapping(value="/home.do", method=RequestMethod.GET)
	public String showVendorHome(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "device", required = true) String device, ModelMap modelMap) {

		setupMenuPrivilegues(modelMap);
		List<Vendor> vendors = vendorService.getAllVendors();
		List<VendorRestModel> vendorRestModelList = vendorMapper.buildVendorListModel(vendors);
		modelMap.addAttribute("vendorList", vendorRestModelList);

		return VendorActionViewPage.HOME_VENDOR_PAGE.getPageName(device);
	}
	
	@RequestMapping(value="/add_vendor.do", method=RequestMethod.GET)
	public String showAddVendorPage(HttpServletRequest request, HttpSession session,
			@RequestParam(value = "device", required = true) String device, ModelMap modelMap) {
		
		setupMenuPrivilegues(modelMap);

		modelMap.addAttribute("vendorModel", new VendorRestModel());

		return VendorActionViewPage.ADD_VENDOR_PAGE.getPageName(device);
	}

	@RequestMapping(value="/add_vendor.do", method=RequestMethod.POST)
	public String addVendor(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value = "device", required = true) String device,
			@ModelAttribute(value = "vendorModel") VendorRestModel vendorRestModel, ModelMap modelMap) {

		Vendor vendor = vendorMapper.buildVendorFromModel(vendorRestModel);
		vendorService.addVendor(vendor);

		return "redirect:/" + VendorActionViewPage.HOME_VENDOR_PAGE.getActionUrl(device);
	}
	
	@RequestMapping(value="/delete_vendor.do", method=RequestMethod.GET)
	public String deleteVendor(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(value = "device", required = true) String device,
			@RequestParam(value = "vendorId", required = false) Integer vendorId, ModelMap modelMap) {

		vendorService.deleteVendor(vendorId);
		return "redirect:/" + VendorActionViewPage.HOME_VENDOR_PAGE.getActionUrl(device);
	}

	/**
	 * Page view names and action urls constants.
	 */
	public static enum VendorActionViewPage {

		ERROR_PAGE("vendor.error", "vendor/error"), 
		HOME_VENDOR_PAGE("vendor.home", "vendor/home"), 
		ADD_VENDOR_PAGE("vendor.add", "vendor/add");

		/**
		 * Enumeration value.
		 */
		private final String pageName; // Correspondents to tiles mapping

		private final String actionUrl; // Correspondents to url-rewrite mapping

		/**
		 * Default constructor.
		 */
		private VendorActionViewPage(String pageName, String actionUrl) {
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
