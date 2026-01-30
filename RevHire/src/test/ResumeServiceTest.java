package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.revhire.entity.Resume;
import com.revhire.service.ResumeService;

public class ResumeServiceTest {

    private ResumeService service = new ResumeService();


    @Test
    public void viewResume_existingData_shouldPassAlways() {

        Resume resume = service.viewResume(2);

        assertNotNull("Resume should exist for seekerId=2", resume);
        assertEquals(2, resume.getSeekerId());

        // safest assertion (guaranteed by DB)
        assertTrue(resume.getFullName().equalsIgnoreCase("Bhanu Naik"));
    }
}
