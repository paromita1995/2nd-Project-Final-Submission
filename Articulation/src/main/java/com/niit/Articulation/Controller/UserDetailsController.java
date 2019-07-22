
package com.niit.Articulation.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.Articulation.Dao.UserDetailsDao;
import com.niit.Articulation.Model.UserDetails;


@RestController
public class UserDetailsController {
	
	@Autowired
	UserDetailsDao ud;
	@Autowired
	UserDetails userDetails;
	

//1.add user...........!
		@RequestMapping(value="/joinUser/", method=RequestMethod.POST)
		public ResponseEntity<UserDetails> creatUser(@RequestBody UserDetails user){
			if(ud.getUserByUserId(user.getUserId())==null){
				user.setRole("USER");
				user.setIsOnline("NO");
				user.setStatus("ACTIVE");
				ud.saveUser(user);
				return new ResponseEntity<UserDetails>(user,HttpStatus.OK);
			}
			user.setErrorMessage("User already exist with id : "+user.getUserId());
			return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
	}
		
//2.Update userProfile by userId...........!
		
		@RequestMapping(value="/updateUser/{id}", method=RequestMethod.PUT)
		public ResponseEntity<UserDetails> updateuser(@PathVariable("id") String id, @RequestBody UserDetails user){
			if(ud.getAllUser()==null){
				user =new UserDetails();
				user.setErrorMessage("User does not exist with id : " +user.getUserId());
				return new ResponseEntity<UserDetails>(user, HttpStatus.NO_CONTENT);
			}
			ud.updateUser(user);
			return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
		}
		
//3.Delete User..................Admin!		
		
		@RequestMapping(value="/deleteUser/{id}", method=RequestMethod.DELETE)
		public ResponseEntity<UserDetails>deleteUser(@PathVariable("id")String id){
			UserDetails users=ud.getUserByUserId(id);
			if(users == null){
				users = new UserDetails();
				users.setErrorMessage("User does not exist with id : " + id);
				return new ResponseEntity<UserDetails>(users, HttpStatus.NOT_FOUND);
				
			}
			ud.deleteUser(users);
			return new ResponseEntity<UserDetails>(users, HttpStatus.OK);
		}
//4.getAll UserList..............!
	@RequestMapping(value="/userlist", method=RequestMethod.GET)
	public ResponseEntity<List<UserDetails>> getAllUser(){
		List<UserDetails>list=ud.getAllUser();
		if(list.isEmpty()){
			return new ResponseEntity<List<UserDetails>>(list, HttpStatus.NO_CONTENT);
		}
		System.out.println(list.size());
		System.out.println("retrieving userlist ");
		return new ResponseEntity<List<UserDetails>>(list, HttpStatus.OK);

}
	

//5.Login and UserAuthentication.............!
	@RequestMapping(value = "/profile/authenticate/", method = RequestMethod.POST)
	public ResponseEntity<UserDetails> UserAuthentication(@RequestBody UserDetails user, HttpSession session){
		user = ud.UserAuthentication(user.getUserId(), user.getPassword());
		if(user == null){
			user = new UserDetails();
			user.setErrorMessage("Invalid userId or password...");
		}
		else {
			session.setAttribute("loggedInUser", user);
			System.out.println("logging session set ");
			session.setAttribute("loggedInUserID", user.getUserId());
			user.setIsOnline("Yes");
			user.setStatus("Active");
			ud.updateUser(user);
		}
		return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
	}
	
	
//6.check userprofile by {id}..........!
	@RequestMapping(value= "/userprofile/{id}",method=RequestMethod.GET)
	public ResponseEntity<UserDetails>getUserByUserId(@PathVariable("id")String id){
		UserDetails user = ud.getUserByUserId(id);
		if (user == null){
			user = new UserDetails();
			user.setErrorMessage("User does not exist with id : " + id);
				return new ResponseEntity<UserDetails>(user, HttpStatus.NOT_FOUND);
				
		}
		return new ResponseEntity<UserDetails>(user, HttpStatus.OK);
	}
	
	
//7. log-out by user profile 

	@RequestMapping(value= "/logout/{id}",method=RequestMethod.PUT)
	public ResponseEntity<UserDetails>logout(@PathVariable("id") String id ,@RequestBody UserDetails user,HttpSession session){
		System.out.println("hello  "+user.getName());	
		user.setIsOnline("NO");
		
		ud.updateUser(user);
		
			session.invalidate();
			 
			
				return new ResponseEntity<UserDetails>(new UserDetails(), HttpStatus.OK);
				
		}
	//8. my profile
	
	
}
	