
package com.mycompany.mackbank;


import java.sql.*;

class DebitAndCredit {
    
    //Create constructor for debit
    private int userId;
    
    public DebitAndCredit(int userId){
        this.userId = userId;
    }
    /*
    public double getBalance(){
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        double balance = 0;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
        String getBalance = "SELECT SUM(CASE WHEN transaction_type = 'debit' THEN amount ELSE - amount END) AS balance FROM Transactions where user_id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(getBalance)){
            preparedStatement.setInt(1, userId);
            try(ResultSet result = preparedStatement.executeQuery()){
                if(result.next()){
                balance = result.getDouble("balance");
                }else{
                    System.out.print("\nNo transactions found for this user");
                }
            }
        }
        }catch (SQLException e){
            System.out.print("\nError in getting the balannce: " + e.getMessage());
        }
        
        return balance;
    }*/
    
    public double getBalance(){
        double balance = 0;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String query = "SELECT SUM(CASE WHEN transaction_type = 'debit' THEN amount WHEN transaction_type = 'credit' THEN -amount ELSE 0 END) AS totalBalance FROM transactions WHERE user_id = ? ORDER by date ASC;"; 
            try(PreparedStatement ps = connection.prepareStatement(query)){
                ps.setInt(1, userId);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        balance = rs.getDouble("totalBalance");
                    }else{
                        return 0;
                    }
                }
            }
        }catch(SQLException e){
            System.out.print("\nFailed in retrieved the balance of this user:" + e.getMessage());
        }
        return balance;
    }
    
    public void debit(double debit, String description) {
        Savings recordSavings = new Savings(userId);
        int getPercentage = recordSavings.getPercentage();
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        
        if(getPercentage == 0){
            double currentBalance = getBalance();
            double newBalance = currentBalance + debit;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String storeDebit = "INSERT INTO Transactions (user_id,transaction_type,amount,description,date,balance) VALUES (?,'debit',?,?,?,?)";
            try(PreparedStatement prepare = connection.prepareStatement(storeDebit)){
                prepare.setInt(1, userId);
                prepare.setDouble(2, debit);
                prepare.setString(3, description);
                prepare.setTimestamp(4, timeStamp);
                prepare.setDouble(5, newBalance);
                prepare.executeUpdate();
                System.out.print("\nDebit Successfully Recorded!!!");
            }
        }catch(SQLException e){
            System.out.print("\nFailed in recording the debit:" + e.getMessage());
        }
    }else{
            double savingsAmount = debit*getPercentage/100;
            double remainingDebit = debit-savingsAmount;
            double currentBalance = getBalance();
            double newBalance = currentBalance + remainingDebit;
            recordSavings.CalculateAndSaveMoney(debit);
            
            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                String storeDebitSavings = "INSERT INTO Transactions (user_id, transaction_type, amount,description,date,balance) VALUES (?,'debit',?,?,?,?)";
                try(PreparedStatement ps = connection.prepareStatement(storeDebitSavings)){
                    ps.setInt(1, userId);
                    ps.setDouble(2, remainingDebit);
                    ps.setString(3, description);
                    ps.setTimestamp(4, timeStamp);
                    ps.setDouble(5, newBalance);
                    ps.executeUpdate();
                    System.out.print("\nDebit and savings are successfully recorded!!!");
                }
            }catch(SQLException e){
                System.out.print("\nFailed in recording the debit and savings:");
            }   
        }
    
}
    public void credit(double credit, String description){
            double currentBalance = getBalance();
            double newBalance = currentBalance - credit;
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String storeCredit = "INSERT INTO Transactions(user_id, transaction_type,amount,description,date,balance) VALUES(?,'credit',?,?,?,?)";
            try(PreparedStatement prepare = connection.prepareStatement(storeCredit)){
                prepare.setInt(1, userId);
                prepare.setDouble(2, credit);
                prepare.setString(3, description);
                prepare.setTimestamp(4, timeStamp);
                prepare.setDouble(5, newBalance);
                prepare.executeUpdate();
                System.out.print("\nCredit Successfully Recorded!!!");
            }
        }catch(SQLException e){
            System.out.print("\nFailed in recording the credit:" + e.getMessage());
        }
        }
}

