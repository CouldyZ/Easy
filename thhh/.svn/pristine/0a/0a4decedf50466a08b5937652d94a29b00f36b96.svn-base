package com.thhh.easy.dao.imp;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.thhh.easy.dao.IPostsDao;
import com.thhh.easy.entity.Posts;

/**
 * A data access object (DAO) providing persistence and search support for Posts
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.thhh.easy.entity.Posts
 * @author MyEclipse Persistence Tools
 */
public class PostsDAO extends HibernateDaoSupport implements IPostsDao {
	private static final Logger log = LoggerFactory.getLogger(PostsDAO.class);
	// property constants
	public static final String CONTENTS = "contents";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.IPostsDao#save(com.thhh.easy.entity.Posts)
	 */
	public void save(Posts transientInstance) {
		log.debug("saving Posts instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Posts persistentInstance) {
		log.debug("deleting Posts instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	//DAO层统计发过帖子的个数
	public int findCount(Integer userid){
		String hql = "select count(*) from Posts where users_id=?" ;
		List<Long> list = this.getHibernateTemplate().find(hql);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.IPostsDao#findById(java.lang.Integer)
	 */
	public Posts findById(java.lang.Integer id) {
		log.debug("getting Posts instance with id: " + id);
		try {
			Posts instance = (Posts) getHibernateTemplate().get(
					"com.thhh.easy.entity.Posts", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Posts instance) {
		log.debug("finding Posts instance by example");
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
	 * @see com.thhh.easy.dao.imp.IPostsDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Posts instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Posts as model where model."
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
		log.debug("finding all Posts instances");
		try {
			String queryString = "from Posts";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.thhh.easy.dao.imp.IPostsDao#merge(com.thhh.easy.entity.Posts)
	 */
	public Posts merge(Posts detachedInstance) {
		log.debug("merging Posts instance");
		try {
			Posts result = (Posts) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Posts instance) {
		log.debug("attaching dirty Posts instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Posts instance) {
		log.debug("attaching clean Posts instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static IPostsDao getFromApplicationContext(ApplicationContext ctx) {
		return (IPostsDao) ctx.getBean("PostsDAO");
	}
}