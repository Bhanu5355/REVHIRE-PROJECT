package com.revhire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revhire.entity.Application;
import com.revhire.util.DBConnection;

public class ApplicationDao {

    // ================= APPLY JOB =================
    public boolean applyJob(Application app) {

        String sql =
            "INSERT INTO APPLICATION " +
            "(APPLICATION_ID, JOB_ID, SEEKER_ID, RESUME_ID, COVER_LETTER, STATUS, APPLIED_AT) " +
            "VALUES (APPLICATION_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, app.getJobId());
            ps.setInt(2, app.getSeekerId());
            ps.setInt(3, app.getResumeId());
            ps.setString(4, app.getCoverLetter());
            ps.setString(5, app.getStatus());
            ps.setTimestamp(6, app.getAppliedAt());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // ================= VIEW APPLICATIONS BY SEEKER =================
    public List<Application> getApplicationsBySeeker(int seekerId) {

        List<Application> list = new ArrayList<Application>();

        String sql =
            "SELECT APPLICATION_ID, JOB_ID, STATUS, APPLIED_AT " +
            "FROM APPLICATION WHERE SEEKER_ID=?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, seekerId);

            rs = ps.executeQuery();

            while (rs.next()) {
                Application app = new Application();
                app.setApplicationId(rs.getInt("APPLICATION_ID"));
                app.setJobId(rs.getInt("JOB_ID"));
                app.setStatus(rs.getString("STATUS"));
                app.setAppliedAt(rs.getTimestamp("APPLIED_AT"));
                list.add(app);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // ================= VIEW APPLICATIONS BY JOB (EMPLOYER) =================
    public List<Application> getApplicationsByJob(int jobId) {

        List<Application> list = new ArrayList<Application>();
        String sql = "SELECT * FROM APPLICATION WHERE JOB_ID=?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, jobId);

            rs = ps.executeQuery();

            while (rs.next()) {
                Application a = new Application();
                a.setApplicationId(rs.getInt("APPLICATION_ID"));
                a.setJobId(rs.getInt("JOB_ID"));
                a.setSeekerId(rs.getInt("SEEKER_ID"));
                a.setStatus(rs.getString("STATUS"));
                a.setAppliedAt(rs.getTimestamp("APPLIED_AT"));
                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // ================= UPDATE STATUS (SHORTLIST / REJECT) =================
    public boolean updateStatus(int applicationId, String status) {

        String sql = "UPDATE APPLICATION SET STATUS=? WHERE APPLICATION_ID=?";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, applicationId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
 // ================= WITHDRAW APPLICATION =================
    public boolean withdrawApplication(int applicationId) {

        String sql =
            "UPDATE APPLICATION SET STATUS='WITHDRAWN' " +
            "WHERE APPLICATION_ID=?";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, applicationId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    
}
