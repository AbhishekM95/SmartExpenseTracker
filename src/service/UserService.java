package service;

import dao.UserDAO;
import model.User;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public boolean registerUser(User user) {

        if (user.getName() == null || user.getName().isEmpty()) {
            return false;
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return false;
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return false;
        }

        return userDAO.registerUser(user);
    }


    public User loginUser(String email, String password) {

        if (email == null || email.isEmpty()) {
            return null;
        }

        if (password == null || password.isEmpty()) {
            return null;
        }

        return userDAO.loginUser(email, password);
    }
}