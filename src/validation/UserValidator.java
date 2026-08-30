package validation;

import model.User;

public class UserValidator {

    public static String validate(User user) {

        if (user.getUsername() == null ||
                user.getUsername().trim().isEmpty()) {

            return "Username cannot be empty.";

        }

        if (user.getPassword() == null ||
                user.getPassword().trim().isEmpty()) {

            return "Password cannot be empty.";

        }

        if (user.getFullName() == null ||
                user.getFullName().trim().isEmpty()) {

            return "Full name cannot be empty.";

        }

        if (user.getEmail() == null ||
                !user.getEmail().contains("@")) {

            return "Invalid email.";

        }

        if (user.getPhone() == null ||
                user.getPhone().trim().isEmpty()) {

            return "Phone number cannot be empty.";

        }

        return null;

    }

}