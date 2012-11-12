package org.pan.voucher.test;

import java.math.BigDecimal;

import org.junit.Test;
import org.pan.voucher.model.Product;
import org.pan.voucher.rest.model.UserLoginRestModel;
import org.pan.voucher.rest.model.UserRegisterModel;
import org.pan.voucher.test.json.GenericSerializer;
import org.pan.voucher.test.json.JacksonJsonSerializer;

public class JsonTestCase {
	
	@Test
	public void testUserLoginJson() {
		GenericSerializer serializer = new JacksonJsonSerializer();
		
		UserLoginRestModel loginModel = new UserLoginRestModel("pance1", "pance1");
		
		String str = serializer.toJson(loginModel);
		
		System.out.println(str);
		
		UserLoginRestModel model = serializer.fromJson(str, UserLoginRestModel.class);
		
		System.out.println(model.getUsername());
	}

	@Test
	public void testUserRegisterJson() {
		GenericSerializer serializer = new JacksonJsonSerializer();
		
		UserRegisterModel model = new UserRegisterModel();
		
		model.setUserType("Admin");
		model.setUsername("pance1");
		model.setPassword("pance1");
		
		model.setCountry("Country");
		model.setPostcode("postocode1");
		
		model.setProfileEmail("pance.isajeski@gmail.com");
		
		String str = serializer.toJson(model);
		
		System.out.println(str);
		
		UserRegisterModel modelNew = serializer.fromJson(str, UserRegisterModel.class);
		
		System.out.println(modelNew.getUsername());
	}
	
	@Test
	public void testProductJson() {
		
		GenericSerializer serializer = new JacksonJsonSerializer();
		Product product = new Product();
		product.setCommision(BigDecimal.valueOf(0.13));
		product.setCostPrice(BigDecimal.valueOf(1.82));
		product.setDescription("BLALB");
		product.setName("PanceProd");
		product.setSalesPrice(BigDecimal.valueOf(2.00));
		product.setTransfee(BigDecimal.valueOf(0.05));
		String productJson = serializer.toJson(product);
		
		System.out.println(productJson);
	}
	
}
