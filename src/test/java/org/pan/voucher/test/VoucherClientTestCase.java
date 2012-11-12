package org.pan.voucher.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.pan.voucher.dao.UserTypeDao;
import org.pan.voucher.model.User;
import org.pan.voucher.model.UserType;
import org.pan.voucher.model.UserTypeName;
import org.pan.voucher.remoting.VoucherRemoteService;
import org.pan.voucher.remoting.model.Voucher;
import org.pan.voucher.service.TransactionService;
import org.pan.voucher.service.UserSessionService;

public class VoucherClientTestCase extends BaseTestCase {
	
	@Resource private VoucherRemoteService service;
	@Resource private UserSessionService userSession;
	@Resource private TransactionService transactionService;
	@Resource private UserTypeDao userTypeDao;
	
	@Test
	public void testClient() {
//		Voucher voucher = service.getVoucher("vodacom", 2);
//		System.out.println(voucher);
		
		List<Voucher> voucherList = service.getVouchers("vodacom", 2, 3);
		System.out.println(voucherList);
	}
	
	@Test 
	public void testBuyingProduct() {
		String sessionToken = "";
		Integer productId = 2;
		User user = userSession.getUserForSession(sessionToken);
		transactionService.buyProduct(user, productId);
	}
	
	@Test 
	public void testUserTypeSave() {	
		
		UserType userType1 = userTypeDao.findUserTypeByName("Vendor");
		UserType userType2 = userTypeDao.findUserTypeByName("Reseller");
		UserType userType3 = userTypeDao.findUserTypeByName("Admin");
		
		UserType type = new UserType();
		type.setDateCreated(new Date());
		type.setDescription("Pance4");
		type.setLastModifiedDate(new Date());
		type.setName(UserTypeName.SuperAdmin);
		type.addUserType(userType1);
		type.addUserType(userType2);
		type.addUserType(userType3);
		userTypeDao.save(type);
	}

}
