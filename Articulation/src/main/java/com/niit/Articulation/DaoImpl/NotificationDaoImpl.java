package com.niit.Articulation.DaoImpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Articulation.Dao.NotificationDao;
import com.niit.Articulation.Model.Notification;

@Repository("notificationDao")
@Transactional
public class NotificationDaoImpl implements NotificationDao  {
	
	@Autowired
	
	private SessionFactory sessionFactory;
	
	public NotificationDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}
	
		public void addNotification(Notification notification) {
			Session session=sessionFactory.getCurrentSession();
			session.save(notification);
			
		}

		public List<Notification> getAllNotificationsNotViewed(String userId) {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery(
					"from Notification where viewed=:viewed and userId=:userId");
			query.setString("viewed", "False");
			query.setString("userId", userId);
			List<Notification> notificationsNotViewed=query.list();
			return notificationsNotViewed;
		}

		public Notification getNotification(int notificationId) {
			Session session=sessionFactory.getCurrentSession();
			Notification notification=(Notification)session.get(Notification.class, notificationId);
			return notification;
		}

		public void updateNotificactionViewedStatus(int notificationId) {
			Session session=sessionFactory.getCurrentSession();
			Notification notification=(Notification)session.get(Notification.class, notificationId);
	        notification.setViewed("True");
	        session.update(notification);
		}


}
