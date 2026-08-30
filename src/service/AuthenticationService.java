package service;

import model.User;
import repository.UserRepository;

public class AuthenticationService {

    private UserRepository userRepository =
            new UserRepository();

    public boolean register(User user) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }

        user.setId(userRepository.getNextId());

        userRepository.addUser(user);

        return true;
    }

    public User login(String username,
                      String password) {

        User user =
                userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        if (!user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }

}