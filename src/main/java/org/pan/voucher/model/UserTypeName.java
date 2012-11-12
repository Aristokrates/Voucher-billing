package org.pan.voucher.model;

import java.util.ArrayList;
import java.util.List;

public enum UserTypeName {
	
	Reseller,
	Vendor,
	Admin,
	SuperAdmin;
	
	private static List<UserTypeName> admins;
	private static List<UserTypeName> superAdmins;

	public static List<UserTypeName> getAdminUserTypes() {
		if (admins == null) {
			admins = new ArrayList<UserTypeName>();
			admins.add(SuperAdmin);
			admins.add(Admin);
		}
		return admins;
	}
	
	public static List<UserTypeName> getSuperAdminUserTypes() {
		if (superAdmins == null) {
			superAdmins = new ArrayList<UserTypeName>();
			superAdmins.add(SuperAdmin);
		}
		return superAdmins;
	}

}
