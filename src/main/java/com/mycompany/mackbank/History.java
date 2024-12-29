package com.mycompany.mackbank;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
class History {
    
    private int userId;
    private int chooseHistory;
    private int filterChoice;
    private int filter;
    private double initialAmount;
    private double endAmount;
    private int sortType;
    
    public History(int userId, int chooseHistory){
        this.userId = userId;
        this.chooseHistory = chooseHistory;
    }
    
    public History(int userId, int chooseHistory, int sortType){
        this.userId = userId;
        this.chooseHistory = chooseHistory;
        this.sortType = sortType;
    }
    
    public History(int userId, int chooseHistory, int filterChoice, int filter){
        this.userId = userId;
        this.chooseHistory = chooseHistory;
        this.filterChoice = filterChoice;
        this.filter = filter;
    }
    
    public History(int userId, int chooseHistory, int filterChoice, double initialAmount, double endAmount){
        this.userId = userId;
        this.chooseHistory = chooseHistory;
        this.filterChoice = filterChoice;
        this.initialAmount = initialAmount;
        this.endAmount = endAmount;
    }
    
    public History(int userId, int chooseHistory, int filterChoice, int filterType, int sortType){
        this.userId = userId;
        this.chooseHistory = chooseHistory;
        this.filterChoice = filterChoice;
        this.filter = filterType;
        this.sortType = sortType;
    }
    
    public History(int userId, int chooseHistory, int filterChoice, double initialAmount, double endAmount, int sortType){
        this.userId = userId;
        this.chooseHistory = chooseHistory;
        this.filterChoice = filterChoice;
        this.initialAmount = initialAmount;
        this.endAmount = endAmount;
        this.sortType = sortType;
    }
    
        public void displayHistory() {
    List<Transactions> transactions = new ArrayList<>();
    
    // Original history
    if (chooseHistory == 1) {
        transactions = getHistory();
    }
    // Filter only
    else if (chooseHistory == 2) {
        if (filterChoice == 1) {
            transactions = getHistoryInDateRange(filter);
        } else if (filterChoice == 2) {
            transactions = getHistoryInTransactionType(filter);
        } else if (filterChoice == 3) {
            transactions = getHistoryInAmountRange();
        }
    }
    // Sort only
    else if (chooseHistory == 3) {
        if (sortType == 1 || sortType == 2) {
            transactions = getHistorySortDate();
        } else if (sortType == 3 || sortType == 4) {
            transactions = getHistorySortAmount();
        }
    }
    // Combined filter and sort
    else if (chooseHistory == 4) {
        // Get filtered transactions first
        if (filterChoice == 1) {
            transactions = getHistoryInDateRange(filter);
        } else if (filterChoice == 2) {
            transactions = getHistoryInTransactionType(filter);
        } else if (filterChoice == 3) {
            transactions = getHistoryInAmountRange();
        }
        
        // Then sort the filtered results
        if (sortType == 1) { // Date ascending
            Collections.sort(transactions, (t1, t2) -> t1.getDate().compareTo(t2.getDate()));
        } else if (sortType == 2) { // Date descending
            Collections.sort(transactions, (t1, t2) -> t2.getDate().compareTo(t1.getDate()));
        } else if (sortType == 3) { // Amount ascending
            Collections.sort(transactions, (t1, t2) -> Double.compare(t1.getAmount(), t2.getAmount()));
        } else if (sortType == 4) { // Amount descending
            Collections.sort(transactions, (t1, t2) -> Double.compare(t2.getAmount(), t1.getAmount()));
        }
    }

    if (transactions.isEmpty()) {
        System.out.print("\nNo transaction history found.");
    } else {
        System.out.print("\n== History ==");
        System.out.printf("\n%-15s%-40s%15s%15s%15s", "Date", "Description", "Debit", "Credit", "Balance");
        for (Transactions transaction : transactions) {
            System.out.print(transaction);
        }
    }
}
        
        
        
        //display CSV here
        public void formCSV(String filePath) {
            List<Transactions> transactions = new ArrayList<>();
    
        // Get transactions based on filter choice
            if (filterChoice == 1) {
                transactions = getHistoryInDateRange(filter);
            } else if (filterChoice == 2) {
                transactions = getHistoryInTransactionType(filter);
            } else if (filterChoice == 3) {
                transactions = getHistoryInAmountRange();
            } else {
                transactions = getHistory();
            }
    
        // Apply sorting if specified
            if (sortType > 0) {
                if (sortType == 1) { // Date ascending
                    Collections.sort(transactions, (t1, t2) -> t1.getDate().compareTo(t2.getDate()));
            } else if (sortType == 2) { // Date descending
                    Collections.sort(transactions, (t1, t2) -> t2.getDate().compareTo(t1.getDate()));
            } else if (sortType == 3) { // Amount ascending
                    Collections.sort(transactions, (t1, t2) -> Double.compare(t1.getAmount(), t2.getAmount()));
            } else if (sortType == 4) { // Amount descending
                    Collections.sort(transactions, (t1, t2) -> Double.compare(t2.getAmount(), t1.getAmount()));
        }
    }

    if (transactions.isEmpty()) {
        System.out.print("\nNo history to export as CSV file");
        return;
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        writer.append("Date,Description,Debit,Credit,Balance");
        writer.newLine();
        for (Transactions transaction : transactions) {
            writer.append(transaction.toCSVRow());
            writer.newLine();
        }
        System.out.print("\nFile Exported Successfully!!!");
        System.out.print("\nYour file name is " + filePath);
    } catch (IOException e) {
        System.out.print("\nFailed in forming csv file");
    }
}
    
        public List<Transactions> getHistory(){
            List <Transactions> transactions = new ArrayList<>();
            
            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                String searching = "SELECT transaction_type,amount,description,date,balance FROM Transactions WHERE user_id = ? ORDER BY date ASC";
                try(PreparedStatement ps = connection.prepareStatement(searching)){
                    ps.setInt(1, userId);
                    try(ResultSet rs = ps.executeQuery()){
                        while(rs.next()){
                        String transaction_type = rs.getString("transaction_type");
                        double amount = rs.getDouble("amount");
                        String description = rs.getString("description");
                        Timestamp date = rs.getTimestamp("date");
                        double balance = rs.getDouble("balance");
                        
                        transactions.add(new Transactions(balance,amount,description,date,transaction_type));
                    
                        }
                    }
                }
            }catch(SQLException e){
                System.out.print("\nFailed in retrieving transaction history:" + e.getMessage());
            }
            return transactions;
        }
           
        
        //Filtering option start from here
        public List<Transactions> getHistoryInDateRange(int dateRangeOption){
            List<Transactions> transactions = new ArrayList<>();
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = null;
            if(dateRangeOption == 1 ){ //Filter by last week
                startDate = endDate.minusWeeks(1);
            }else if(dateRangeOption == 2){ //Filter by last month
                startDate = endDate.minusMonths(1);
            }else if(dateRangeOption == 3){ //Filter by last year
                startDate = endDate.minusYears(1);
            }
            else{
                System.out.print("\nInvalid option.");
            }
            Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
            Timestamp endTimestamp = Timestamp.valueOf(endDate.atStartOfDay().plusDays(1).minusNanos(1));
            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                String query = "SELECT transaction_type, amount, description, date, balance "+ "FROM Transactions WHERE user_id = ? AND date >= ? AND date <= ? ORDER by date ASC";
                try(PreparedStatement ps = connection.prepareStatement(query)){
                    ps.setInt(1, userId);
                    ps.setTimestamp(2, startTimestamp);
                    ps.setTimestamp(3, endTimestamp);
                    try(ResultSet rs = ps.executeQuery()){
                        while(rs.next()){
                            String transaction_type = rs.getString("transaction_type");
                            double amount = rs.getDouble("amount");
                            String description = rs.getString("description");
                            Timestamp date = rs.getTimestamp("date");
                            double balance = rs.getDouble("balance");
                            
                            transactions.add(new Transactions(balance,amount,description,date,transaction_type));
                        }
                    }
                }
            }catch(SQLException e){
                System.out.print("\nFailed in retrieving transaction history in date range:" + e.getMessage());
            }
            return transactions;
        }
        
        public List<Transactions> getHistoryInTransactionType(int transactionType){
            List <Transactions> transactions = new ArrayList<>();
            if(transactionType == 1){   //Filter based on debit
                
            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                String searching = "SELECT transaction_type,amount,description,date,balance FROM Transactions WHERE user_id = ? AND transaction_type = 'debit' ORDER BY date ASC";
                try(PreparedStatement ps = connection.prepareStatement(searching)){
                    ps.setInt(1, userId);
                    try(ResultSet rs = ps.executeQuery()){
                        while(rs.next()){
                        String transaction_type = rs.getString("transaction_type");
                        double amount = rs.getDouble("amount");
                        String description = rs.getString("description");
                        Timestamp date = rs.getTimestamp("date");
                        double balance = rs.getDouble("balance");
                        
                        transactions.add(new Transactions(balance,amount,description,date,transaction_type));
                    
                        }
                    }
                }
            }catch(SQLException e){
                System.out.print("\nFailed in retrieving transaction history by debit transaction type:" + e.getMessage());
            }
            return transactions;
        }   
            else{          //Filter by credit transaction type
                try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                String searching = "SELECT transaction_type,amount,description,date,balance FROM Transactions WHERE user_id = ? AND transaction_type = 'credit' ORDER BY date ASC";
                try(PreparedStatement ps = connection.prepareStatement(searching)){
                    ps.setInt(1, userId);
                    try(ResultSet rs = ps.executeQuery()){
                        while(rs.next()){
                        String transaction_type = rs.getString("transaction_type");
                        double amount = rs.getDouble("amount");
                        String description = rs.getString("description");
                        Timestamp date = rs.getTimestamp("date");
                        double balance = rs.getDouble("balance");
                        
                        transactions.add(new Transactions(balance,amount,description,date,transaction_type));
                    
                        }
                    }
                }
            }catch(SQLException e){
                System.out.print("\nFailed in retrieving transaction history by credit transaction type:" + e.getMessage());
            }
            return transactions;
            }
        }
         
        
        
        public List<Transactions> getHistoryInAmountRange(){
            List<Transactions> transactions = new ArrayList<>();
            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                String query = "SELECT transaction_type, amount, description, date, balance "+ "FROM Transactions WHERE user_id = ? AND amount >= ? AND amount <= ? ORDER by date ASC";
                try(PreparedStatement ps = connection.prepareStatement(query)){
                    ps.setInt(1, userId);
                    ps.setDouble(2, initialAmount);
                    ps.setDouble(3, endAmount);
                    try(ResultSet rs = ps.executeQuery()){
                        while(rs.next()){
                            String transaction_type = rs.getString("transaction_type");
                            double amount = rs.getDouble("amount");
                            String description = rs.getString("description");
                            Timestamp date = rs.getTimestamp("date");
                            double balance = rs.getDouble("balance");
                            
                            transactions.add(new Transactions(balance,amount,description,date,transaction_type));
                        }
                    }
                }
            }catch(SQLException e){
                System.out.print("\nFailed in retrieving transaction history in amount range:" + e.getMessage());
            }
            return transactions;
        }
        
        //Start for sorting option
        public List<Transactions> getHistorySortDate(){
            List<Transactions> transactions = new ArrayList<>();
            String order = (sortType == 1) ? "ASC" : "DESC";
            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                String query = "SELECT transaction_type, amount, description, date, balance "+ "FROM Transactions WHERE user_id = ? ORDER by date " + order;
                try(PreparedStatement ps = connection.prepareStatement(query)){
                    ps.setInt(1, userId);
                    try(ResultSet rs = ps.executeQuery()){
                        while(rs.next()){
                            String transaction_type = rs.getString("transaction_type");
                            double amount = rs.getDouble("amount");
                            String description = rs.getString("description");
                            Timestamp date = rs.getTimestamp("date");
                            double balance = rs.getDouble("balance");
                            
                            transactions.add(new Transactions(balance,amount,description,date,transaction_type));
                        }
                    }
                }
            }catch(SQLException e){
                System.out.print("\nFailed in retrieving transaction history in ascending order date:" + e.getMessage());
            }
            return transactions;
            }
        
        
        public List<Transactions> getHistorySortAmount(){
            List<Transactions> transactions = new ArrayList<>();
            String order = (sortType == 3) ? "ASC" : "DESC";
            
            try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
                String query = "SELECT transaction_type, amount, description, date, balance "+ "FROM Transactions WHERE user_id = ? ORDER by amount " + order;
                try(PreparedStatement ps = connection.prepareStatement(query)){
                    ps.setInt(1, userId);
                    try(ResultSet rs = ps.executeQuery()){
                        while(rs.next()){
                            String transaction_type = rs.getString("transaction_type");
                            double amount = rs.getDouble("amount");
                            String description = rs.getString("description");
                            Timestamp date = rs.getTimestamp("date");
                            double balance = rs.getDouble("balance");
                            
                            transactions.add(new Transactions(balance,amount,description,date,transaction_type));
                        }
                    }
                }
            }catch(SQLException e){
                System.out.print("\nFailed in retrieving transaction history in ascending order amount:" + e.getMessage());
            }
            return transactions;
            }
}
    


class Transactions{
    private Timestamp date;
    private String description;
    private double amount;
    private double balance;
    private String transaction_type;
    
    public Transactions(double balance, double amount, String description, Timestamp date,String transaction_type){
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.balance = balance;
        this.transaction_type = transaction_type;
}
    public double getBalance() {
        return balance;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getTransactionType() {
        return transaction_type;
    }
    
    public String toCSVRow(){
        if(transaction_type.equals("debit")){
        return String.format("%s,%s,%.2f,,%.2f",date.toString(),description,amount,balance);
        }else{
        return String.format("%s,%s,,%.2f,%.2f",date.toString(),description,amount,balance );
        }
    }
    
    @Override
    public String toString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        
        if(transaction_type.equals("debit")){
        return String.format("\n%-15s%-40s%15.2f%15s%15.2f",dateFormat.format(date),description,amount,"",balance);
        }else
            return String.format("\n%-15s%-40s%15s%15.2f%15.2f",dateFormat.format(date),description,"",amount,balance);
    }
}

