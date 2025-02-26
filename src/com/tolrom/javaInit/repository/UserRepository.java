package com.tolrom.javaInit.repository;

import com.tolrom.javaInit.db.Database;
import com.tolrom.javaInit.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserRepository {

    // Attributes

    private static Connection connection = Database.getConnection();

    // Methods

    public static User save(User user) {
        try {
            // Request
            String sql =    "INSERT INTO users(firstname, lastname, email, password) " +
                            "VALUE(?,?,?,?);";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind params
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            // Execute
            int nbRows = preparedStatement.executeUpdate();
            // Check if request went through
            if (nbRows > 0){
                User newUser = new User(
                        user.getFirstname(),
                        user.getLastname(),
                        user.getEmail(),
                        user.getPassword()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User();
    }
    public static User findByMail(String email) {
        User user = null;
        try {
            // Request
            String sql = "SELECT id, firstname, lastname, email FROM users WHERE email = ?";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind param
            preparedStatement.setString(1, email);
            // Execute
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                user = new User();
                user.setId(result.getInt("id"));
                user.setFirstname(result.getString("firstname"));
                user.setLastname(result.getString("lastname"));
                user.setEmail(result.getString("email"));
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    public static ArrayList<User> findAll(){
        User user = null;
        ArrayList<User> users = new ArrayList<>();
        try {
            // Request
            String sql = "SELECT id, firstname, lastname, email FROM users";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Execute
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                user = new User();
                user.setId(result.getInt("id"));
                user.setFirstname(result.getString("firstname"));
                user.setLastname(result.getString("lastname"));
                user.setEmail(result.getString("email"));
                users.add(user);
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    public static User update(User updatedUser, String email){
        User user = findByMail(email);
        if (user == null) {
            System.out.println("No user found");
            return null;
        }
        try {
            // Request
            String sql =    "UPDATE users " +
                            "SET firstname = ?," +
                                "lastname = ?," +
                                "email = ? " +
                            "WHERE id = ?";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind param
            preparedStatement.setString(1, updatedUser.getFirstname());
            preparedStatement.setString(2, updatedUser.getLastname());
            preparedStatement.setString(3, updatedUser.getEmail());
            preparedStatement.setInt(4, user.getId());
            // Execute
            int nbRows = preparedStatement.executeUpdate();
            if (nbRows > 0) {
                // Retrieve updated user
                user = findByMail(updatedUser.getEmail());
            } else {
                System.out.println("No update");
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static String delete(int id){
        try {
            // Request
            String sql = "DELETE FROM users WHERE id = ?";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind param
            preparedStatement.setInt(1, id);
            // Execute
            ResultSet result = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
