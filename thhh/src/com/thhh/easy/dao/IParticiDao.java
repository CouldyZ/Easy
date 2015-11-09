package com.thhh.easy.dao;

import java.util.List;

import com.thhh.easy.entity.Act;
import com.thhh.easy.entity.Users;

public interface IParticiDao {

	// 参加活动
	public void insertPartici(Act act, Users users);

	// 根据id查找已参加的活动
	public List<Act> allPartici(int userid, int pageIndex, int rowCount);

	// 根据userid查找参与记录
	public String findParticiById(int userid, int actid);

}
