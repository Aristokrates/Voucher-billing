package org.pan.voucher.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.pan.voucher.exception.InvalidDataException;
import org.pan.voucher.model.Product;
import org.pan.voucher.model.ProductType;
import org.pan.voucher.model.Vendor;
import org.pan.voucher.rest.model.ProductRestModel;
import org.pan.voucher.rest.model.ProductTypeModel;
import org.pan.voucher.service.ProductTypeService;
import org.pan.voucher.service.VendorService;

public class ProductMapperService {
	
	@Resource private ProductTypeService productTypeService;
	@Resource private VendorService vendorService;
	
	public Product buildProductFromModel(ProductRestModel productModel) {
		
		Product product = new Product();
		buildProductData(product, productModel);
		return product;
	}
	
	public ProductRestModel buildModelFromProduct(Product product) {
		
		ProductRestModel modelProduct = new ProductRestModel();
		modelProduct.setProductId(product.getId());
		modelProduct.setName(product.getName());
		modelProduct.setCommision(product.getCommision());
		modelProduct.setCostPrice(product.getCostPrice());
		modelProduct.setDescription(product.getDescription());
		modelProduct.setProductType(product.getProductType().getName());
		modelProduct.setSalesPrice(product.getSalesPrice());
		modelProduct.setTransfee(product.getTransfee());	
		
		return modelProduct;
	}
	
	public List<ProductRestModel> buildModelListFromProducts(List<Product> vendorProducts) {
		
		List<ProductRestModel> projectListModel = new ArrayList<ProductRestModel>();
		
		for (Product product : vendorProducts) {				
			projectListModel.add(buildModelFromProduct(product));
		}
		
		return projectListModel;
	}
	
	public List<ProductTypeModel> buildProductTypeListModel(List<ProductType> productTypes) {
		
		List<ProductTypeModel> modelList = new ArrayList<ProductTypeModel>();
		for (ProductType productType : productTypes) {
			modelList.add(new ProductTypeModel(productType.getId(), productType.getName(), 
					productType.getDescription()));
		}
		return modelList;
	}

	private void buildProductData(Product product, ProductRestModel productModel) {
		
		ProductType productType = productTypeService.findProductTypeByName(productModel.getProductType());
		if (productType == null) {
			throw new InvalidDataException("Product Type must be set");
		}
		
		product.setProductType(productType);
		
		Vendor vendor = vendorService.getVendorByVendorId(productModel.getVendorId());
		if (vendor == null) {
			throw new InvalidDataException("Vendor must be set");
		}
		
		product.setVendor(vendor);
		
		product.setId(productModel.getProductId());
		
		product.setCommision(productModel.getCommision());
		product.setCostPrice(productModel.getCostPrice());
		product.setDescription(productModel.getDescription());
		product.setName(productModel.getName());

		product.setSalesPrice(productModel.getSalesPrice());
		product.setTransfee(productModel.getTransfee());
	}

}
