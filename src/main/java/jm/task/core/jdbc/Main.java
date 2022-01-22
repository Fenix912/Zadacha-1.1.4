package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        userServiceImpl.createUsersTable();

        userServiceImpl.saveUser("Dima", "Fateev", (byte) 22);
        userServiceImpl.saveUser("Anton", "Rakov", (byte) 45);
        userServiceImpl.saveUser("Igor", "Kuzelev", (byte) 30);
        userServiceImpl.saveUser("Vlad", "Snegirev", (byte) 31);

        userServiceImpl.getAllUsers().forEach(System.out::println);
        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();
    }
}
