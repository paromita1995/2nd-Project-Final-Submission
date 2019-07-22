package com.niit.Articulation.DaoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Articulation.Dao.ForumDao;
import com.niit.Articulation.Model.Forum;

@Repository("forumDao")
@Transactional
public class ForumDaoImpl  implements ForumDao {
private SessionFactory sessionFactory;
	
	public ForumDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}

	public boolean saveForum(Forum forum) {
		try
		{
			Session session = getSession();

			session.save(forum);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean deleteForum(Forum forum) {
		try
		{
			Session session = getSession();

			session.delete(forum);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean updateForum(Forum forum) {
		try
		{
			Session session = getSession();

			session.update(forum);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public Forum getForumByForumId(int forumId) {
		Session session = getSession();
		Query query=session.createQuery("FROM Forum p where forumId=:forumId");
		query.setParameter("forumId", forumId);
		Forum  p=(Forum)query.uniqueResult();
		session.close();
		return p;
	}

	public List<Forum> getAllForums() {
		Session session = getSession();
		Query query=session.createQuery("from Forum");
		List<Forum> forumlist=query.list();
		session.close();
		return forumlist;
	}

	public List<Forum> getAllApprovedForums() {
		Session session = getSession();
		Query query=session.createQuery("FROM Forum p where forumStatus=:forumStatus");
		query.setParameter("forumStatus", "Approved");
		List<Forum> approvedForumList=query.list();
		session.close();
		return approvedForumList;
	}

	public List<Forum> getAllRejectedForums() {
		Session session=getSession();
		Query query=session.createQuery("FROM Forum p where forumStatus=:forumStatus");
		query.setParameter("forumStatus", "Pending");
		List<Forum> rejectedForumList=query.list();
		session.close();
		return rejectedForumList;
	}

	public List<Forum> getMyForums(String userid) {
		Session session=getSession();
		try{
			Query query=session.createQuery("from Forum where userId = ?");
			query.setString(0, userid);
			
			return query.list();
			
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
			}


}
