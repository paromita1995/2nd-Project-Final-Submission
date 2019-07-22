package com.niit.Articulation.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Forum extends BaseDomain implements Serializable{
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_AUTO_FORUM_ID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	
	private int forumId;
	private String forumName;
	private String forumDescription;
	private String userId;
	private String userName;
	private Date forumCreationDate;
	private String forumStatus;
	private int forumCountComment;
	private int forumUserCount;
	public int getForumId() {
		return forumId;
	}
	public void setForumId(int forumId) {
		this.forumId = forumId;
	}
	public String getForumName() {
		return forumName;
	}
	public void setForumName(String forumName) {
		this.forumName = forumName;
	}
	public String getForumDescription() {
		return forumDescription;
	}
	public void setForumDescription(String forumDescription) {
		this.forumDescription = forumDescription;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getForumCreationDate() {
		return forumCreationDate;
	}
	public void setForumCreationDate(Date forumCreationDate) {
		this.forumCreationDate = forumCreationDate;
	}
	public String getForumStatus() {
		return forumStatus;
	}
	public void setForumStatus(String forumStatus) {
		this.forumStatus = forumStatus;
	}
	public int getForumCountComment() {
		return forumCountComment;
	}
	public void setForumCountComment(int forumCountComment) {
		this.forumCountComment = forumCountComment;
	}
	public int getForumUserCount() {
		return forumUserCount;
	}
	public void setForumUserCount(int forumUserCount) {
		this.forumUserCount = forumUserCount;
	}
	

}
