package com.thhh.easy.dao.imp;

import java.util.Date;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.thhh.easy.dao.ICollectsDao;
import com.thhh.easy.entity.Collects;

/**
 * A data access object (DAO) providing persistence and search support for
 * Collects entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.thhh.easy.entity.Collects
 * @author MyEclipse Persistence Tools
 */
public class CollectsDAO extends HibernateDaoSupport implements ICollectsDao {
	private static final Logger log = LoggerFactory
			.getLogger(CollectsDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.ICollectsDao#save(com.thhh.easy.entity.Collects)
	 */
	public void save(Collects transientInstance) {
		log.debug("saving Collects instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.ICollectsDao#delete(com.thhh.easy.entity.Collects)
	 */
	public void delete(Collects persistentInstance) {
		log.debug("deleting Collects instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.ICollectsDao#findById(java.lang.Integer)
	 */
	public Collects findById(java.lang.Integer id) {
		log.debug("getting Collects instance with id: " + id);
		try {
			Collects instance = (Collects) getHibernateTemplate().get(
					"com.thhh.easy.entity.Collects", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Collects instance) {
		log.debug("finding Collects instance by example");
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
	 * @see com.thhh.easy.dao.imp.ICollectsDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Collects instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Collects as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Collects instances");
		try {
			String queryString = "from Collects";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.ICollectsDao#merge(com.thhh.easy.entity.Collects)
	 */
	public Collects merge(Collects detachedInstance) {
		log.debug("merging Collects instance");
		try {
			Collects result = (Collects) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Collects instance) {
		log.debug("attaching dirty Collects instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Collects instance) {
		log.debug("attaching clean Collects instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ICollectsDao getFromApplicationContext(ApplicationContext ctx) {
		return (ICollectsDao) ctx.getBean("CollectsDAO");
	}
}