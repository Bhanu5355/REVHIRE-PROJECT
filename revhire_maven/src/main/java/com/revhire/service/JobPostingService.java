package com.revhire.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.revhire.dao.JobPostingDao;
import com.revhire.entity.JobPosting;
import com.revhire.exception.ValidationException;

public class JobPostingService {

    private JobPostingDao dao = new JobPostingDao();

    // ============== EMPLOYER: POST JOB (STEP 3) ==============
    public boolean postJob(int employerId, int companyId, String title,
                           String location, String jobType,
                           double minSalary, double maxSalary,
                           Date deadline) throws ValidationException {

        if (title == null || title.trim().isEmpty())
            throw new ValidationException("Job title cannot be empty");

        if (minSalary > maxSalary)
            throw new ValidationException("Min salary cannot be greater than max salary");

        JobPosting job = new JobPosting();
        // ❌ DO NOT set jobId (handled by sequence)
        job.setEmployerId(employerId);
        job.setCompanyId(companyId);
        job.setTitle(title);
        job.setLocation(location);
        job.setJobType(jobType);
        job.setSalaryMin(minSalary);
        job.setSalaryMax(maxSalary);
        job.setDeadline(deadline);
        job.setStatus("OPEN");
        job.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return dao.addJob(job);
    }

    // ============== JOB SEEKER: VIEW JOBS (STEP 2) ==============
    public List<JobPosting> viewOpenJobs() {
        return dao.getAllOpenJobs();
    }

    // ============== JOB SEEKER: SEARCH JOBS (STEP 2) ==============
    public List<JobPosting> searchJobs(String location, String jobType) {
        return dao.searchJobs(location, jobType);
    }
}
