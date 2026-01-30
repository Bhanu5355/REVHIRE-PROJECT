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

    public void register(String role, String name, String email,
                         String pass, String q, String a)
            throws ValidationException, Exception {

        logger.info("Register request for email=" + email);

        if (email == null || email.trim().isEmpty()) {
            logger.error("Email empty");
            throw new ValidationException("Email cannot be empty");
        }

        if (pass == null || pass.trim().isEmpty()) {
            logger.error("Password empty");
            throw new ValidationException("Password cannot be empty");
        }

        if (dao.emailExists(email)) {
            logger.warn("Email already exists: " + email);
            throw new ValidationException("Email already registered");
        }

        UserAccount u = new UserAccount();
        u.setRole(role.toUpperCase());
        u.setName(name);
        u.setEmail(email);
        u.setPasswordHash(pass);
        u.setSecurityQuestion(q);
        u.setSecurityAnswerHash(a);
        u.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        dao.register(u);
        logger.info("Registration successful for email=" + email);
    }

    public UserAccount login(String email, String pass)
            throws InvalidCredentialsException, Exception {

        logger.info("Login attempt for email=" + email);

        UserAccount u = dao.login(email, pass);

        if (u == null) {
            logger.warn("Invalid login for email=" + email);
            throw new InvalidCredentialsException("Invalid credentials");
        }

        logger.info("Login successful for email=" + email);
        return u;
    }

    public String forgotPasswordGetQuestion(String email) throws Exception {

        logger.info("Forgot password requested for email=" + email);

        String question = dao.getSecurityQuestion(email);
        if (question == null) {
            logger.error("Email not registered: " + email);
            throw new ValidationException("Email not registered");
        }
        return question;
    }

    public boolean resetPassword(String email, String answer, String newPassword)
            throws Exception {

        logger.info("Reset password attempt for email=" + email);

        boolean valid = dao.validateSecurityAnswer(email, answer);
        if (!valid) {
            logger.error("Incorrect security answer for email=" + email);
            throw new ValidationException("Incorrect security answer");
        }

        boolean result = dao.updatePassword(email, newPassword);
        logger.info("Password reset result: " + result);

        return result;
    }
}
