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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.Articulation.Dao.FriendDao;
import com.niit.Articulation.Dao.UserDetailsDao;
import com.niit.Articulation.Model.Friend;
import com.niit.Articulation.Model.UserDetails;

@RestController
public class FriendController {
	@Autowired
	Friend frnd;
	
	@Autowired
	FriendDao frndDao;
	
	@Autowired
	UserDetailsDao userdetailsDao;
	@Autowired
	UserDetails userDetails;
	
//	
@RequestMapping(value = "/myFriends" , method=RequestMethod.GET)

 public ResponseEntity<List<Friend>>getMyFriends(HttpSession session) {
	System.out.println("getmyFriends() method");
	UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
	List<Friend> Friends = frndDao.getMyFriends(user.getUserId());
	System.out.println(Friends.size());
	System.out.println("retrieving friends ");
	return new ResponseEntity<List<Friend>> (Friends, HttpStatus.OK);

}


///

@RequestMapping(value = "/addFriend/{friendId}" , method=RequestMethod.POST)			
public ResponseEntity<Friend> sendFriendRequest(@PathVariable("friendId") String friendId, HttpSession session) {
	System.out.println(" sendFriendRequest() method");
	Friend friend = new Friend();
	UserDetails user = (UserDetails) session.getAttribute("loggedInUser");
	
	Friend f=frndDao.get(user.getUserId(), friendId);
	
	System.out.println(user.getUserId()+" "+friendId);
	if(f==null)
	{
	friend.setUserId(user.getUserId());
	
	friend.setFriendId(friendId);
	friend.setStatus("N");	
	friend.setIsOnline((userdetailsDao.getUserByUserId(friendId)).getIsOnline());
	frndDao.save(friend);
	}
	
	else
	{
		f.setStatus("N");
		frndDao.update(f);
	}
	System.out.println("sendFriendRequest() method");
	return new ResponseEntity<Friend> (friend, HttpStatus.OK);
}
//
@RequestMapping(value = "/newFriendRequest",method=RequestMethod.GET )	

public ResponseEntity<List<Friend>> newFriendRequests(HttpSession session) {
	System.out.println(" newFriendRequests() method.");
	UserDetails user = (UserDetails) session.getAttribute("loggedInUser");
	List<Friend> friends = frndDao.getNewFriendRequests(user.getUserId());
	System.out.println("retrieving new friends");
	return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
}

//
@RequestMapping(value = "/acceptFriend/{friendId}"  , method=RequestMethod.PUT)			
public ResponseEntity<Friend> acceptFriendRequest(@PathVariable("friendId") String friendId, HttpSession session) {
	System.out.println(" acceptFriendRequest() method");
	UserDetails user = (UserDetails) session.getAttribute("loggedInUser");
	String userId=user.getUserId();
	System.out.println("hello" + userId );
	Friend frnd = frndDao.getRequest(userId, friendId);
	System.out.println("hii "+ frnd);
	frnd.setUserId(user.getUserId());
	System.out.println("wooo");
	frnd.setFriendId(userId);
	frnd.setUserId(friendId);
	frnd.setStatus("A");	
	frndDao.update(frnd);
	System.out.println("End of acceptFriendRequest() method");
	return new ResponseEntity<Friend> (frnd, HttpStatus.OK);
}
//

@RequestMapping(value = "/rejectFriend/{friendId}"  , method=RequestMethod.PUT)			
public ResponseEntity<Friend> rejectFriendRequest(@PathVariable("friendId") String friendId, HttpSession session) {
	System.out.println(" rejectFriendRequest() method");
	UserDetails user = (UserDetails) session.getAttribute("loggedInUser");
	String userId=user.getUserId();
	System.out.println("hello" + userId );
	Friend frnd = frndDao.getRequest(userId, friendId);
	System.out.println("hii "+ frnd);
	frnd.setUserId(user.getUserId());
	System.out.println("wooo");
	frnd.setFriendId(userId);
	frnd.setUserId(friendId);
	frnd.setStatus("R");	
	frndDao.update(frnd);
	System.out.println("End of rejectFriendRequest() method");
	return new ResponseEntity<Friend> (frnd, HttpStatus.OK);
}
//
@RequestMapping(value = "/unFriend/{friendId}" , method=RequestMethod.PUT)			
public ResponseEntity<Friend> unFriend(@PathVariable("friendId") String friendId, HttpSession session) {
	System.out.println("unFriend() method");
	UserDetails user = (UserDetails) session.getAttribute("loggedInUser");
	String userId=user.getUserId();
	Friend frnd = frndDao.get(userId, friendId);
	frnd.setUserId(user.getUserId());
	frnd.setFriendId(userId);
	frnd.setUserId(friendId);
	frnd.setStatus("Unfrnd");	
	frndDao.update(frnd);
	System.out.println("End of unFriend() method");
	return new ResponseEntity<Friend> (frnd, HttpStatus.OK);
}
}
