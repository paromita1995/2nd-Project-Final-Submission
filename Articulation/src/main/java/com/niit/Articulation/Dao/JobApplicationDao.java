package com.niit.Articulation.Dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.Articulation.Model.JobApplication;

@Repository
public interface JobApplicationDao {
	
	public boolean saveJobApplication(JobApplication jobApplication);
	public boolean updateJobApplication(JobApplication jobApplication);
	public JobApplication getJobApplicationByJobApplicationId(int jobApplicationId);
	public List<JobApplication> jobApplicationsByJobId(int jobId);
	public List<JobApplication> listJobApplications();
	public List<JobApplication> jobApplicationsByUserId(String userId);
	public boolean isJobExist(String userId, int jobId);

}
