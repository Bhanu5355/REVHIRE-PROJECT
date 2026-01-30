package com.revhire.service;

import java.sql.Timestamp;
import java.util.List;

import com.revhire.dao.ApplicationDao;
import com.revhire.entity.Application;
import com.revhire.exception.ValidationException;

public class ApplicationService {

    private ApplicationDao dao = new ApplicationDao();

    // ================= APPLY JOB =================
    public boolean apply(int jobId, int seekerId, int resumeId, String coverLetter)
            throws ValidationException {

        if (jobId <= 0 || seekerId <= 0) {
            throw new ValidationException("Invalid job or seeker");
        }

        Application app = new Application();
        app.setJobId(jobId);
        app.setSeekerId(seekerId);
        app.setResumeId(resumeId);
        app.setCoverLetter(coverLetter);
        app.setStatus("APPLIED");
        app.setAppliedAt(new Timestamp(System.currentTimeMillis()));

        return dao.applyJob(app);
    }

    // ================= JOB SEEKER =================
    public List<Application> viewMyApplications(int seekerId) {
        return dao.getApplicationsBySeeker(seekerId);
    }

    // ================= EMPLOYER =================
    public List<Application> viewApplicantsByJob(int jobId) {
        return dao.getApplicationsByJob(jobId);
    }

    public boolean shortlist(int applicationId) {
        return dao.updateStatus(applicationId, "SHORTLISTED");
    }

    public boolean reject(int applicationId) {
        return dao.updateStatus(applicationId, "REJECTED");
    }
    
 // ================= WITHDRAW =================
    public boolean withdraw(int applicationId) {
        return dao.withdrawApplication(applicationId);
    }

}
