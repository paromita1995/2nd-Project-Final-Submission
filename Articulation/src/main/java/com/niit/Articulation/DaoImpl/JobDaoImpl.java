package com.niit.Articulation.DaoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Articulation.Dao.JobDao;
import com.niit.Articulation.Model.Job;
import com.niit.Articulation.Model.JobApplication;



@Repository("jobDao")
@Transactional
public class JobDaoImpl implements JobDao {
	
private SessionFactory sessionFactory;
	
	public JobDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}

	public boolean saveJob(Job job) {
		try
		{
			Session session = getSession();

			session.save(job);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean update(Job job) {
		try
		{
			Session session = getSession();

			session.update(job);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean delete(Job job) {
		try
		{
			Session session = getSession();

			session.delete(job);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public Job getJobByJobId(int id) {
		Session session = getSession();
		Query query=session.createQuery("FROM Job s where s.jobId=:jobId");
		query.setParameter("jobId", id);
		Job s=(Job)query.uniqueResult();
		session.close();
		return s;
	}

	public List<Job> list() {
		Session session = getSession();
		Query query=session.createQuery("from Job");
		List<Job> joblist=query.list();
		session.close();
		return joblist;
		
	}

	public List<Job> getMyAppliedJobs(String userId) {
		Session session=getSession();
		
		try{
			Query query=session.createQuery("from JobApplication where userId = ?");
			query.setString(0, userId);
			
			return query.list();
			
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}


	public JobApplication get(String userid, int jobid) {
		Session session = getSession();
		Query query=session.createQuery("FROM JobApplication where userId=:userId and jobId=:jobId");
		query.setParameter("userId", userid );
		query.setParameter("jobId", jobid );
		JobApplication s=(JobApplication)query.uniqueResult();
		session.close();
		return s;
	}

	public List<JobApplication> listJobApplications() {
		Session session = getSession();
		Query query=session.createQuery("from JobApplication");
		List<JobApplication> jobApplicationList=query.list();
		session.close();
		return jobApplicationList;
	}
	
	public List<Job> listVacantJobs() {
		Session session = getSession();
		Query query=session.createQuery("from Job where status=:status");
		query.setParameter("status", "Open" );
		List<Job> jobApplicationList=query.list();
		session.close();
		return jobApplicationList;
	}

	public boolean updateJobApplication(JobApplication jobApplication) {
		try
		{
			Session session = getSession();

			session.update(jobApplication);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean applyForJob(JobApplication jobApplication) {
	
	try
		{
			Session session = getSession();

			session.save(jobApplication);

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