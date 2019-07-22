package com.niit.Articulation.Dao;

import java.util.List;

import com.niit.Articulation.Model.Notification;

public interface NotificationDao {
	
	
		void addNotification(Notification notification);
		
		//select * from notification where userToBeNotified.email=? and viewed=0
		List<Notification> getAllNotificationsNotViewed(String userId);//glyphicon globe
		
		//select * from notification where notificationId=?
		Notification getNotification(int notificationId);
		
		//update notification set viewed=1 where notificationId=?
		void updateNotificactionViewedStatus(int notificationId);

}
