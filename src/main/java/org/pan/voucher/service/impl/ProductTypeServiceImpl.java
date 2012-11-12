package org.pan.voucher.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.pan.voucher.dao.ProductTypeDao;
import org.pan.voucher.model.ProductType;
import org.pan.voucher.service.ProductTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("productTypeService")
@Transactional(propagation=Propagation.REQUIRED, readOnly=true)
public class ProductTypeServiceImpl implements ProductTypeService {
	
	@Resource private ProductTypeDao productTypeDao;

	@Override
	public List<ProductType> getAllProductTypes() {
		return productTypeDao.findAll();
	}

	@Override
	public ProductType findProductTypeByName(String name) {
		return productTypeDao.findProductTypeByName(name);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void addProductType(ProductType productType) {
		productType.setDateCreated(new Date());
		productType.setDateModified(new Date());
		productTypeDao.save(productType);
	}

}
