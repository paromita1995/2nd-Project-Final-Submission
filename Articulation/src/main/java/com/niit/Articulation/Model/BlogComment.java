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
public class BlogComment extends BaseDomain implements Serializable{
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_AUTO_BLOG_COMMENT_ID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	private int blogCommentId;
	private int blogId;
	private String userId;
	private String blogComment;
	private String userName;
	private Date blogCommentDate;
	public int getBlogCommentId() {
		return blogCommentId;
	}
	public void setBlogCommentId(int blogCommentId) {
		this.blogCommentId = blogCommentId;
	}
	public int getBlogId() {
		return blogId;
	}
	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBlogComment() {
		return blogComment;
	}
	public void setBlogComment(String blogComment) {
		this.blogComment = blogComment;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getBlogCommentDate() {
		return blogCommentDate;
	}
	public void setBlogCommentDate(Date blogCommentDate) {
		this.blogCommentDate = blogCommentDate;
	}

}
