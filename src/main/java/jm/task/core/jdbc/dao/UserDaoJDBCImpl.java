package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getConnection();


    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users" +
                "(" +
                "id BIGINT auto_increment, " +
                "name VARCHAR(64) not null, " +
                "lastName VARCHAR(128) not null, " +
                "age tinyint not null, " +
                "constraint table_name_pk " +
                "unique (id) " +
                ");";
        try (Connection conn = Util.getConnection(); Statement stmt = connection.createStatement()){
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection conn = Util.getConnection(); Statement stmt = connection.createStatement()){
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);
            if (pstmt.executeUpdate() == 1) {
                System.out.println("User с именем – " + name + " успешно добавлен в базу данных");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new LinkedList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User user = new User(rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age"));
                user.setId(rs.getLong("id"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE users";
        try (Statement stmt = connection.createStatement()){
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}