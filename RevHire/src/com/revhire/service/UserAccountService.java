package com.revhire.service;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.revhire.dao.UserAccountDao;
import com.revhire.entity.UserAccount;
import com.revhire.exception.InvalidCredentialsException;
import com.revhire.exception.ValidationException;

public class UserAccountService {

    private static final Logger logger =
            Logger.getLogger(UserAccountService.class);

    private UserAccountDao dao = new UserAccountDao();

    // ================= EMAIL VALIDATION =================
    private boolean isValidEmail(String email) {
        return email != null
            && email.contains("@")
            && email.contains(".")
            && email.indexOf("@") < email.lastIndexOf(".");
    }

    // ================= REGISTER =================
    public void register(String role, String name, String email,
                         String pass, String q, String a)
            throws ValidationException, Exception {

        logger.info("Register request | email=" + email);

        if (email == null || email.trim().isEmpty())
            throw new ValidationException("Email cannot be empty");

        // ✅ EMAIL FORMAT VALIDATION (FIX)
        email = email.trim();
        if (!isValidEmail(email))
            throw new ValidationException(
                "Invalid email format. Example: user@gmail.com");

        if (pass == null || pass.trim().isEmpty())
            throw new ValidationException("Password cannot be empty");

        if (dao.emailExists(email))
            throw new ValidationException("Email already registered");

        UserAccount u = new UserAccount();
        u.setRole(role.toUpperCase());
        u.setName(name);
        u.setEmail(email);
        u.setPasswordHash(pass);
        u.setSecurityQuestion(q);
        u.setSecurityAnswerHash(a);
        u.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        dao.register(u);
        logger.info("Registration successful | email=" + email);
    }

    // ================= LOGIN =================
    public UserAccount login(String email, String pass)
            throws InvalidCredentialsException, Exception {

        logger.info("Login attempt | email=" + email);

        UserAccount u = dao.login(email, pass);

        if (u == null)
            throw new InvalidCredentialsException("Invalid credentials");

        logger.info("Login success | userId=" + u.getUserId());
        return u;
    }

    // ================= FORGOT PASSWORD =================
    public String forgotPasswordGetQuestion(String email) throws Exception {

        logger.info("Forgot password | email=" + email);

        String question = dao.getSecurityQuestion(email);
        if (question == null)
            throw new ValidationException("Email not registered");

        return question;
    }

    public boolean resetPassword(String email, String answer, String newPassword)
            throws Exception {

        if (newPassword == null || newPassword.trim().isEmpty())
            throw new ValidationException("Password cannot be empty");

        boolean valid = dao.validateSecurityAnswer(email, answer);
        if (!valid)
            throw new ValidationException("Incorrect security answer");

        boolean result = dao.updatePassword(email, newPassword);
        logger.info("Password reset result=" + result);

        return result;
    }
}
