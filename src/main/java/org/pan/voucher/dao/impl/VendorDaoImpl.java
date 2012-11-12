package org.pan.voucher.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pan.voucher.dao.VendorDao;
import org.pan.voucher.model.Vendor;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("vendorDao")
public class VendorDaoImpl extends BaseDaoImpl<Vendor, Integer> implements VendorDao {

	@Override
	public Vendor getVendorWithNotices(final Integer vendorId) {
		return getHibernateTemplate().execute(new HibernateCallback<Vendor>() {

			@Override
			public Vendor doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("getVendorWithNotices");
				query.setInteger("vendorId", vendorId);
				return (Vendor) query.uniqueResult();
			}
		});
	}

	@Override
	public List<Vendor> getActiveVendors() {
		return getHibernateTemplate().execute(new HibernateCallback<List<Vendor>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Vendor> doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("getActiveVendors");
				return query.list();
			}
		});
	}

	@Override
	public Vendor getVendorByVendorId(final Integer vendorId) {
		return getHibernateTemplate().execute(new HibernateCallback<Vendor>() {

			@Override
			public Vendor doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("getVendorByVendorId");
				query.setInteger("vendorId", vendorId);
				return (Vendor) query.uniqueResult();
			}
		});
	}



}
