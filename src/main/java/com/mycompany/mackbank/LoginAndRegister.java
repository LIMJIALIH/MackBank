package com.mycompany.mackbank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.sql.*;
import javax.swing.JOptionPane;

class LoginAndRegister{
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private String name;
    private String email;
    private String password;
   //Create a constructor for login page
    public LoginAndRegister(String email, String password){
        this.email = email;
        this.password = password;
    }
    //Overloading for constructor register page
    public LoginAndRegister(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
    //method for login page
    public int Login(){
        int userId = -1;
        //Connect to database
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String info = "SELECT user_id,password FROM users WHERE email = ?"; //Pass the commend to the database
            try(PreparedStatement preparedStatement = connection.prepareStatement(info)){
                preparedStatement.setString(1, email);
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    if(resultSet.next()){
                        String hashedPassword = resultSet.getString("password"); //Get the hashed password that store in the database
                        if(passwordEncoder.matches(password, hashedPassword)){  //Check if the password is match with the hashed password
                            JOptionPane.showMessageDialog(null, "Login Successful!!!", "Login", JOptionPane.INFORMATION_MESSAGE);
//System.out.print("\nLogin Successful!");
                            userId = resultSet.getInt("user_id");
                        }else{
                            JOptionPane.showMessageDialog(null, "Error with email or password input!", "Failed Login", JOptionPane.WARNING_MESSAGE);
//System.out.print("\nError with email or password input!"); //Appear when user input a wrong email or password
                            userId = -1;
                            return userId;
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "No user with this email is found!", "Login", JOptionPane.WARNING_MESSAGE);
//System.out.print("\nNo user with this email is found!"); //Appear when the email input by user is not appear
                        userId = -2;
                        return userId;
                    }
                }
            }
        }catch (SQLException e){
            System.out.print("\nFailed to connect with Database!");
        }
        return userId;
    }
    
    public String getName(){ 
        String username = "";
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String getName = "SELECT name FROM users WHERE email = ?";
            try(PreparedStatement prepareStatement = connection.prepareStatement(getName)){
                prepareStatement.setString(1, email);
                try(ResultSet resultSet = prepareStatement.executeQuery()){
                    if(resultSet.next()){
                    username = resultSet.getString("name");
                    }else{
                        System.out.print("\nNo user found with this email");
                    }
                    
                }
            }
        }catch(SQLException e){
            System.out.print("\nError in finding username:" + e.getMessage());
        }
        return username;
    }
    
    // In LoginAndRegister.java
public void Register() {
    // Hash the password
    String hashedPassword = passwordEncoder.encode(password);

    // Connect to the database
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")) {

        // Query to check if the email already exists
        String checkEmailQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkEmailQuery)) {
            checkStatement.setString(1, email);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                // Email already exists
                JOptionPane.showMessageDialog(null, "This email is already registered. Please use a different email.", 
                    "Registration Failed", JOptionPane.WARNING_MESSAGE);
                return; 
            }
        }

        // If email does not exist, proceed with registration
        String registerQuery = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement registerStatement = connection.prepareStatement(registerQuery)) {
            registerStatement.setString(1, name);         
            registerStatement.setString(2, email);        
            registerStatement.setString(3, hashedPassword); 

            registerStatement.executeUpdate(); 
            
            JOptionPane.showMessageDialog(null, "Registration successful! Please login.", 
                "Registration Success", JOptionPane.INFORMATION_MESSAGE);

        }

    } catch (SQLException e) {
        if (e.getMessage().contains("Duplicate entry")) {
            JOptionPane.showMessageDialog(null, "This email is already registered. Please use a different email.", 
                "Registration Failed", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database. Please try again later.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            System.out.print("\nFailed to connect with the database! Error: " + e.getMessage());
        }
    }
}
}
    