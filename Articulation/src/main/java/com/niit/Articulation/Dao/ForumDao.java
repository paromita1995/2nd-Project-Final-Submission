package com.niit.Articulation.Dao;

import java.util.List;

import com.niit.Articulation.Model.Blog;
import com.niit.Articulation.Model.Forum;

public interface ForumDao {
	
public boolean saveForum(Forum forum);//1.............!
	
	public boolean deleteForum(Forum forum);//2.............!
	
	public boolean updateForum(Forum forum);//3.............!
	
	public Forum getForumByForumId(int forumId);//4.............!
	
	public List<Forum> getAllForums();//5.............!
	
	public List<Forum> getAllApprovedForums();//6.............!
	
	public List<Forum> getAllRejectedForums();//7.............!
	
	public List<Forum> getMyForums(String userid);


}
