package org.pan.voucher.test;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class NumberTest {
	
	public static void main(String[] args) throws NumberParseException {
		PhoneNumberUtil util = PhoneNumberUtil.getInstance();
		PhoneNumber num = util.parse("0038970563236", "FR");
		System.out.println(num.getCountryCode());
		boolean valid = util.isValidNumber(num);
		System.out.println(valid);
	}

}
