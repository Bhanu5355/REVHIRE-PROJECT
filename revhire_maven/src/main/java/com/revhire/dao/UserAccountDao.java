package com.revhire.dao;

import java.sql.*;
import com.revhire.entity.UserAccount;
import com.revhire.util.DBConnection;

public class UserAccountDao {

    public boolean emailExists(String email) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps =
                con.prepareStatement("SELECT 1 FROM USER_ACCOUNT WHERE EMAIL=?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();
        rs.close(); ps.close(); con.close();
        return exists;
    }

    public boolean register(UserAccount u) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO USER_ACCOUNT VALUES (USER_ACCOUNT_SEQ.NEXTVAL,?,?,?,?,?,?,?,NULL)"
        );
        ps.setString(1, u.getRole());
        ps.setString(2, u.getName());
        ps.setString(3, u.getEmail());
        ps.setString(4, u.getPasswordHash());
        ps.setString(5, u.getSecurityQuestion());
        ps.setString(6, u.getSecurityAnswerHash());
        ps.setTimestamp(7, u.getCreatedAt());

        int r = ps.executeUpdate();
        ps.close(); con.close();
        return r > 0;
    }

    public UserAccount login(String email, String password) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM USER_ACCOUNT WHERE EMAIL=? AND PASSWORD_HASH=?"
        );
        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        UserAccount u = null;
        if (rs.next()) {
            u = new UserAccount();
            u.setUserId(rs.getInt("USER_ID"));
            u.setRole(rs.getString("ROLE"));
            u.setName(rs.getString("NAME"));
        }
        rs.close(); ps.close(); con.close();
        return u;
    }

    public boolean saveResume(int userId, String resume) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps =
            con.prepareStatement("UPDATE USER_ACCOUNT SET RESUME_TEXT=? WHERE USER_ID=?");
        ps.setString(1, resume);
        ps.setInt(2, userId);
        int r = ps.executeUpdate();
        ps.close(); con.close();
        return r > 0;
    }
}
