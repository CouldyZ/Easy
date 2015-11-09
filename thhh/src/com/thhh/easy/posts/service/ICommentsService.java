package com.thhh.easy.posts.service;

import java.util.List;

import com.thhh.easy.entity.Comments;
import com.thhh.easy.entity.Posts;
import com.thhh.easy.entity.Users;

public interface ICommentsService {

	List<Comments> findCommentByPostsId(int pageIndex, int rowCount ,int postsId);

	Users findUserById(Integer id);

	Posts findPostsById(Integer id);

	void saveComments(Comments comments);

}