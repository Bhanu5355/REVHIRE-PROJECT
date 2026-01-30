package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.revhire.entity.JobPosting;
import com.revhire.service.JobPostingService;

public class JobPostingServiceTest {

    private JobPostingService service = new JobPostingService();

    /**
     * Test to verify that open jobs can be viewed.
     * Data already exists in DB.
     */
    @Test
    public void viewOpenJobs_shouldReturnAtLeastOneJob() {

        List<JobPosting> jobs = service.viewOpenJobs();

        assertNotNull(jobs);
        assertTrue(jobs.size() > 0);

        // validate first job details (demo-friendly)
        JobPosting job = jobs.get(0);
        assertEquals("Java Developer", job.getTitle());
        assertEquals("Hyderabad", job.getLocation());
        assertEquals("OPEN", job.getStatus());
    }

    /**
     * Test to verify job search by location and job type.
     */
    @Test
    public void searchJobs_withLocationAndJobType() {

        List<JobPosting> jobs =
                service.searchJobs("Hyderabad", "FullTime");

        assertNotNull(jobs);
        assertTrue(jobs.size() > 0);

        for (JobPosting job : jobs) {
            assertEquals("Hyderabad", job.getLocation());
            assertEquals("FullTime", job.getJobType());
        }
    }
}
