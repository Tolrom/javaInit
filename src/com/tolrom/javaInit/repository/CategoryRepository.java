package com.tolrom.javaInit.repository;

import com.tolrom.javaInit.db.Database;
import com.tolrom.javaInit.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CategoryRepository {

    // Attributes

    private static Connection connection = Database.getConnection();

    // Methods

    public static Category save(Category category){
        Category newCategory = null;
        try {
            // Request
            String sql = "INSERT INTO category(category_name) " +
                         "VALUE(?);";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind params
            preparedStatement.setString(1, category.getCategoryName());
            // Execute
            int nbRows = preparedStatement.executeUpdate();
            // Check if request went through
            if (nbRows > 0){
                newCategory = new Category(
                        category.getCategoryName()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newCategory;
    }
    public static boolean exists(String categoryName){
        boolean getCategory = false;
        try {
            String sql = "SELECT id FROM category WHERE category_name = ?";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind Params
            preparedStatement.setString(1, categoryName);
            // Retrieve result
            ResultSet resultSet = preparedStatement.executeQuery();
            // Check result
            while(resultSet.next()){
                getCategory = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return getCategory;
    }
    public static Category findByName(String categoryName) {
        Category category = null;
        try {
            // Request
            String sql = "SELECT id, category_name FROM category WHERE category_name = ?";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind param
            preparedStatement.setString(1, categoryName);
            // Execute
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                category = new Category();
                category.setId(result.getInt("id"));
                category.setCategoryName(result.getString("category_name"));
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }
    public static ArrayList<Category> findAll(){
        Category category = null;
        ArrayList<Category> categories = new ArrayList<>();
        try {
            // Request
            String sql = "SELECT id, category_name FROM category ";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Execute
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                category = new Category();
                category.setId(result.getInt("id"));
                category.setCategoryName(result.getString("category_name"));
                categories.add(category);
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }
}
