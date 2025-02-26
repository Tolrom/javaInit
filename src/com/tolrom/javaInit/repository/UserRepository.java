package com.tolrom.javaInit.repository;

import com.tolrom.javaInit.db.Database;
import com.tolrom.javaInit.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
