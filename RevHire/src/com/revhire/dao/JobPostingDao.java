package com.revhire.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.revhire.entity.JobPosting;
import com.revhire.util.DBConnection;

public class JobPostingDao {

    // ================= ADD JOB (EMPLOYER – STEP 3) =================
    public boolean addJob(JobPosting job) {

        String sql =
            "INSERT INTO job_posting " +
            "(job_id, employer_id, company_id, title, location, job_type, " +
            " salary_min, salary_max, deadline, status, created_at) " +
            "VALUES (JOB_POSTING_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, job.getEmployerId());
            ps.setInt(2, job.getCompanyId());
            ps.setString(3, job.getTitle());
            ps.setString(4, job.getLocation());
            ps.setString(5, job.getJobType());
            ps.setDouble(6, job.getSalaryMin());
            ps.setDouble(7, job.getSalaryMax());
            ps.setDate(8, job.getDeadline());
            ps.setString(9, job.getStatus());
            ps.setTimestamp(10, job.getCreatedAt());

            int rows = ps.executeUpdate();

            ps.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= VIEW ALL OPEN JOBS (STEP 2) =================
    public List<JobPosting> getAllOpenJobs() {

        List<JobPosting> list = new ArrayList<JobPosting>();
        String sql = "SELECT * FROM job_posting WHERE status='OPEN'";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JobPosting job = new JobPosting();

                job.setJobId(rs.getInt("job_id"));
                job.setEmployerId(rs.getInt("employer_id"));
                job.setCompanyId(rs.getInt("company_id"));
                job.setTitle(rs.getString("title"));
                job.setLocation(rs.getString("location"));
                job.setJobType(rs.getString("job_type"));
                job.setSalaryMin(rs.getDouble("salary_min"));
                job.setSalaryMax(rs.getDouble("salary_max"));
                job.setDeadline(rs.getDate("deadline"));
                job.setStatus(rs.getString("status"));
                job.setCreatedAt(rs.getTimestamp("created_at"));

                list.add(job);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================= SEARCH JOBS (STEP 2) =================
    public List<JobPosting> searchJobs(String location, String jobType) {

        List<JobPosting> list = new ArrayList<JobPosting>();

        String sql =
            "SELECT * FROM job_posting " +
            "WHERE status='OPEN' AND location LIKE ? AND job_type LIKE ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, "%" + location + "%");
            ps.setString(2, "%" + jobType + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JobPosting job = new JobPosting();

                job.setJobId(rs.getInt("job_id"));
                job.setTitle(rs.getString("title"));
                job.setLocation(rs.getString("location"));
                job.setJobType(rs.getString("job_type"));
                job.setSalaryMin(rs.getDouble("salary_min"));
                job.setSalaryMax(rs.getDouble("salary_max"));
                job.setDeadline(rs.getDate("deadline"));

                list.add(job);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
