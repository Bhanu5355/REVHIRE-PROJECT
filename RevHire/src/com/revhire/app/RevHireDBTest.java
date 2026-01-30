package com.revhire.app;

import java.sql.Connection;
import com.revhire.util.DBConnection;

public class RevHireDBTest {

    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con != null) {
            System.out.println("Connected to Oracle 10g as REVHIRE");
        } else {
            System.out.println("Connection failed");
        }
    }
}
