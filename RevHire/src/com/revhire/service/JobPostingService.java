package com.revhire.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import com.revhire.dao.JobPostingDao;
import com.revhire.entity.JobPosting;
import com.revhire.exception.ValidationException;

public class JobPostingService {

    private static final Logger logger =
            Logger.getLogger(JobPostingService.class);

    private JobPostingDao dao = new JobPostingDao();

    public boolean postJob(int employerId, int companyId, String title,
                           String location, String jobType,
                           double minSalary, double maxSalary,
                           Date deadline) throws ValidationException {

        logger.info("Post job request by employerId=" + employerId);

        if (title == null || title.trim().isEmpty())
            throw new ValidationException("Job title cannot be empty");

        if (location == null || location.trim().isEmpty())
            throw new ValidationException("Location cannot be empty");

        if (minSalary > maxSalary)
            throw new ValidationException("Invalid salary range");

        JobPosting job = new JobPosting();
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

        boolean result = dao.addJob(job);
        logger.info("Job posted result=" + result);

        return result;
    }

    public List<JobPosting> viewOpenJobs() {
        logger.info("Fetching open jobs");
        return dao.getAllOpenJobs();
    }

    public List<JobPosting> searchJobs(String location, String jobType) {
        logger.info("Searching jobs | location=" + location + ", type=" + jobType);
        return dao.searchJobs(location, jobType);
    }
}
