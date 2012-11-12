package org.pan.voucher.test.service;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.pan.voucher.model.Product;
import org.pan.voucher.model.ProductType;
import org.pan.voucher.service.ProductService;
import org.pan.voucher.service.ProductTypeService;
import org.pan.voucher.service.VendorService;
import org.pan.voucher.test.BaseTestCase;

public class ProductServiceTestCase extends BaseTestCase {
	
	@Resource private ProductService productService;
	@Resource private ProductTypeService productTypeService;
	@Resource private VendorService vendorService;
	
	@Test
	public void addProductType() {

		ProductType pt = new ProductType();
		pt.setName("First");
		pt.setDescription("Nothing special");
		productTypeService.addProductType(pt);
	}
	
	@Test
	public void testAddProduct() {
		
		Product product = new Product();
		product.setCommision(BigDecimal.valueOf(0.13));
		product.setCostPrice(BigDecimal.valueOf(1.82));
		product.setDescription("pance first tdescription");
		product.setName("Pance product");
		product.setProductType(productTypeService.findProductTypeByName("First"));
		product.setSalesPrice(BigDecimal.valueOf(2.00));
		product.setTransfee(BigDecimal.valueOf(0.05));
		product.setVendor(vendorService.getVendorByVendorId(1));
		productService.addProduct(product);
	}
	
	@Test
	public void testGetProduct() {
		
		Product product = productService.getProductById(1);
		Assert.assertNotNull(product);
	}
	
	@Test
	public void testDeleteProduct() {
		
		productService.deleteProduct(2);
		Product product = productService.getProductById(2);
		Assert.assertNull(product);
	}

}
