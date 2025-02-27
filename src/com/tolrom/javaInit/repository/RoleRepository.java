package com.tolrom.javaInit.repository;

import com.tolrom.javaInit.db.Database;
import com.tolrom.javaInit.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RoleRepository {

    // Attributes

    private static Connection connection = Database.getConnection();

    // Methods

    public static Role save(Role role){
        Role newRole = null;
        try {
            // Request
            String sql = "INSERT INTO role(role_name) " +
                    "VALUE(?);";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind params
            preparedStatement.setString(1, role.getRoleName());
            // Execute
            int nbRows = preparedStatement.executeUpdate();
            // Check if request went through
            if (nbRows > 0){
                newRole = new Role(
                        role.getRoleName()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newRole;
    }
    public static boolean exists(String roleName){
        boolean getRole = false;
        try {
            String sql = "SELECT id FROM role WHERE role_name = ?";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind Params
            preparedStatement.setString(1, roleName);
            // Retrieve result
            ResultSet resultSet = preparedStatement.executeQuery();
            // Check result
            while(resultSet.next()){
                getRole = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return getRole;
    }
    public static Role findByName(String roleName) {
        Role role = null;
        try {
            // Request
            String sql = "SELECT id, role_name FROM role WHERE role_name = ?";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind param
            preparedStatement.setString(1, roleName);
            // Execute
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                role = new Role();
                role.setId(result.getInt("id"));
                role.setRoleName(result.getString("role_name"));
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }
    public static ArrayList<Role> findAll(){
        Role role = null;
        ArrayList<Role> roles = new ArrayList<>();
        try {
            // Request
            String sql = "SELECT id, role_name FROM role ";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Execute
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                role = new Role();
                role.setId(result.getInt("id"));
                role.setRoleName(result.getString("role_name"));
                roles.add(role);
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roles;
    }
}
