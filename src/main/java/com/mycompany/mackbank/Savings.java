package com.mycompany.mackbank;

import java.sql.*;
class Savings {
    private int user_id;
    private String status;
    private int percentage;
    
    public Savings(int user_id){
        this.user_id = user_id;
    }
    
    public Savings(int user_id, int percentage){
        this.user_id  = user_id;
        this.percentage = percentage;
    }
    
    public Savings(int user_id,String status, int percentage){
        this.user_id = user_id;
        this.status = status;
        this.percentage = percentage;
    }
   
    public void openSavings(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String insertSavings = "INSERT INTO Savings(user_id,Status,Percentage,savings,last_transfer_date) VALUES (?,?,?,0,?)";
            try(PreparedStatement ps = connection.prepareStatement(insertSavings)){
                ps.setInt(1, user_id);
                ps.setString(2, status);
                ps.setInt(3, percentage);
                ps.setTimestamp(4, timestamp);
                ps.executeUpdate();
                System.out.print("\nSavings Settings added successfully!!!");
            }
        }catch(SQLException e){
            System.out.print("\nFailed in getting the savings:" + e.getMessage());
        }
    }
    
    public int getPercentage(){
            int percentage = 0;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                String sentenceGetPercentage = "SELECT Percentage FROM Savings WHERE user_id = ?";
                try(PreparedStatement ps = connection.prepareStatement(sentenceGetPercentage)){
                    ps.setInt(1, user_id);
                    try(ResultSet rs = ps.executeQuery()){
                        if(rs.next()){
                            percentage = rs.getInt("Percentage");
                        }else{
                            return 0;
                        }
                    }
                }
        }catch(SQLException e){
           System.out.print("\nFailed in getting the savings percentage:" + e.getMessage());
        }
        return percentage;
}
    
        public String getStatus(){
            String status = null;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                String sentenceGetPercentage = "SELECT Status FROM Savings WHERE user_id = ?";
                try(PreparedStatement ps = connection.prepareStatement(sentenceGetPercentage)){
                    ps.setInt(1, user_id);
                    try(ResultSet rs = ps.executeQuery()){
                        if(rs.next()){
                            status = rs.getString("Status");
                        }
                    }
                }
        }catch(SQLException e){
           System.out.print("\nFailed in getting the savings status:" + e.getMessage());
        }
        return status != null ? status: "not activate";
}
        
        public double getSavings(){
            double savings = 0.0;
            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                String getSavingsQuery = "SELECT savings FROM Savings WHERE user_id = ?";
                try(PreparedStatement ps = connection.prepareStatement(getSavingsQuery)){
                    ps.setInt(1, user_id);
                    try(ResultSet rs = ps.executeQuery()){
                        if(rs.next()){
                            savings = rs.getDouble("savings");
                        }else{
                            return 0;
                        }
                    }
                }
            }catch(SQLException e){
                System.out.print("\nFailed in getting the savings:" + e.getMessage());
            }
            return savings;
        }
        
        
        public Timestamp getTime(){
            Timestamp last_transfer_date = new Timestamp(System.currentTimeMillis());
            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                String getDateQuery = "SELECT last_transfer_date FROM Savings WHERE user_id = ?";
                try(PreparedStatement ps = connection.prepareStatement(getDateQuery)){
                    ps.setInt(1, user_id);
                    try(ResultSet rs = ps.executeQuery()){
                        if(rs.next()){
                            last_transfer_date = rs.getTimestamp("last_transfer_date");
                        }else{
                            System.out.print("\nNo savings time record found for this user.");
                        }
                    }
                }
            }catch(SQLException e){
                System.out.print("\nFailed in getting the savings time:" + e.getMessage());
            }
            return last_transfer_date;
        }
        
        
        public void CalculateAndSaveMoney(double debit){
            double savingsAmount = debit* getPercentage()/100;
            double updatedSavings = getSavings() + savingsAmount;
            try(Connection connection  = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                String updateSavingsQuery = "UPDATE Savings SET savings = ?, Percentage = 0 WHERE user_id=?";
                try(PreparedStatement ps = connection.prepareStatement(updateSavingsQuery)){
                   ps.setDouble(1, updatedSavings);
                    ps.setInt(2, user_id);
                    ps.executeUpdate();
                    System.out.print("\nYou have successfully save " + savingsAmount + " into your savings account!");
                    System.out.print("\nThe savings percentage has been reset to 0! Please insert savings percentage again in option 4!");
                }
            }catch(SQLException e){
                    System.out.print("\nFailed to update savings:" + e.getMessage());
                    }
        }
        
        public void setPercentage(int percentage){
                try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                    String updateQuery = "UPDATE Savings SET Percentage = ? WHERE user_id = ?";
                    try(PreparedStatement ps = connection.prepareStatement(updateQuery)){
                        ps.setInt(1, percentage);
                        ps.setInt(2, user_id);
                        ps.executeUpdate();
                        System.out.print("\nYour savings percentage have been update to " + percentage);
                    }
                }catch(SQLException e){
                    System.out.print("\nFailed to update the percentage:" + e.getMessage());
                }
        }
        
        public void transferSavingsToBalance(){
            double savings;
            Timestamp lastTimeStamp;
            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                String checkTime = "SELECT savings,last_transfer_date FROM Savings WHERE user_id = ?";
                try(PreparedStatement ps = connection.prepareStatement(checkTime)){
                    ps.setInt(1, user_id);
                    try(ResultSet rs = ps.executeQuery()){
                        if(rs.next()){
                            savings = rs.getDouble("savings");
                            lastTimeStamp = rs.getTimestamp("last_transfer_date");
                            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                            
                            long month = 30L* 24 * 60 * 60 * 1000;
                            if(currentTime.getTime() - lastTimeStamp.getTime() >= month){
                                String updateBalance = "INSERT INTO Transactions(user_id,transaction_type,amount,description,date) VALUES (?,'debit',?,'Savings Transfer',?)";
                                try(PreparedStatement psBalance = connection.prepareStatement(updateBalance)){
                                    psBalance.setInt(1, user_id);
                                    psBalance.setDouble(2, savings);
                                    psBalance.setTimestamp(3, currentTime);
                                    psBalance.executeUpdate();
                                }
                                String resetSavings = "UPDATE Savings SET savings = 0, last_transfer_date =? WHERE user_id = ?";
                                try(PreparedStatement psUpdateSavings = connection.prepareStatement(resetSavings)){
                                    psUpdateSavings.setTimestamp(1, currentTime);
                                    psUpdateSavings.setInt(2, user_id);
                                    psUpdateSavings.executeUpdate();
                                }
                                System.out.print("\nSavings has been transfer back to balance successfully!");
                            }
                            else{
                                System.out.print("");
                            }
                        }
                    }
                }
            }catch(SQLException e){
                System.out.print("\nFailed in transfer the savings into balance! " + e.getMessage());
            }
        }
}
