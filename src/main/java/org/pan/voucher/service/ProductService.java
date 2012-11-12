package org.pan.voucher.service;

import org.pan.voucher.model.Product;

public interface ProductService {

	Product getProductById(Integer productId);

	void addProduct(Product product);

	void updateProduct(Product existingProduct, Product modelProduct);
	
	void deleteProduct(Integer productId);

}
