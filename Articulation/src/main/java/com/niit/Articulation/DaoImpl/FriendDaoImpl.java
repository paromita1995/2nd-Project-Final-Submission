package com.niit.Articulation.DaoImpl;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Articulation.Dao.FriendDao;
import com.niit.Articulation.Model.Friend;
@Transactional
@EnableTransactionManagement
@Repository(value="friendDao")
@SuppressWarnings("unchecked")
public class FriendDaoImpl implements FriendDao{

	@Autowired	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	protected Session getSession() {
		return sessionFactory.openSession();
	}

	
	public FriendDaoImpl() { 		
		
	}	
	public FriendDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	public boolean save(Friend friend) {
		try {
			Session session=getSession();
			session.save(friend);
			session.flush();
			session.close();
			return true;
		} 
		catch (HibernateException e) {
			
			return false;
		}
	}
	
	
	public boolean update(Friend friend) {
		try {
			Session session=getSession();
			session.update(friend);
			session.flush();
			session.close();
			return true;
		}
		catch (HibernateException e) {
			
			return false;
		}
	}
	
	//Two user are  friends  [if]  
	public Friend get(String userId, String friendId) {
		
		Session session=getSession();
		Query query = session.createQuery("from Friend where ( userId = '" + friendId + "' and friendId = '" + userId + "'  and status = 'A' ) or " + " ( userId = '" + userId + "' and friendId = '" + friendId + "'  and status = 'A' ) ");
		List<Friend> list = (List<Friend>) query.list();
		session.close();
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	
	
	
	public List<Friend> getMyFriends(String userId) {
		
		Session session=getSession();
		Query query = session.createQuery("from Friend where ( userId = '" + userId + "' and status = 'A' ) or ( friendId = '" + userId + "' and status = 'A' )");
		List<Friend> list = (List<Friend>) query.list();
		session.close();
		return list;
	}
	
	
			
			
		
	public List<Friend> getNewFriendRequests(String friendId){
			Session session=getSession();
			Query query = session.createQuery("from Friend where friendId = '" + friendId + "' and status = 'N'");
			List<Friend> list = (List<Friend>) query.list();
			session.close();
			return list;
		}
		
		
		
	public void setOnline(String userId) {
		
		Session session=getSession();
		Query query = session.createQuery("update Friend set isOnline = 'Y' where userId = '" + userId + "'");
		query.executeUpdate();
		session.close();
	}
	
	

       
      public void setOffline(String userId) {
		// TODO Auto-generated method stub
		String hql = "update Friend set isOnline = 'N' where userId = '" + userId + "'";
		Session session=getSession();
		Query query = session.createQuery(hql);
		query.executeUpdate();
		session.close();
	
	}
	public Friend getRequest(String userId, String friendId) {
		// TODO Auto-generated method stub
		   
		Session session=getSession();
		Query query = session.createQuery("from Friend where  userId = '" + friendId + "' and friendId = '" + userId + "'  and status = 'N' ");
		List<Friend> list = (List<Friend>) query.list();
		session.close();
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	
	public boolean isFriendtrue(String userId, String friendId) {
		
		
		Session session=getSession();
		Query query = session.createQuery("from Friend where ( userId = '" + friendId + "' and friendId = '" + userId + "'  and status = 'A' ) or " + " ( userId = '" + userId + "' and friendId = '" + friendId + "'  and status = 'A' )");
	
		List<Friend> list = (List<Friend>) query.list();
		session.close();
		if(list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}

	public List<Friend> getAllFriends(){
		Session session = getSession();
		Query query=session.createQuery("from Friend");
		List<Friend> friendList=query.list();
		session.close();
		return friendList;
	
	}
	public boolean unFriend(Friend friend)
	{
		try
		{
			Session session = getSession();
			friend.setStatus("Unfriend");
	        session.update(friend);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}
}