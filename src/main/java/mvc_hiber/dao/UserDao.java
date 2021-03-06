package mvc_hiber.dao;

import mvc_hiber.model.User;


import java.util.List;


public interface UserDao {
    void saveUser(User user);

    User getUserById(long id);

    List<User> getAllUsers();

    void editUser(User user);

    void deleteUser(long id);
}