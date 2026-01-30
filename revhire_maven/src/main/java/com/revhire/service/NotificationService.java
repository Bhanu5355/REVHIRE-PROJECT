package com.revhire.service;

import java.sql.Timestamp;
import java.util.List;

import com.revhire.dao.NotificationDao;
import com.revhire.entity.Notification;

public class NotificationService {

    private NotificationDao dao = new NotificationDao();

    public boolean notifyUser(int userId, String title, String message, String type) {

        Notification n = new Notification();
        n.setNotificationId(generateId());
        n.setUserId(userId);
        n.setTitle(title);
        n.setMessage(message);
        n.setType(type);
        n.setRead(false);
        n.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return dao.addNotification(n);
    }

    public List<Notification> viewNotifications(int userId) {
        return dao.getNotifications(userId);
    }

    private int generateId() {
        return (int) (System.currentTimeMillis() % 100000);
    }
}
