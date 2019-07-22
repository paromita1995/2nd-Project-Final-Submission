package com.niit.Articulation.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.Articulation.Dao.NotificationDao;
import com.niit.Articulation.Model.Notification;
import com.niit.Articulation.Model.UserDetails;

@RestController
public class NotificationController {
	
	@Autowired
	NotificationDao notificationDao;
	@Autowired
	UserDetails userdetailDao;
	@Autowired
	Notification notification;
	
	
	
		@RequestMapping(value="/getnotificationsnotviewed",method=RequestMethod.GET)
		public ResponseEntity<List<Notification>> getNotificationsNotViewed(HttpSession session){
			//Check for Authenticated- only logged user can post a blog 
				UserDetails user=(UserDetails)session.getAttribute("loggedInUser");
					if(user==null){//if the user is not yet logged in,user is not an authenticated user
						notification.setErrorCode("404");
						notification.setErrorMessage("Notification Not Created");
						return new ResponseEntity<List<Notification>>(HttpStatus.UNAUTHORIZED);
					}
					List<Notification> notificationsNotViewed=notificationDao.getAllNotificationsNotViewed(user.getUserId());
					
					return new ResponseEntity<List<Notification>>(notificationsNotViewed,HttpStatus.OK);
		}
		
		
		
		@RequestMapping(value="/getnotification/{notificationId}",method=RequestMethod.GET)
		public ResponseEntity<?> getNotification(HttpSession session,@PathVariable int notificationId){
			UserDetails user=(UserDetails)session.getAttribute("loggedInUser");
			if(user==null){//if the user is not yet logged in,user is not an authenticated user
				notification.setErrorCode("404");
				notification.setErrorMessage("Notification Not Created");
				return new ResponseEntity<List<Notification>>(HttpStatus.UNAUTHORIZED);
			}
			Notification notification=notificationDao.getNotification(notificationId);
			return new ResponseEntity<Notification>(notification,HttpStatus.OK);
		}
		
		@RequestMapping(value="/updatenotification/{notificationId}",method=RequestMethod.PUT)
		public ResponseEntity<?> updateNotificationViewedStatus(HttpSession session,@PathVariable int notificationId){
			UserDetails user=(UserDetails)session.getAttribute("loggedInUser");
			if(user==null){//if the user is not yet logged in,user is not an authenticated user
				notification.setErrorCode("404");
				notification.setErrorMessage("Notification Not Created");
				return new ResponseEntity<List<Notification>>(HttpStatus.UNAUTHORIZED);
			}
			notificationDao.updateNotificactionViewedStatus(notificationId);
			return new ResponseEntity<Notification>(HttpStatus.OK);
		}

}
