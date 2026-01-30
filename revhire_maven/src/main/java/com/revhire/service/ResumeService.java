package com.revhire.service;

import java.sql.Timestamp;
import com.revhire.dao.ResumeDao;
import com.revhire.entity.Resume;

public class ResumeService {

    private ResumeDao dao = new ResumeDao();

    public boolean createResume(int seekerId,String fullName, String obj,
        String edu, String exp, String skills, String proj) {

        Resume r = new Resume();
        r.setSeekerId(seekerId);
        r.setObjective(obj);
        r.setEducationText(edu);
        r.setExperienceText(exp);
        r.setSkillsText(skills);
        r.setProjectsText(proj);
        r.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        return dao.saveResume(r);
    }

    public Resume viewResume(int seekerId) {
        return dao.getResumeBySeeker(seekerId);
    }
}
