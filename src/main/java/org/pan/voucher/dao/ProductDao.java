package org.pan.voucher.dao;

import java.util.List;

import org.pan.voucher.model.Product;
import org.pan.voucher.model.Vendor;

public interface ProductDao extends BaseDao<Product, Integer> {

	Product getProductById(Integer productId);

	List<Product> getProductsByVendor(Vendor vendor);

}
