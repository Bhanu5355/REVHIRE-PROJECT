package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.revhire.entity.Application;
import com.revhire.service.ApplicationService;

public class ApplicationServiceTest {

    private ApplicationService service = new ApplicationService();

    /**
     * View applications for existing job seeker (ID = 2)
     */
    @Test
    public void viewApplications_bySeeker() {

        List<Application> applications =
                service.viewMyApplications(2);

        assertNotNull(applications);
        assertTrue(applications.size() > 0);
    }

    /**
     * View applicants for existing job (ID = 1)
     */
    @Test
    public void viewApplicants_byJob() {

        List<Application> applications =
                service.viewApplicantsByJob(1);

        assertNotNull(applications);
        assertTrue(applications.size() > 0);
    }

    /**
     * Shortlist application (APPLICATION_ID = 1)
     */
    @Test
    public void shortlistApplication_shouldPass() {

        boolean result = service.shortlist(1);
        assertTrue(result);
    }

    /**
     * Reject application (APPLICATION_ID = 1)
     */
    @Test
    public void rejectApplication_shouldPass() {

        boolean result = service.reject(1);
        assertTrue(result);
    }
}
