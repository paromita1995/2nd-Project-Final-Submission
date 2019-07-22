package com.niit.Articulation.Controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.Articulation.Dao.JobApplicationDao;
import com.niit.Articulation.Dao.JobDao;
import com.niit.Articulation.Model.Job;
import com.niit.Articulation.Model.JobApplication;
import com.niit.Articulation.Model.UserDetails;





@RestController
public class JobController {
	
	@Autowired
	JobDao jobDao;
	@Autowired
	Job job;
	@Autowired
	JobApplication jobApplication;
	@Autowired
	JobApplicationDao jobApplicationDao;
	
	
	
	


	@RequestMapping(value="/jobs", method=RequestMethod.GET)
	@ResponseBody
	
	public ResponseEntity<List<Job>> list(){
		List<Job> jobs=jobDao.list();
		if(jobs.isEmpty()){
			return new ResponseEntity<List<Job>>(jobs,HttpStatus.NO_CONTENT);
		}
		System.out.println(jobs.size());
		System.out.println("fetching job ");
		return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
	}

	

	
	
@RequestMapping(value="/job/", method=RequestMethod.POST)
	
	public ResponseEntity<Job> createJob(@RequestBody Job job,HttpSession session)
	{
		try
		{
		UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
		
		if(!(user.getRole().equals("ADMIN")))
		{
			
			job.setErrorCode("404");
			job.setErrorMessage("Job Not Created");
			return new ResponseEntity<Job>(job,HttpStatus.OK);
				
		}
		}
		catch(NullPointerException e)
		{
			job.setErrorCode("404");
			job.setErrorMessage("Admin Not logged in");
			return new ResponseEntity<Job>(job,HttpStatus.OK);
			
		}
		job.setJobDate(new Date(System.currentTimeMillis()));
		job.setStatus("Active");
		jobDao.saveJob(job);
		return new ResponseEntity<Job>(job,HttpStatus.OK);
		}
	
	
	
	
@RequestMapping(value="/job/{id}", method=RequestMethod.PUT)
public ResponseEntity<Job> updatejob(@PathVariable("id") int id, @RequestBody Job job){
	if(jobDao.list()==null){
		job =new Job();
		job.setErrorMessage("Job does not exist with id : " +job.getJobId());
		return new ResponseEntity<Job>(job, HttpStatus.NO_CONTENT);
	}
	job.setStatus("Active");
	jobDao.update(job);
	return new ResponseEntity<Job>(job, HttpStatus.OK);
}
	

	
	@RequestMapping(value="/job/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Job>deleteJob(@PathVariable("id")int id){
		Job job=jobDao.getJobByJobId(id);
		if(job == null){
			job = new Job();
			job.setErrorMessage("Job does not exist with id : " + id);
			return new ResponseEntity<Job>(job, HttpStatus.NOT_FOUND);
			
		}
		jobDao.delete(job);
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	

	
	@RequestMapping(value= "/job/{jobId}",method=RequestMethod.GET)
	public ResponseEntity<Job>getAllJobs(@PathVariable("jobId")int jobId){
		Job job = jobDao.getJobByJobId(jobId);
		if (job == null){
			job = new Job();
			job.setErrorMessage("Job does not exist with id : " + jobId);
				return new ResponseEntity<Job>(job, HttpStatus.NOT_FOUND);
				
		}
		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	
	

	
	@RequestMapping(value= "/JobByUserId/{userId}",method=RequestMethod.GET)
	public ResponseEntity<List<Job>>getAllAppliedJobs(@PathVariable("userId") String userId){
		List<Job> jobs=jobDao.getMyAppliedJobs(userId);
		if (jobs == null){
			
			jobApplication.setErrorMessage("Job Application does not exist with userId : " + userId);
				return new ResponseEntity<List<Job>>(jobs, HttpStatus.NOT_FOUND);
				
		}
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}
	

	@RequestMapping(value= "/JobByUserIdAndJobId/",method=RequestMethod.GET)
	public ResponseEntity<JobApplication> getJobByJobIdUserID(@RequestBody JobApplication jobApplication, HttpSession session){
		jobApplication = jobDao.get(jobApplication.getUserid(), jobApplication.getJobid());
		if (jobApplication == null){
			
			jobApplication.setErrorMessage("Job Application does not exist with id : " + jobApplication.getUserid() );
				
				
		}
		return new ResponseEntity<JobApplication >(jobApplication, HttpStatus.OK);
	}

	
	@RequestMapping(value="/JobByApplicationId/{jobApplicationId}", method=RequestMethod.PUT)
	public ResponseEntity<JobApplication> updateJobApplication(@PathVariable("jobApplicationId") int jobApplicationId, @RequestBody JobApplication jobApplication){
		
		
		if(jobApplication==null){
			jobApplication =new JobApplication();
			jobApplication.setErrorMessage("Job does not exist with id : " +jobApplication.getJobApplicationId());
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NO_CONTENT);
		}
		jobDao.updateJobApplication(jobApplication);
		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/NewJobApplication/", method=RequestMethod.POST)
	public ResponseEntity<JobApplication> updateJobApplicationStatus(@RequestBody JobApplication jobApplication){
		
		JobApplication s=jobDao.get(jobApplication.getUserid(), jobApplication.getJobid());
		if(s==null){
			
			jobDao.applyForJob(jobApplication);
			return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
		}
		else{
			jobApplication.setErrorMessage("Job already applied with userid : " +jobApplication.getUserid());
		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.NO_CONTENT);
		}
		
	}
	
	
	
	
	@RequestMapping(value="/JobApplications", method=RequestMethod.GET)
	
	public ResponseEntity<List<JobApplication>> listJobApplica(){
		List<JobApplication> jobApplList=jobDao.listJobApplications();
		if(jobApplList.isEmpty()){
			return new ResponseEntity<List<JobApplication>>(jobApplList,HttpStatus.NO_CONTENT);
		}
		System.out.println(jobApplList.size());
		System.out.println("retrieving jobs ");
		return new ResponseEntity<List<JobApplication>>(jobApplList,HttpStatus.OK);
	}


	
	
@RequestMapping(value="/vacantJobs", method=RequestMethod.GET)
	
	public ResponseEntity<List<Job>> listJobVacant(){
		List<Job> jobVacantList=jobDao.listVacantJobs();
		if(jobVacantList.isEmpty()){
			return new ResponseEntity<List<Job>>(jobVacantList,HttpStatus.NO_CONTENT);
		}
		System.out.println(jobVacantList.size());
		System.out.println("retrieving vacant job");
		return new ResponseEntity<List<Job>>(jobVacantList,HttpStatus.OK);
	}


@RequestMapping(value= "/appliedJobs",method=RequestMethod.GET)
public ResponseEntity<List<JobApplication>> getAppliedJobs(HttpSession session)
{
	
	try
	{
	
		UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
		return new ResponseEntity<List<JobApplication>>(jobApplicationDao.jobApplicationsByUserId(user.getUserId()),HttpStatus.OK);
	}
	catch(NullPointerException e)
	{ 
		e.printStackTrace(); 
		System.out.println("user not logged in");
		return new ResponseEntity<List<JobApplication>>(HttpStatus.NO_CONTENT);
	}
	
	
}

@RequestMapping(value= "/allApplications/{jobId}",method=RequestMethod.GET)
public ResponseEntity<List<JobApplication>> getJobApplications(@PathVariable("jobId")int jobId, HttpSession session)
{
	try
	{
		UserDetails userDetails=(UserDetails) session.getAttribute("loggedInUser");
		if((!userDetails.getRole().equals("ADMIN"))||
			(jobDao.getJobByJobId(jobId)==null)){
			return new ResponseEntity<List<JobApplication>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<JobApplication>>(jobApplicationDao.jobApplicationsByJobId(jobId),HttpStatus.OK);
	}
	catch(NullPointerException e)
	{
		e.printStackTrace();
		System.out.println("user not logged in");
		return new ResponseEntity<List<JobApplication>>(HttpStatus.NO_CONTENT);
	}
	
}
@RequestMapping(value="/rejectJobApplication/{jobApplicationId}", method=RequestMethod.PUT)
public ResponseEntity<JobApplication>rejectJobApplication(@PathVariable("jobApplicationId") int jobApplicationId,HttpSession session)
{
	try
	{
		UserDetails userDetails=(UserDetails) session.getAttribute("loggedInUser");
		JobApplication jobApplication=jobApplicationDao.getJobApplicationByJobApplicationId(jobApplicationId);
		if((userDetails.getRole().equals("ADMIN"))&&(jobApplication!=null)){
			jobApplication.setStatus("Rejected");
			jobApplicationDao.updateJobApplication(jobApplication);
			return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
		}
		else
		{
			 jobApplication=new JobApplication();
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("not found");
			return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
		}
	}
	catch(NullPointerException e)
	{
		e.printStackTrace();
		JobApplication jobApplication=new JobApplication();
		jobApplication.setErrorCode("404");
		jobApplication.setErrorMessage("admin not logged in");
		return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
	}
}

@RequestMapping(value="/approveJobApplication/{jobApplicationId}", method=RequestMethod.PUT)
public ResponseEntity<JobApplication>approveJobApplication(@PathVariable("jobApplicationId") int jobApplicationId,HttpSession session)
{
	try
	{
		UserDetails userDetails=(UserDetails) session.getAttribute("loggedInUser");
		JobApplication jobApplication=jobApplicationDao.getJobApplicationByJobApplicationId(jobApplicationId);
		if((userDetails.getRole().equals("ADMIN"))&&(jobApplication!=null))
		{
			jobApplication.setStatus("Selected");
			jobApplicationDao.updateJobApplication(jobApplication);
			return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
		}
		else
		{
			 jobApplication=new JobApplication();
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("not found");
			return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
		}
	}
	catch(NullPointerException e)
	{
		e.printStackTrace();
		JobApplication jobApplication=new JobApplication();
		jobApplication.setErrorCode("404");
		jobApplication.setErrorMessage("admin not logged in");
		return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
	}
}

@RequestMapping(value="/jobApplication/{jobId}", method=RequestMethod.POST)
public ResponseEntity<JobApplication> saveJobApplication(@PathVariable("jobId")int jobId, HttpSession session)
{
	try
	{
		System.out.println("at apply to job");
	UserDetails userDetails=(UserDetails) session.getAttribute("loggedInUser");
	System.out.println("at get userId");
	String userId=userDetails.getUserId();
		if(jobApplicationDao.isJobExist(userId, jobId))
		{
			JobApplication jobApplication=new JobApplication();
			jobApplication.setErrorCode("404");
			jobApplication.setErrorMessage("JobApplication Not Created");
			return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
		}
		
		JobApplication jobApplication=new JobApplication();
		jobApplication.setJobid(jobId);
		jobApplication.setUserid(userId);
		jobApplication.setStatus("Applied");
		jobApplicationDao.saveJobApplication(jobApplication);
		return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
	
	
	}
	catch(NullPointerException e)
	{
		e.printStackTrace();
		JobApplication jobApplication=new JobApplication();
		jobApplication.setErrorCode("404");
		jobApplication.setErrorMessage("User Not loggedin");
		return new ResponseEntity<JobApplication>(jobApplication,HttpStatus.OK);
	}
	
	
}
}