package com.tolrom.javaInit.repository;

import com.tolrom.javaInit.db.Database;
import com.tolrom.javaInit.model.Category;
import com.tolrom.javaInit.model.Role;
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

    public static Task save(Task task, Category[] categories){
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
            preparedStatement.setInt(3, UserRepository.findByMail(task.getUser().getEmail()).getId());
            // Execute
            int nbRows = preparedStatement.executeUpdate();
            // Check if request went through
            if (nbRows > 0){
                newTask = new Task(
                        task.getTitle(),
                        task.getContent(),
                        task.getUser()
                );
                newTask.setId(getLastId());
                for (Category category : categories){
                    // Request
                    sql = "INSERT INTO task_category(task_id, category_id) " +
                          "VALUE(?,?);";
                    // Prepare
                    preparedStatement = connection.prepareStatement(sql);
                    // Bind params
                    preparedStatement.setInt(1, newTask.getId());
                    preparedStatement.setInt(2, CategoryRepository.findByName(category.getCategoryName()).getId());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newTask;
    }
    public static boolean exists(String title){
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
        User user = null;
        Role role = null;
        ArrayList<Category> cats = new ArrayList<>();
        try {
            // Request
            String sql =    "SELECT t.id AS tId, t.title, t.content, t.created_at, t.end_date, t.`status`, \n" +
                            "u.id AS uId, u.firstname, u.lastname, r.id AS rId, r.role_name AS rName,\n" +
                            "group_concat(c.id) AS catId,\n" +
                            "group_concat(c.category_name) AS catName \n" +
                            "FROM task AS t\n" +
                            "LEFT JOIN task_category AS tc ON tc.task_id = t.id\n" +
                            "LEFT JOIN category AS c ON tc.category_id = c.id\n" +
                            "INNER JOIN user AS u ON t.user_id = u.id\n" +
                            "INNER JOIN role AS r ON u.role_id = r.id\n" +
                            "WHERE t.title = ? "+
                            "GROUP BY t.id";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Bind param
            preparedStatement.setString(1, title);
            // Execute
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                // Task instance
                task = new Task();
                task.setId(result.getInt("tId"));
                task.setTitle(result.getString("title"));
                task.setContent(result.getString("content"));
                task.setCreatedAt(result.getDate("created_at"));
                task.setEndDate(result.getDate("end_date"));
                task.setStatus(result.getBoolean("status"));
                // User instance
                user = new User();
                user.setId(result.getInt("uId"));
                user.setFirstname(result.getString("firstname"));
                user.setLastname(result.getString("lastname"));
                task.setUser(user);
                // Role instance
                role = new Role();
                role.setId(result.getInt("rId"));
                role.setRoleName(result.getString("rName"));
                // Categories instance
                String catNames = result.getString("catName");
                String catIds = result.getString("catId");
                if (catNames != null && catIds != null) {
                    String[] catNameArray = catNames.split(",");
                    String[] catIdArray = catIds.split(",");
                    for (int i = 0; i < catNameArray.length; i++) {
                        Category category = new Category();
                        category.setId(Integer.parseInt(catIdArray[i].trim()));
                        category.setCategoryName(catNameArray[i].trim());
                        cats.add(category);
                    }
                }
                for (Category cat : cats) {
                    task.addCategory(cat);
                }
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
    public static int getLastId() {
        int id = 0;
        try {
            // Request
            String sql = "SELECT id FROM task ORDER BY id DESC LIMIT 1;";
            // Prepare
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Execute
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                id = result.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
