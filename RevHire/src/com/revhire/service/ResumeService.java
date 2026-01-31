package com.revhire.service;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.revhire.dao.ResumeDao;
import com.revhire.entity.Resume;

public class ResumeService {

    private static final Logger logger =
            Logger.getLogger(ResumeService.class);

    private ResumeDao dao = new ResumeDao();

    public boolean createResume(int seekerId, String fullName, String obj,
                                String edu, String exp, String skills, String proj) {

        logger.info("Create/Update resume for seekerId=" + seekerId);

        Resume r = new Resume();
        r.setSeekerId(seekerId);
        r.setFullName(fullName);
        r.setObjective(obj);
        r.setEducationText(edu);
        r.setExperienceText(exp);
        r.setSkillsText(skills);
        r.setProjectsText(proj);
        r.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        return dao.saveOrUpdateResume(r);
    }

    public Resume viewResume(int seekerId) {
        logger.info("Viewing resume for seekerId=" + seekerId);
        return dao.getResumeBySeeker(seekerId);
    }
}
