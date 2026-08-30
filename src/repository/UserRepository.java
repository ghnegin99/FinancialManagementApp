package repository;

import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static final String FILE_PATH = "data/users.txt";

    /**
     * برگرداندن تمام کاربران
     */
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                User user = new User(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        data[5]
                );

                users.add(user);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * اضافه کردن کاربر جدید
     */
    public void addUser(User user) {

        List<User> users = getAllUsers();

        users.add(user);

        saveAllUsers(users);
    }


    public User findByUsername(String username) {

        List<User> users = getAllUsers();

        for (User user : users) {

            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }

        }

        return null;
    }

    /**
     * جستجو بر اساس شناسه
     */
    public User findById(int id) {

        List<User> users = getAllUsers();

        for (User user : users) {

            if (user.getId() == id) {
                return user;
            }

        }

        return null;
    }

    /**
     * حذف کاربر
     */
    public void deleteUser(int id) {

        List<User> users = getAllUsers();

        users.removeIf(user -> user.getId() == id);

        saveAllUsers(users);
    }

    /**
     * ویرایش اطلاعات کاربر
     */
    public void updateUser(User updatedUser) {

        List<User> users = getAllUsers();

        for (int i = 0; i < users.size(); i++) {

            if (users.get(i).getId() == updatedUser.getId()) {

                users.set(i, updatedUser);

                break;
            }
        }

        saveAllUsers(users);
    }

    /**
     * ذخیره تمام کاربران داخل فایل
     */
    private void saveAllUsers(List<User> users) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_PATH))) {

            for (User user : users) {

                writer.write(
                        user.getId() + "," +
                                user.getUsername() + "," +
                                user.getPassword() + "," +
                                user.getFullName() + "," +
                                user.getEmail() + "," +
                                user.getPhone()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getNextId() {

        int max = 0;

        List<User> users = getAllUsers();

        for (User user : users) {

            if (user.getId() > max) {

                max = user.getId();

            }

        }

        return max + 1;

    }
}