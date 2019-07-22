package com.niit.Articulation.Dao;

import java.util.List;

import com.niit.Articulation.Model.ForumComment;

public interface ForumCommentDao {
	
	public boolean save(ForumComment forumComment);//1...........!
	
	public boolean update(ForumComment forumComment);//2...........!
	
	public boolean saveOrUpdate(ForumComment forumComment);//3...........!//pending
	
	public boolean delete(ForumComment forumComment);//4...........!
	
	public ForumComment getByForumCommentId(int id);//5...........!
	
	public List<ForumComment> listByUserId(String id);//6...........!
	
	public List<ForumComment> listByForumId(int id);//7...........!
	
	public List<ForumComment> getAllForumComments();//8...........!

}
