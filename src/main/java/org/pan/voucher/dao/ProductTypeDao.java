package org.pan.voucher.dao;

import org.pan.voucher.model.ProductType;

public interface ProductTypeDao extends BaseDao<ProductType, Integer> {
	
	ProductType findProductTypeByName(String productTypeName);

}
