package com.thhh.easy.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.thhh.easy.entity.Act;
import com.thhh.easy.entity.Users;

public interface IActDao {

	public abstract HibernateTemplate getHibernateTemplate();

	// 查看活动
	public List<Act> findAct(int userid, int pageIndex, int rowCount);

	// 查看活动详细
	public Act findActDetail(int id);

	// 参加该活动的人数
	public int countPartici(int actid);

	// 根据id查询活动
	public Act findActById(int id);

	public Users findUserById(int id);

	// 取消活动
	public void cancelAct(int actid);

	// 活动正在进行，状态
	public void underway(int actid);

	// 活动完成，状态
	public void over(int actid);

	// 取消活动，判断查询
	public String checkCancel(int userid, int actid);

	// 保存活动
	public void saveAct(Act act);

}
