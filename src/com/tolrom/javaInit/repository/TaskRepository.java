package com.tolrom.javaInit.repository;

import com.tolrom.javaInit.db.Database;
import com.tolrom.javaInit.model.Task;
import com.tolrom.javaInit.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TaskRepository {

    // Attributes

    private static Connection connection = Database.getConnection();

    // Methods

    public Task save(Task task, User user){
        Task newTask = null;
        try {
            // Request
            String sql = "INSERT INTO task(title, content, user_id) " +
                         "VALUE(?,?,?);";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind params
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getContent());
            preparedStatement.setInt(3, user.getId());
            // Execute
            int nbRows = preparedStatement.executeUpdate();
            // Check if request went through
            if (nbRows > 0){
                newTask = new Task(
                        task.getTitle(),
                        task.getContent(),
                        null
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newTask;
    }
    public boolean exists(String title){
        boolean getTask = false;
        try {
            String sql = "SELECT id FROM task WHERE title = ?";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind Params
            preparedStatement.setString(1, title);
            // Retrieve result
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check result
            while(resultSet.next()){
                getTask = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return getTask;
    }
    public static Task findByTitle(String title) {
        Task task = null;
        try {
            // Request
            String sql = "SELECT id, title, content FROM task WHERE title = ?";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind param
            preparedStatement.setString(1, title);
            // Execute
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                task = new Task();
                task.setId(result.getInt("id"));
                task.setTitle(result.getString("title"));
                task.setContent(result.getString("content"));
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }
    public static ArrayList<Task> findAll(){
        Task task = null;
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            // Request
            String sql = "SELECT id, title, content FROM task ";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Execute
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                task = new Task();
                task.setId(result.getInt("id"));
                task.setTitle(result.getString("title"));
                task.setContent(result.getString("content"));
                tasks.add(task);
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
