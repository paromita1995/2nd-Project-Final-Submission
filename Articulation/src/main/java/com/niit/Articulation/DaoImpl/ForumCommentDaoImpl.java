package com.niit.Articulation.DaoImpl;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Articulation.Dao.ForumCommentDao;

import com.niit.Articulation.Model.ForumComment;
@Repository("forumCommentDao")
@Transactional
public class ForumCommentDaoImpl implements ForumCommentDao
{
private SessionFactory sessionFactory;
	
	public ForumCommentDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
	protected Session getSession() {
		return sessionFactory.openSession();
	}


	public boolean save(ForumComment forumComment) {
		try{
			Session session=getSession();
			session.save(forumComment);
			session.flush();
			session.close();
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean update(ForumComment forumComment) {
		try{
			Session session=getSession();
			session.update(forumComment);
			session.flush();
			session.close();
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean saveOrUpdate(ForumComment forumComment) {
		try{
			Session session=getSession();
			session.saveOrUpdate(forumComment);
			session.flush();
			session.close();
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean delete(ForumComment forumComment) {
		try{
			Session session=getSession();
			session.delete(forumComment);
			session.flush();
			session.close();
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public ForumComment getByForumCommentId(int id) {
		Session session = getSession();
		Query query=session.createQuery("FROM ForumComment s where forumCommentId=:forumCommentId");
		query.setParameter("forumCommentId", id);
		ForumComment  forumComment =(ForumComment)query.uniqueResult();
		session.close();
		return forumComment;
	}

	public List<ForumComment> listByUserId(String id) {
		Session session=getSession();
		Query query=session.createQuery("FROM ForumComment  where userId=:userId");
		query.setParameter("userId", id);
		List<ForumComment> forumCommentList=query.list();
		session.close();
		return forumCommentList;
	}

	public List<ForumComment> listByForumId(int id) {
		Session session = getSession();
		Query query=session.createQuery("FROM ForumComment  where forumId=:forumId");
		query.setParameter("forumId", id);
		List<ForumComment> forumCommentList=query.list();
		session.close();
		return forumCommentList;
	}

	

	public List<ForumComment> getAllForumComments() {
		Session session = getSession();
		Query query=session.createQuery("from ForumComment");
		List<ForumComment> forumCommentlist=query.list();
		
		return forumCommentlist;
	}

}