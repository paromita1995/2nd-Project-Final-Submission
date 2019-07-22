package com.niit.Articulation.Dao;

import java.util.List;

import com.niit.Articulation.Model.BlogLike;



public interface BlogLikeDao {

	public List<BlogLike>getBlogLikesByBlogId(int blogId);//1.......!
	public boolean saveBlogLike(BlogLike blogLike);//2..............!
	public boolean isExist(int blogId,String userId);//3.............!pending
}
