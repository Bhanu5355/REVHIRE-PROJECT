package com.revhire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revhire.entity.Resume;
import com.revhire.util.DBConnection;

public class ResumeDao {

    // ---------------- CHECK IF RESUME EXISTS ----------------
    public boolean resumeExists(int seekerId) {

        String sql = "SELECT 1 FROM RESUME WHERE SEEKER_ID = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, seekerId);
            rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(rs, ps, con);
        }
    }

    // ---------------- INSERT RESUME ----------------
    private boolean insertResume(Resume r) {

        String sql =
            "INSERT INTO RESUME " +
            "(RESUME_ID, SEEKER_ID, OBJECTIVE, EDUCATION_TEXT, EXPERIENCE_TEXT, " +
            "SKILLS_TEXT, PROJECTS_TEXT, UPDATED_AT, FULL_NAME) " +
            "VALUES (RESUME_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, r.getSeekerId());
            ps.setString(2, r.getObjective());
            ps.setString(3, r.getEducationText());
            ps.setString(4, r.getExperienceText());
            ps.setString(5, r.getSkillsText());
            ps.setString(6, r.getProjectsText());
            ps.setTimestamp(7, r.getUpdatedAt());
            ps.setString(8, r.getFullName());

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(null, ps, con);
        }
    }

    // ---------------- UPDATE RESUME ----------------
    private boolean updateResume(Resume r) {

        String sql =
            "UPDATE RESUME SET " +
            "FULL_NAME=?, OBJECTIVE=?, EDUCATION_TEXT=?, EXPERIENCE_TEXT=?, " +
            "SKILLS_TEXT=?, PROJECTS_TEXT=?, UPDATED_AT=? " +
            "WHERE SEEKER_ID=?";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, r.getFullName());
            ps.setString(2, r.getObjective());
            ps.setString(3, r.getEducationText());
            ps.setString(4, r.getExperienceText());
            ps.setString(5, r.getSkillsText());
            ps.setString(6, r.getProjectsText());
            ps.setTimestamp(7, r.getUpdatedAt());
            ps.setInt(8, r.getSeekerId());

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(null, ps, con);
        }
    }

    // ---------------- UPSERT ----------------
    public boolean saveOrUpdateResume(Resume r) {

        if (resumeExists(r.getSeekerId())) {
            return updateResume(r);
        } else {
            return insertResume(r);
        }
    }

    // ---------------- FETCH RESUME ----------------
    public Resume getResumeBySeeker(int seekerId) {

        String sql =
            "SELECT * FROM RESUME WHERE SEEKER_ID=? ORDER BY UPDATED_AT DESC";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, seekerId);
            rs = ps.executeQuery();

            if (rs.next()) {
                Resume r = new Resume();
                r.setResumeId(rs.getInt("RESUME_ID"));
                r.setSeekerId(rs.getInt("SEEKER_ID"));
                r.setFullName(rs.getString("FULL_NAME"));
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
        } finally {
            close(rs, ps, con);
        }
        return null;
    }

    // ---------------- CLOSE UTILITY ----------------
    private void close(ResultSet rs, PreparedStatement ps, Connection con) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }
}
