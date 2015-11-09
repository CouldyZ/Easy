package com.thhh.easy.act.service.imp;

import java.util.List;

import com.thhh.easy.act.service.IActService;
import com.thhh.easy.dao.IActDao;
import com.thhh.easy.dao.IParticiDao;
import com.thhh.easy.dao.IReportDao;
import com.thhh.easy.dao.IUsersDao;
import com.thhh.easy.entity.Act;
import com.thhh.easy.entity.Users;

public class ActService implements IActService {

	private IActDao actDao;
	private IParticiDao particiDao;
	private IReportDao reportDao;
	private IUsersDao usersDao;

	/**
	 * 根据id查询活动
	 */
	public Act findActById(int id) {
		// TODO Auto-generated method stub
		return actDao.findActById(id);
	}

	public Users findUserById(int id) {
		return actDao.findUserById(id);
	}

	/**
	 * 查看活动 ,分页显示
	 */
	public List<Act> findAct(int userid, int pageIndex, int rowCount) {

		// List<Act> listAct = null;

		// DetachedCriteria criteria = DetachedCriteria.forClass(Act.class);
		// criteria.addOrder(Order.desc("create.date"));

		// listAct = actDao.getHibernateTemplate().findByCriteria(criteria,
		// (pageIndex - 1) * rowCount, rowCount);

		return actDao.findAct(userid, pageIndex, rowCount);
	}

	/**
	 * 查看单个活动详情， id=act.id
	 * 
	 * @return
	 */
	public Act findActDetail(int id) {

		return actDao.findActDetail(id);
	}

	/**
	 * 参加该活动的人数
	 */
	// 参加该活动的人数
	public int countPartici(int actid) {
		return actDao.countPartici(actid);
	}

	/**
	 * 参加活动，userid，actid
	 * 
	 * @return
	 */
	public void addAct(int userid, int actid) {

		Act act = actDao.findActById(actid);
		Users users = actDao.findUserById(userid);

		particiDao.insertPartici(act, users);
	}

	/**
	 * 举报活动,举报人userid,举报的活动actid
	 */
	public void reportAct(int userid, int actid, int userRP) {

		Users users = actDao.findUserById(userid);
		Act act = actDao.findActById(actid);
		userRP = userRP - 1;
		reportDao.updateRP(userid, userRP);
		reportDao.report(users, act);

	}

	/**
	 * 查看举报记录，userid
	 * 
	 * @return
	 */
	public String findReportById(int userid, int actid) {
		Users users = actDao.findUserById(userid);
		return reportDao.findReportById(userid, actid);

	}

	/**
	 * 查看参与记录，userid
	 * 
	 * @return
	 */
	public String findParticiById(int userid, int actid) {
		Users users = actDao.findUserById(userid);
		return particiDao.findParticiById(userid, actid);

	}

	/**
	 * 查询已经参加的活动
	 * 
	 * @return
	 */
	public List<Act> allPartici(int userid, int pageIndex, int rowCount) {
		// Users users = new Users();
		// users = actDao.findUserById(userid);

		// List<Act> list = null;
		// DetachedCriteria criteria = DetachedCriteria.forClass(Partici.class);
		// criteria.add(Restrictions.eq("users.id", userid));
		// criteria.addOrder(Order.desc("dates"));
		// list = actDao.getHibernateTemplate().findByCriteria(criteria,
		// (pageIndex - 1) * rowCount, rowCount);

		return particiDao.allPartici(userid, pageIndex, rowCount);
		// return list;
	}

	/**
	 * 保存一个发起的活动
	 */
	public void saveAct(Act act) {
		actDao.saveAct(act);
	}

	/**
	 * 活动正在进行，状态
	 */
	public void underway(int actid) {
		actDao.underway(actid);
	}

	/**
	 * 活动完成，状态
	 */
	public void over(int actid) {
		actDao.underway(actid);
	}

	/**
	 * 取消活动
	 * 
	 * @return
	 */
	public void cancelAct(int actid) {
		// TODO Auto-generated method stub

		actDao.cancelAct(actid);

	}

	/**
	 * 取消活动的判断查询
	 */
	public String checkCancel(int userid, int actid) {

		return actDao.checkCancel(userid, actid);
	}

	public IActDao getActDao() {
		return actDao;
	}

	public void setActDao(IActDao actDao) {
		this.actDao = actDao;
	}

	public IParticiDao getParticiDao() {
		return particiDao;
	}

	public void setParticiDao(IParticiDao particiDao) {
		this.particiDao = particiDao;
	}

	public IReportDao getReportDao() {
		return reportDao;
	}

	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}

	public IUsersDao getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(IUsersDao usersDao) {
		this.usersDao = usersDao;
	}

}
