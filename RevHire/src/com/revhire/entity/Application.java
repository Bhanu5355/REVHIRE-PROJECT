package com.revhire.entity;
import java.sql.Timestamp;

public class Application {

    private int applicationId;      
    private int jobId;              
    private int seekerId;           
    private int resumeId;          
    private String coverLetter;
    private String status;          
    private Timestamp appliedAt;   
    public Application() {}

    public Application(int applicationId, int jobId, int seekerId, int resumeId,
                       String coverLetter, String status, Timestamp appliedAt) {
        this.applicationId = applicationId;
        this.jobId = jobId;
        this.seekerId = seekerId;
        this.resumeId = resumeId;
        this.coverLetter = coverLetter;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public int getApplicationId() { return applicationId; }
    public void setApplicationId(int applicationId) { this.applicationId = applicationId; }

    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

    public int getSeekerId() { return seekerId; }
    public void setSeekerId(int seekerId) { this.seekerId = seekerId; }

    public int getResumeId() { return resumeId; }
    public void setResumeId(int resumeId) { this.resumeId = resumeId; }

    public String getCoverLetter() { return coverLetter; }
    public void setCoverLetter(String coverLetter) { this.coverLetter = coverLetter; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getAppliedAt() { return appliedAt; }
    public void setAppliedAt(Timestamp appliedAt) { this.appliedAt = appliedAt; 
    
    }

	@Override
	public String toString() {
		return "Application [applicationId=" + applicationId + ", jobId="
				+ jobId + ", seekerId=" + seekerId + ", resumeId=" + resumeId
				+ ", coverLetter=" + coverLetter + ", status=" + status
				+ ", appliedAt=" + appliedAt + "]";
	}
}
