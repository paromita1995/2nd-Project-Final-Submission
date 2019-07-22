package com.niit.Articulation.Model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;


@Entity
@Component
public class Notification extends BaseDomain implements Serializable {
@Id
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_AUTO_NOTIFICATION_ID", allocationSize = 1)
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
private int notificationId;
private String blogTitle;
private String rejectionReason;
private String approvedOrRejected;//'APPROVED' or 'REJECTED'
private String viewed;
private String userId;//Author of the blogpost
public int getNotificationId() {
	return notificationId;
}
public void setNotificationId(int notificationId) {
	this.notificationId = notificationId;
}
public String getBlogTitle() {
	return blogTitle;
}
public void setBlogTitle(String blogTitle) {
	this.blogTitle = blogTitle;
}
public String getRejectionReason() {
	return rejectionReason;
}
public void setRejectionReason(String rejectionReason) {
	this.rejectionReason = rejectionReason;
}
public String getApprovedOrRejected() {
	return approvedOrRejected;
}
public void setApprovedOrRejected(String approvedOrRejected) {
	this.approvedOrRejected = approvedOrRejected;
}

public String getViewed() {
	return viewed;
}
public void setViewed(String viewed) {
	this.viewed = viewed;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}



}
