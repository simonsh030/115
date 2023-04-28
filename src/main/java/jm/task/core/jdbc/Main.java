package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Gans", "Jonson", (byte) 23);

        userService.saveUser("Rick", "Sanches", (byte) 68);

        userService.saveUser("Tinki", "Winki", (byte) 11);

        userService.saveUser("Vasya", "Petrov", (byte) 75);

        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();



    }
}