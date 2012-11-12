package org.pan.voucher.service;

import java.util.List;

import org.pan.voucher.model.ProductType;

public interface ProductTypeService {
	
	List<ProductType> getAllProductTypes();
	
	ProductType findProductTypeByName(String name);
	
	void addProductType(ProductType productType);

}
