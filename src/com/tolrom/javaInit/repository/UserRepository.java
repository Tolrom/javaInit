package com.tolrom.javaInit.repository;

import com.tolrom.javaInit.db.Database;
import com.tolrom.javaInit.model.Role;
import com.tolrom.javaInit.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserRepository {

    // Attributes

    private static Connection connection = Database.getConnection();

    // Methods

    public static User save(User user, Role role) {
        User newUser = null;
        try {
            // Request
            String sql =    "INSERT INTO user(firstname, lastname, email, password, role_id) " +
                            "VALUE(?,?,?,?,?);";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind params
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, RoleRepository.findByName(role.getRoleName()).getId());
            // Execute
            int nbRows = preparedStatement.executeUpdate();
            // Check if request went through
            if (nbRows > 0){
                newUser = new User(
                        user.getFirstname(),
                        user.getLastname(),
                        user.getEmail(),
                        user.getPassword()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUser;
    }
    public static boolean exists(String email) {
        boolean getUser = false;
        try {
            String sql = "SELECT id FROM user WHERE email = ?";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind Params
            preparedStatement.setString(1, email);
            // Retrieve result
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check result
            while(resultSet.next()){
                getUser = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return getUser;
    }
    public static User findByMail(String email) {
        User user = null;
        try {
            // Request
            String sql = "SELECT id, firstname, lastname, email FROM user WHERE email = ?";
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
            String sql = "SELECT id, firstname, lastname, email FROM user";
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
            String sql =    "UPDATE user " +
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
    public  static User saveWithRole(User user){
        User newUser = null;
        try {
            String sql = "INSERT INTO user(firstname, lastname, email, password, role_id)" +
                    "VALUE(?,?,?,?,(SELECT id FROM role WHERE role_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind params
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(2, user.getRole().getRoleName());
            int nbRows = preparedStatement.executeUpdate();
            if (nbRows > 0){
                newUser = user;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return newUser;
    }
}
