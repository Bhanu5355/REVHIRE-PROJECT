package com.revhire.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import com.revhire.dao.ApplicationDao;
import com.revhire.entity.Application;
import com.revhire.exception.ValidationException;

public class ApplicationService {

    private static final Logger logger =
            Logger.getLogger(ApplicationService.class);

    private ApplicationDao dao = new ApplicationDao();

    // ================= APPLY JOB =================
    public boolean apply(int jobId, int seekerId, int resumeId, String coverLetter)
            throws ValidationException {

        logger.info("Apply job requested | jobId=" + jobId + ", seekerId=" + seekerId);

        if (jobId <= 0 || seekerId <= 0) {
            logger.error("Invalid job or seeker");
            throw new ValidationException("Invalid job or seeker");
        }

        Application app = new Application();
        app.setJobId(jobId);
        app.setSeekerId(seekerId);
        app.setResumeId(resumeId);
        app.setCoverLetter(coverLetter);
        app.setStatus("APPLIED");
        app.setAppliedAt(new Timestamp(System.currentTimeMillis()));

        boolean result = dao.applyJob(app);
        logger.info("Apply job result: " + result);

        return result;
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

    public boolean shortlist(int applicationId) {
        logger.info("Shortlisting applicationId=" + applicationId);
        return dao.updateStatus(applicationId, "SHORTLISTED");
    }

    public boolean reject(int applicationId) {
        logger.info("Rejecting applicationId=" + applicationId);
        return dao.updateStatus(applicationId, "REJECTED");
    }

    // ================= WITHDRAW =================
    public boolean withdraw(int applicationId) {
        logger.info("Withdrawing applicationId=" + applicationId);
        return dao.withdrawApplication(applicationId);
    }
}
