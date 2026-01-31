package com.revhire.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import com.revhire.dao.ApplicationDao;
import com.revhire.entity.Application;
import com.revhire.exception.ValidationException;
import com.revhire.dao.JobPostingDao;


public class ApplicationService {

    private static final Logger logger =
            Logger.getLogger(ApplicationService.class);

    private ApplicationDao dao = new ApplicationDao();

    // ================= APPLY JOB =================
    public boolean apply(int jobId, int seekerId, int resumeId, String coverLetter)
            throws ValidationException {

        logger.info("Apply job requested | jobId=" + jobId + ", seekerId=" + seekerId);

        if (jobId <= 0)
            throw new ValidationException("Invalid job ID");

        if (seekerId <= 0)
            throw new ValidationException("Invalid seeker ID");
        
        // ✅ NEW VALIDATION: check job exists
        JobPostingDao jobDao = new JobPostingDao();
        if (!jobDao.jobExistsAndOpen(jobId)) {
            logger.error("Invalid or closed job | jobId=" + jobId);
            throw new ValidationException(
                "Invalid Job ID or Job is not open for applications");
        }


        Application app = new Application();
        app.setJobId(jobId);
        app.setSeekerId(seekerId);
        app.setResumeId(resumeId);
        app.setCoverLetter(coverLetter);
        app.setStatus("APPLIED");
        app.setAppliedAt(new Timestamp(System.currentTimeMillis()));

        boolean result = dao.applyJob(app);

        if (!result) {
            logger.error("Apply failed | Invalid or closed jobId=" + jobId);
            throw new ValidationException(
                "Invalid Job ID or job is not open for applications");
        }

        return true;

    }

    // ================= JOB SEEKER =================
    public List<Application> viewMyApplications(int seekerId) {
        logger.info("Fetching applications for seekerId=" + seekerId);
        return dao.getApplicationsBySeeker(seekerId);
    }

    // ================= EMPLOYER =================
    public List<Application> viewApplicantsByJob(int jobId) {
        logger.info("Fetching applicants for jobId=" + jobId);
        return dao.getApplicationsByJob(jobId);
    }

    public boolean shortlist(int applicationId) throws ValidationException {

        logger.info("Shortlisting applicationId=" + applicationId);

        String status = dao.getApplicationStatus(applicationId);

        if (status == null) {
            return false;
        }

        if ("REJECTED".equalsIgnoreCase(status)) {
            throw new ValidationException("Application is rejected. Reopen first.");
        }

        if ("WITHDRAWN".equalsIgnoreCase(status)) {
            throw new ValidationException("Application is withdrawn. Reopen first.");
        }

        if ("SHORTLISTED".equalsIgnoreCase(status)) {
            throw new ValidationException("Application already shortlisted");
        }

        // APPLIED or REOPENED allowed
        return dao.updateStatus(applicationId, "SHORTLISTED");
    }



    public boolean reject(int applicationId) throws ValidationException {

        logger.info("Rejecting applicationId=" + applicationId);

        String currentStatus = dao.getApplicationStatus(applicationId);

        if (currentStatus == null) {
            return false;
        }

        if ("REJECTED".equalsIgnoreCase(currentStatus)) {
            throw new ValidationException("Application already rejected");
        }

        if ("WITHDRAWN".equalsIgnoreCase(currentStatus)) {
            throw new ValidationException("Application already withdrawn");
        }

        return dao.updateStatus(applicationId, "REJECTED");
    }

    
    public boolean reopen(int applicationId) throws ValidationException {

        logger.info("Reopening applicationId=" + applicationId);

        String status = dao.getApplicationStatus(applicationId);

        if (status == null) {
            return false; // application not found
        }

        if ("REOPENED".equalsIgnoreCase(status)) {
            throw new ValidationException("Application already reopened");
        }

        if ("APPLIED".equalsIgnoreCase(status)) {
            throw new ValidationException("Application already active");
        }

        if ("SHORTLISTED".equalsIgnoreCase(status)) {
            throw new ValidationException("Application already shortlisted");
        }

        // ONLY allowed cases reach here
        // REJECTED or WITHDRAWN
        return dao.reopenApplication(applicationId);
    }






    public boolean withdraw(int applicationId) throws ValidationException {

        logger.info("Withdrawing applicationId=" + applicationId);

        String currentStatus = dao.getApplicationStatus(applicationId);

        if (currentStatus == null) {
            return false; // application not found
        }

        if ("WITHDRAWN".equalsIgnoreCase(currentStatus)) {
            throw new ValidationException("Application already withdrawn");
        }

        if ("REJECTED".equalsIgnoreCase(currentStatus)) {
            throw new ValidationException("Application already rejected");
        }

        if ("SHORTLISTED".equalsIgnoreCase(currentStatus)) {
            throw new ValidationException("Application already shortlisted");
        }

        return dao.withdrawApplication(applicationId);
    }

}
