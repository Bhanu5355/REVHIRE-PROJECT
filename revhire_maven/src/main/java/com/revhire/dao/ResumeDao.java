package com.revhire.dao;

import java.sql.*;
import com.revhire.entity.Resume;
import com.revhire.util.DBConnection;

public class ResumeDao {

    public boolean saveResume(Resume r) {
        String sql =
          "INSERT INTO RESUME VALUES (RESUME_SEQ.NEXTVAL,?,?,?,?,?,?,?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, r.getSeekerId());
            ps.setString(2, r.getObjective());
            ps.setString(3, r.getEducationText());
            ps.setString(4, r.getExperienceText());
            ps.setString(5, r.getSkillsText());
            ps.setString(6, r.getProjectsText());
            ps.setTimestamp(7, r.getUpdatedAt());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Resume getResumeBySeeker(int seekerId) {
        String sql = "SELECT * FROM RESUME WHERE SEEKER_ID=?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, seekerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Resume r = new Resume();
                r.setResumeId(rs.getInt("RESUME_ID"));
                r.setSeekerId(rs.getInt("SEEKER_ID"));
                r.setObjective(rs.getString("OBJECTIVE"));
                r.setEducationText(rs.getString("EDUCATION_TEXT"));
                r.setExperienceText(rs.getString("EXPERIENCE_TEXT"));
                r.setSkillsText(rs.getString("SKILLS_TEXT"));
                r.setProjectsText(rs.getString("PROJECTS_TEXT"));
                r.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));
                return r;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
