package com.revhire.service;

import java.sql.Timestamp;
import com.revhire.dao.UserAccountDao;
import com.revhire.entity.UserAccount;
import com.revhire.exception.*;

public class UserAccountService {

    private UserAccountDao dao = new UserAccountDao();

    public void register(String role, String name, String email, String pass,
                         String q, String a) throws Exception {

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
    }

    public UserAccount login(String email, String pass) throws Exception {
        UserAccount u = dao.login(email, pass);
        if (u == null)
            throw new InvalidCredentialsException("Invalid credentials");
        return u;
    }

    public void updateResume(int userId, String resume) throws Exception {
        dao.saveResume(userId, resume);
    }
}
