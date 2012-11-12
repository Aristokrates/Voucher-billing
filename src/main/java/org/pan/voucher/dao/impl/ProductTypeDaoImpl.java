package org.pan.voucher.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pan.voucher.dao.ProductTypeDao;
import org.pan.voucher.model.ProductType;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository("productTypeDao")
public class ProductTypeDaoImpl extends BaseDaoImpl<ProductType, Integer> implements ProductTypeDao {

	@Override
	public ProductType findProductTypeByName(final String productTypeName) {
		return getHibernateTemplate().execute(new HibernateCallback<ProductType>() {

			@Override
			public ProductType doInHibernate(Session paramSession) throws HibernateException, SQLException {
				Query query = paramSession.getNamedQuery("findProductTypeByName");
				query.setString("name", productTypeName);
				return (ProductType) query.uniqueResult();
			}
		});
	}

}
