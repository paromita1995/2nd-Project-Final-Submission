package com.niit.Articulation.Model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;
@Entity
@Component
public class JobApplication extends BaseDomain implements Serializable{
	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_AUTO_JOB_APPLICATION_ID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")

    private int jobApplicationId;
	
	private String userid;
    private int Jobid;
	
	private String status;	// R = Reject, C = Call for Interview, S = Selected
	
	private String remarks;
	
	public int getJobApplicationId() {
		return jobApplicationId;
	}

	public void setJobApplicationId(int jobApplicationId) {
		this.jobApplicationId = jobApplicationId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getJobid() {
		return Jobid;
	}

	public void setJobid(int jobid) {
		Jobid = jobid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
}


