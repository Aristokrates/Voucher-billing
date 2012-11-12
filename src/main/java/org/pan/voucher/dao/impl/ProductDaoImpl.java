package org.pan.voucher.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pan.voucher.dao.ProductDao;
import org.pan.voucher.model.Product;
import org.pan.voucher.model.Vendor;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("productDao")
public class ProductDaoImpl extends BaseDaoImpl<Product, Integer> implements ProductDao {

	@Override
	public Product getProductById(final Integer productId) {
		return getHibernateTemplate().execute(new HibernateCallback<Product>() {

			@Override
			public Product doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("getProductById");
				query.setInteger("productId", productId);
				return (Product) query.uniqueResult();
			}
		});
	}

	@Override
	public List<Product> getProductsByVendor(final Vendor vendor) {
		return getHibernateTemplate().execute(new HibernateCallback<List<Product>>() {

			@SuppressWarnings("unchecked")
			@Override
			public List<Product> doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("getProductsByVendor");
				query.setEntity("vendor", vendor);
				return query.list();
			}
		});
	}
	
	

}
