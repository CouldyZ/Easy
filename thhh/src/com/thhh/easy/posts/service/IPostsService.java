package com.thhh.easy.posts.service;

import java.util.List;

import com.thhh.easy.entity.Collects;
import com.thhh.easy.entity.Likes;
import com.thhh.easy.entity.Posts;
import com.thhh.easy.entity.Users;

public interface IPostsService {

	List<Posts> findNewPosts(int pageIndex, int rowCount);

	List<int[]> findHotPosts(int pageIndex, int rowCount);

	int findPostsLikesCount(Integer id);

	Posts findPostsById(int id);

	Users findUserById(Integer id);

	void saveLikes(Likes likes);

	boolean userIsLikes(Integer userId, Integer postsId);

	void deleteLikes(Integer userId, Integer postsId);

	void saveCollects(Collects collects);

	boolean userIsCollects(Integer userId, Integer postsId);

	void deleteCollects(Integer userId, Integer postsId);

	List<Collects> findCollects(int pageIndex, int rowCount, Integer userId);

	List<Posts> findUserPosts(int pageIndex, int rowCount, Integer userId);

	void savePosts(Posts posts);

}
