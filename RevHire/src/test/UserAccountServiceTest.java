package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.revhire.entity.UserAccount;
import com.revhire.exception.InvalidCredentialsException;
import com.revhire.exception.ValidationException;
import com.revhire.service.UserAccountService;

public class UserAccountServiceTest {

    private UserAccountService service = new UserAccountService();

    // ================= REGISTER VALIDATION TESTS =================

    @Test(expected = ValidationException.class)
    public void register_shouldFail_whenEmailIsEmpty() throws Exception {
        service.register(
            "JOB_SEEKER",
            "Test User",
            "",
            "password123",
            "fav color",
            "blue"
        );
    }

    @Test(expected = ValidationException.class)
    public void register_shouldFail_whenPasswordIsEmpty() throws Exception {
        service.register(
            "JOB_SEEKER",
            "Test User",
            "dummy@gmail.com",
            "",
            "fav color",
            "blue"
        );
    }

    // ================= LOGIN TESTS (USING EXISTING DB DATA) =================

    // ✅ Job Seeker Login
    @Test
    public void login_jobSeeker_shouldPass() throws Exception {

        UserAccount user =
            service.login("bhukyabhanu1234@gmail.com", "bhanu@5355");

        assertNotNull(user);
        assertEquals(2, user.getUserId());
        assertEquals("JOB_SEEKER", user.getRole().toUpperCase());
    }

    // ✅ Employer Login
    @Test
    public void login_employer_shouldPass() throws Exception {

        UserAccount user =
            service.login("bhukya1234@gmail.com", "bhanu@6658");

        assertNotNull(user);
        assertEquals(3, user.getUserId());
        assertEquals("EMPLOYER", user.getRole().toUpperCase());
    }

    // ❌ Wrong Password
    @Test(expected = InvalidCredentialsException.class)
    public void login_shouldFail_withWrongPassword() throws Exception {
        service.login("bhukyabhanu1234@gmail.com", "wrongpass");
    }
}
