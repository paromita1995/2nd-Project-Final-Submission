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
public class Blog extends BaseDomain implements Serializable {
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_AUTO_BLOGID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	private int blogId;
	private String blogContent;
	private String blogTitle;
	private Date blogDate;
	private String userId;
	private String blogStatus;
	private int blogCountLike;
	private int blogCommentCount;
	public int getBlogId() {
		return blogId;
	}
	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}
	public String getBlogContent() {
		return blogContent;
	}
	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}
	public String getBlogTitle() {
		return blogTitle;
	}
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
	public Date getBlogDate() {
		return blogDate;
	}
	public void setBlogDate(Date blogDate) {
		this.blogDate = blogDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBlogStatus() {
		return blogStatus;
	}
	public void setBlogStatus(String blogStatus) {
		this.blogStatus = blogStatus;
	}
	public int getBlogCountLike() {
		return blogCountLike;
	}
	public void setBlogCountLike(int blogCountLike) {
		this.blogCountLike = blogCountLike;
	}
	public int getBlogCommentCount() {
		return blogCommentCount;
	}
	public void setBlogCommentCount(int blogCommentCount) {
		this.blogCommentCount = blogCommentCount;
	}
	
	}
