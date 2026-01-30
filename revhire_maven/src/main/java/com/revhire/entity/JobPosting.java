package com.revhire.entity;
import java.sql.Date;
import java.sql.Timestamp;

public class JobPosting {

    private int jobId;                  // PK
    private int employerId;             // FK -> EmployerProfile
    private int companyId;              // FK -> Company

    private String title;
    private String location;
    private String jobType;             // FullTime/PartTime/Internship etc.

    private double salaryMin;
    private double salaryMax;

    private Date deadline;              // ✅ java.sql.Date
    private String status;              // OPEN/CLOSED
    private Timestamp createdAt;        // ✅ java.sql.Timestamp

    public JobPosting() {}

    public JobPosting(int jobId, int employerId, int companyId, String title, String location,
                      String jobType, double salaryMin, double salaryMax,
                      Date deadline, String status, Timestamp createdAt) {

        this.jobId = jobId;
        this.employerId = employerId;
        this.companyId = companyId;
        this.title = title;
        this.location = location;
        this.jobType = jobType;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.deadline = deadline;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

    public int getEmployerId() { return employerId; }
    public void setEmployerId(int employerId) { this.employerId = employerId; }

    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getJobType() { return jobType; }
    public void setJobType(String jobType) { this.jobType = jobType; }

    public double getSalaryMin() { return salaryMin; }
    public void setSalaryMin(double salaryMin) { this.salaryMin = salaryMin; }

    public double getSalaryMax() { return salaryMax; }
    public void setSalaryMax(double salaryMax) { this.salaryMax = salaryMax; }

    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt;
    
    }

	@Override
	public String toString() {
		return "JobPosting [jobId=" + jobId + ", employerId=" + employerId
				+ ", companyId=" + companyId + ", title=" + title
				+ ", location=" + location + ", jobType=" + jobType
				+ ", salaryMin=" + salaryMin + ", salaryMax=" + salaryMax
				+ ", deadline=" + deadline + ", status=" + status
				+ ", createdAt=" + createdAt + "]";
	}
}
