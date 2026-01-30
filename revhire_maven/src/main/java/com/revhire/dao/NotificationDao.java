package com.revhire.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revhire.entity.Notification;
import com.revhire.util.DBConnection;

public class NotificationDao {

    public boolean addNotification(Notification n) {
        String sql = "INSERT INTO notification "
                   + "(notification_id, user_id, title, message, type, is_read, created_at) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, n.getNotificationId());
            ps.setInt(2, n.getUserId());
            ps.setString(3, n.getTitle());
            ps.setString(4, n.getMessage());
            ps.setString(5, n.getType());
            ps.setBoolean(6, n.isRead());
            ps.setTimestamp(7, n.getCreatedAt());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); if (con != null) con.close(); } catch (Exception e) {}
        }
        return false;
    }

    public List<Notification> getNotifications(int userId) {
        List<Notification> list = new ArrayList<Notification>();
        String sql = "SELECT * FROM notification WHERE user_id=?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Notification n = new Notification();
                n.setTitle(rs.getString("title"));
                n.setMessage(rs.getString("message"));
                n.setRead(rs.getBoolean("is_read"));
                list.add(n);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); if (ps != null) ps.close(); if (con != null) con.close(); } catch (Exception e) {}
        }
        return list;
    }
}
