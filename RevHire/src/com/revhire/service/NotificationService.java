package com.revhire.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import com.revhire.dao.NotificationDao;
import com.revhire.entity.Notification;

public class NotificationService {

    private static final Logger logger =
            Logger.getLogger(NotificationService.class);

    private NotificationDao dao = new NotificationDao();

    public boolean notifyUser(int userId, String title, String message, String type) {

        logger.info("Sending notification to userId=" + userId);

        Notification n = new Notification();
        n.setNotificationId(generateId());
        n.setUserId(userId);
        n.setTitle(title);
        n.setMessage(message);
        n.setType(type);
        n.setRead(false);
        n.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        boolean result = dao.addNotification(n);
        logger.info("Notification result: " + result);

        return result;
    }

    public List<Notification> viewNotifications(int userId) {
        logger.info("Viewing notifications for userId=" + userId);
        return dao.getNotifications(userId);
    }

    private int generateId() {
        return (int) (System.currentTimeMillis() % 100000);
    }
}
