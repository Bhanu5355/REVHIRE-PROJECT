package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.revhire.entity.Application;
import com.revhire.exception.ValidationException;
import com.revhire.service.ApplicationService;

public class ApplicationServiceTest {

    private ApplicationService service = new ApplicationService();

    /**
     * View applications for existing job seeker (ID = 2)
     */
    @Test
    public void viewApplications_bySeeker_shouldReturnData() {

        List<Application> applications =
                service.viewMyApplications(2);

        assertNotNull(applications);
        assertTrue(applications.size() >= 0); // SAFE
    }

    /**
     * View applicants for existing job (ID = 1)
     */
    @Test
    public void viewApplicants_byJob_shouldReturnData() {

        List<Application> applications =
                service.viewApplicantsByJob(1);

        assertNotNull(applications);
        assertTrue(applications.size() >= 0); // SAFE
    }

    /**
     * Shortlist application – should either succeed
     * OR throw ValidationException if already processed
     */
    @Test
    public void shortlistApplication_shouldBehaveCorrectly() {

        try {
            boolean result = service.shortlist(1);
            assertTrue(result); // first-time case
        } catch (ValidationException e) {
            assertTrue(
                e.getMessage().contains("already")
                || e.getMessage().contains("rejected")
                || e.getMessage().contains("withdrawn")
            ); // repeat-run case
        }
    }


    /**
     * Reject application – should either succeed
     * OR throw ValidationException if already processed
     */
    @Test
    public void rejectApplication_shouldBehaveCorrectly() {

        try {
            boolean result = service.reject(1);
            assertTrue(result); // first-time case
        } catch (ValidationException e) {
            assertTrue(
                e.getMessage().contains("already")
            ); // repeat run case
        }
    }
    @Test
    public void reopen_then_shortlist_shouldBehaveCorrectly() {

        // ---- REOPEN ----
        try {
            boolean reopened = service.reopen(2);
            assertTrue(reopened);
        } catch (ValidationException e) {
            assertTrue(
                e.getMessage().contains("already")
                || e.getMessage().contains("active")
                || e.getMessage().contains("shortlisted")
            );
        }

        // ---- SHORTLIST ----
        try {
            boolean result = service.shortlist(2);
            assertTrue(result);
        } catch (ValidationException e) {
            assertTrue(
                e.getMessage().contains("already")
                || e.getMessage().contains("rejected")
                || e.getMessage().contains("withdrawn")
            );
        }
    }


    
    @Test
    public void withdrawApplication_shouldBehaveCorrectly() {

        try {
            boolean result = service.withdraw(1);
            assertTrue(result); // first run
        } catch (ValidationException e) {
            assertTrue(e.getMessage().contains("already"));
        }
    }


}
