package com.thhh.easy.dao.imp;

import java.util.Date;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.thhh.easy.dao.ICommentsDao;
import com.thhh.easy.entity.Comments;

/**
 * A data access object (DAO) providing persistence and search support for
 * Comments entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.thhh.easy.entity.Comments
 * @author MyEclipse Persistence Tools
 */
public class CommentsDAO extends HibernateDaoSupport implements ICommentsDao {
	private static final Logger log = LoggerFactory
			.getLogger(CommentsDAO.class);
	// property constants
	public static final String CONTENTS = "contents";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.ICommentsDao#save(com.thhh.easy.entity.Comments)
	 */
	public void save(Comments transientInstance) {
		log.debug("saving Comments instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.ICommentsDao#delete(com.thhh.easy.entity.Comments)
	 */
	public void delete(Comments persistentInstance) {
		log.debug("deleting Comments instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.ICommentsDao#findById(java.lang.Integer)
	 */
	public Comments findById(java.lang.Integer id) {
		log.debug("getting Comments instance with id: " + id);
		try {
			Comments instance = (Comments) getHibernateTemplate().get(
					"com.thhh.easy.entity.Comments", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Comments instance) {
		log.debug("finding Comments instance by example");
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
	 * @see com.thhh.easy.dao.imp.ICommentsDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Comments instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Comments as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByContents(Object contents) {
		return findByProperty(CONTENTS, contents);
	}

	public List findAll() {
		log.debug("finding all Comments instances");
		try {
			String queryString = "from Comments";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.ICommentsDao#merge(com.thhh.easy.entity.Comments)
	 */
	public Comments merge(Comments detachedInstance) {
		log.debug("merging Comments instance");
		try {
			Comments result = (Comments) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Comments instance) {
		log.debug("attaching dirty Comments instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Comments instance) {
		log.debug("attaching clean Comments instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ICommentsDao getFromApplicationContext(ApplicationContext ctx) {
		return (ICommentsDao) ctx.getBean("CommentsDAO");
	}
}