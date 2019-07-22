package com.niit.Articulation.Dao;

import java.util.List;

import com.niit.Articulation.Model.UserDetails;



public interface UserDetailsDao {

	public boolean saveUser(UserDetails user);//1..............!
	
	public boolean updateUser(UserDetails user);//2............!
	
	public boolean deleteUser(UserDetails user);//3............!
	
	public List<UserDetails> getAllUser();//4..................!
	
	public UserDetails UserAuthentication(String userId, String userPassword);//5..............!
	
	public UserDetails getUserByUserId(String id);//6.......................!
	
	public UserDetails getUserById(String id);

}
