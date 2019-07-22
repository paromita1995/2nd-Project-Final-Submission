package com.niit.Articulation.DaoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Articulation.Dao.BlogCommentDao;
import com.niit.Articulation.Model.BlogComment;

@Repository("blogCommentDao")
@Transactional
public class BlogCommentDaoImpl implements BlogCommentDao{
private SessionFactory sessionFactory;
	
	public BlogCommentDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}

	public List<BlogComment> getAllBlogComments() {
		Session session = getSession();
		Query query=session.createQuery("from BlogComment");
		List<BlogComment> blogCommentlist=query.list();
		
		return blogCommentlist;
	}
	

	public boolean save(BlogComment blogComment) {
		try{
			Session session=getSession();
			session.save(blogComment);
			session.flush();
			session.close();
			return true;
		}
		catch(HibernateException e){
		return false;
		}
	}

	public boolean update(BlogComment blogComment) {
		try
		{
			Session session = getSession();

			session.update(blogComment);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean saveOrUpdate(BlogComment blogComment) {
		try
		{
			Session session = getSession();

			session.saveOrUpdate(blogComment);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean delete(BlogComment blogComment) {
		try
		{
			Session session = getSession();

			session.delete(blogComment);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public BlogComment getByBlogCommentId(int id) {
		Session session = getSession();
		Query query=session.createQuery("FROM BlogComment s where blogCommentId=:blogCommentId");
		query.setParameter("blogCommentId", id);
		BlogComment  blogComment =(BlogComment)query.uniqueResult();
		session.close();
		return blogComment;
	}

	public List<BlogComment> listByBlogId(int id) {
		Session session = getSession();
		Query query=session.createQuery("FROM BlogComment  where blogId=:blogId");
		query.setParameter("blogId", id);
		List<BlogComment> blogCommentList=query.list();
		session.close();
		return blogCommentList;
	}

	public List<BlogComment> listByUserId(String id) {
		Session session = getSession();
		Query query=session.createQuery("FROM BlogComment  where userId=:userId");
		query.setParameter("userId", id);
		List<BlogComment> blogCommentList=query.list();
		session.close();
		return blogCommentList;
	}


}
