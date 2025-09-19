package org.smart_job.session;

import org.smart_job.entity.User;

/**
 * Singleton class to store current logged-in user info
 */
public class UserSession {

    private static UserSession instance;

    private User currentUser;

    // Private constructor để đảm bảo chỉ có 1 instance
    private UserSession() {}

    // Lấy instance duy nhất
    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Lưu user hiện tại
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Lấy user hiện tại
    public User getCurrentUser() {
        return currentUser;
    }

    // Kiểm tra đã login hay chưa
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    // Logout user
    public void clear() {
        currentUser = null;
    }
}
