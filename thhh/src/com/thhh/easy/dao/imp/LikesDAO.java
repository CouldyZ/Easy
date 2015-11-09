package com.thhh.easy.dao.imp;

import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.thhh.easy.dao.ILikesDao;
import com.thhh.easy.entity.Likes;

/**
 * A data access object (DAO) providing persistence and search support for Likes
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.thhh.easy.entity.Likes
 * @author MyEclipse Persistence Tools
 */
public class LikesDAO extends HibernateDaoSupport implements ILikesDao {
	private static final Logger log = LoggerFactory.getLogger(LikesDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.ILikesDao#save(com.thhh.easy.entity.Likes)
	 */
	public void save(Likes transientInstance) {
		log.debug("saving Likes instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.ILikesDao#delete(com.thhh.easy.entity.Likes)
	 */
	public void delete(Likes persistentInstance) {
		log.debug("deleting Likes instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.ILikesDao#findById(java.lang.Integer)
	 */
	public Likes findById(java.lang.Integer id) {
		log.debug("getting Likes instance with id: " + id);
		try {
			Likes instance = (Likes) getHibernateTemplate().get(
					"com.thhh.easy.entity.Likes", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Likes instance) {
		log.debug("finding Likes instance by example");
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
	 * @see com.thhh.easy.dao.imp.ILikesDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Likes instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Likes as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all Likes instances");
		try {
			String queryString = "from Likes";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.ILikesDao#merge(com.thhh.easy.entity.Likes)
	 */
	public Likes merge(Likes detachedInstance) {
		log.debug("merging Likes instance");
		try {
			Likes result = (Likes) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Likes instance) {
		log.debug("attaching dirty Likes instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Likes instance) {
		log.debug("attaching clean Likes instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ILikesDao getFromApplicationContext(ApplicationContext ctx) {
		return (ILikesDao) ctx.getBean("LikesDAO");
	}
}