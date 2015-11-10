package com.thhh.easy.dao.imp;

import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.thhh.easy.dao.IShopDao;
import com.thhh.easy.entity.Shop;

/**
 * A data access object (DAO) providing persistence and search support for Shop
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.thhh.easy.entity.Shop
 * @author MyEclipse Persistence Tools
 */
public class ShopDAO extends HibernateDaoSupport implements IShopDao {
	private static final Logger log = LoggerFactory.getLogger(ShopDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String PHONE = "phone";
	public static final String SHORTCUT = "shortcut";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.IShopDao#save(com.thhh.easy.entity.Shop)
	 */
	public void save(Shop transientInstance) {
		log.debug("saving Shop instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.IShopDao#delete(com.thhh.easy.entity.Shop)
	 */
	public void delete(Shop persistentInstance) {
		log.debug("deleting Shop instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.IShopDao#findById(java.lang.Integer)
	 */
	public Shop findById(java.lang.Integer id) {
		log.debug("getting Shop instance with id: " + id);
		try {
			Shop instance = (Shop) getHibernateTemplate().get(
					"com.thhh.easy.entity.Shop", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Shop instance) {
		log.debug("finding Shop instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.IShopDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Shop instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Shop as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByAddress(Object address) {
		return findByProperty(ADDRESS, address);
	}

	public List findByPhone(Object phone) {
		return findByProperty(PHONE, phone);
	}

	public List findByShortcut(Object shortcut) {
		return findByProperty(SHORTCUT, shortcut);
	}

	public List findAll() {
		log.debug("finding all Shop instances");
		try {
			String queryString = "from Shop";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.IShopDao#merge(com.thhh.easy.entity.Shop)
	 */
	public Shop merge(Shop detachedInstance) {
		log.debug("merging Shop instance");
		try {
			Shop result = (Shop) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Shop instance) {
		log.debug("attaching dirty Shop instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Shop instance) {
		log.debug("attaching clean Shop instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IShopDao getFromApplicationContext(ApplicationContext ctx) {
		return (IShopDao) ctx.getBean("ShopDAO");
	}
}