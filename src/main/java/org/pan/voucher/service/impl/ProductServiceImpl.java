package org.pan.voucher.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.pan.voucher.dao.ProductDao;
import org.pan.voucher.exception.InvalidDataException;
import org.pan.voucher.model.Product;
import org.pan.voucher.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("productService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class ProductServiceImpl implements ProductService {
	
	@Resource private ProductDao productDao;

	@Override
	public Product getProductById(Integer productId) {				
		Product product = productDao.getProductById(productId);		
		if (product == null) {
			throw new InvalidDataException("Product not found: " + "[ " + productId + " ]"); 
		}
		
		return product;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addProduct(Product product) {
		
		product.setLastModifiedDate(new Date());
		product.setDateCreated(new Date());
		productDao.save(product);		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateProduct(Product existingProduct, Product modelProduct) {
		
		existingProduct.updateStateFromProductModel(modelProduct);
		productDao.merge(existingProduct);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteProduct(Integer productId) {
		
		Product product = productDao.findById(productId);
		if (product == null) {
			throw new InvalidDataException("Product not found: " + "[ " + productId + " ]"); 
		}
		product.setIsDeleted(true);
		productDao.merge(product);		
	}
}
