package com.thhh.easy.posts.service.imp;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.thhh.easy.dao.ICommentsDao;
import com.thhh.easy.dao.IPostsDao;
import com.thhh.easy.dao.IUsersDao;
import com.thhh.easy.entity.Comments;
import com.thhh.easy.entity.Posts;
import com.thhh.easy.entity.Users;
import com.thhh.easy.posts.service.ICommentsService;

public class CommentsService implements ICommentsService {

	private ICommentsDao commentsDao ;
	private IPostsDao postsDao ;
	private IUsersDao usersDao ;
	

	/**
	 * 根据贴子id查找贴子评论
	 */
	public List<Comments> findCommentByPostsId(int pageIndex, int rowCount ,int postsId) {
		List<Comments> listComments = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(Comments.class) ;
		criteria.add(Restrictions.eq("posts.id", postsId));
		criteria.addOrder(Order.desc("dates")) ;
		listComments = commentsDao.getHibernateTemplate().findByCriteria(
				criteria, (pageIndex - 1) * rowCount, rowCount);
		
		for (Comments comments : listComments) {
			Hibernate.initialize(comments.getUsers());
		}
		return listComments;
	}
	
	/**
	 * 根据id查找用户
	 */
	public Users findUserById(Integer id) {
		// TODO Auto-generated method stub
		return usersDao.findById(id);
	}

	/**
	 * 根据id查找贴子
	 */
	public Posts findPostsById(Integer id) {
		// TODO Auto-generated method stub
		return postsDao.findById(id);
	}


	/**
	 * 保存贴子
	 */
	public void saveComments(Comments comments) {
		
		commentsDao.save(comments) ;
	}

	public ICommentsDao getCommentsDao() {
		return commentsDao;
	}

	public void setCommentsDao(ICommentsDao commentsDao) {
		this.commentsDao = commentsDao;
	}

	public IPostsDao getPostsDao() {
		return postsDao;
	}

	public void setPostsDao(IPostsDao postsDao) {
		this.postsDao = postsDao;
	}

	public IUsersDao getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(IUsersDao usersDao) {
		this.usersDao = usersDao;
	}



}
