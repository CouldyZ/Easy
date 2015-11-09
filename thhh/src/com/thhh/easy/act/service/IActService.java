package com.thhh.easy.act.service;

import java.util.List;

import com.thhh.easy.entity.Act;
import com.thhh.easy.entity.Users;

public interface IActService {

	// 根据id查询活动
	Act findActById(int id);

	// 查看活动
	public List<Act> findAct(int userid, int pageIndex, int rowCount);

	// 查看活动详细
	public Act findActDetail(int id);

	// 参加该活动的人数
	public int countPartici(int actid);

	// 参加活动
	public void addAct(int userid, int actid);

	// 举报活动
	public void reportAct(int userid, int actid, int usersRP);

	// 查看举报记录，userid
	public String findReportById(int userid, int actid);

	// 查看参与记录，userid
	public String findParticiById(int userid, int actid);

	// 取消活动
	public void cancelAct(int actid);

	// 活动正在进行，状态
	public void underway(int actid);

	// 活动完成，状态
	public void over(int actid);

	// 取消活动，判断查询
	public String checkCancel(int userid, int actid);

	// 查询该用户已经参加的活动
	public List<Act> allPartici(int userid, int pageIndex, int rowCount);

	// 保存一个发起的活动
	public void saveAct(Act act);

	Users findUserById(int id);

}
