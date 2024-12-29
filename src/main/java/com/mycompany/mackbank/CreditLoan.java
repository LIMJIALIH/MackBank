package com.mycompany.mackbank;

import java.text.DecimalFormat;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

class CreditLoan {
    
        DecimalFormat df = new DecimalFormat("#.00"); 
        
    private int user_id;
    private double principal_amount;
    private double annualInterestRate;
    private double year;
    private int frequencyChoice;
    
    public CreditLoan(int user_id){
        this.user_id = user_id;
    }
    
    public CreditLoan(int user_id , double principal_amount , double annualInterestRate, double year, int frequencyChoice){
        this.user_id = user_id;
        this.principal_amount = principal_amount;
        this.annualInterestRate = annualInterestRate;
        this.year = year;
        this.frequencyChoice = frequencyChoice;
    }
    
    public double calculateLoan(){
            int frequency = frequencyChoice == 1? 12:4;
            String frequencyName  = frequencyChoice == 1? "Monthly" : "Quarterly";
            double totalInstallments = year*frequency; //R = total Repayment Frequency
            double periodicInterestRate = (annualInterestRate/100)/frequency;
                
            double installmentAmount = (principal_amount*periodicInterestRate)/
                                           (1-Math.pow((1+periodicInterestRate),(-totalInstallments)));
                
            double totalRepayment = installmentAmount * totalInstallments;
            return totalRepayment;
    }
    
    public void OpenLoan(){
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String query = "INSERT INTO loansAccount(user_id,principle_amount,interest_rate,repayment_period,outstanding_balance,status,created_at,next_due_date) VALUES (?,?,?,?,?,'repaid',?,?)";
               try(PreparedStatement ps = connection.prepareStatement(query)){
                   double outstanding_balance = calculateLoan();
                   Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                   ps.setInt(1, user_id);
                   ps.setDouble(2, principal_amount);
                   ps.setDouble(3, annualInterestRate);
                   ps.setDouble(4, year);
                   ps.setDouble(5, outstanding_balance);
                   ps.setTimestamp(6, currentTime);
                   ps.setTimestamp(7, currentTime);
                   ps.executeUpdate();
               }
        }catch(SQLException e){
            System.out.print("\nFailed to apply loans:" + e.getMessage());
        }
    }
    
    public void displaySchedule() {
    int frequency = frequencyChoice == 1 ? 12 : 4; // Monthly or Quarterly
    double totalInstallments = year * frequency; // Total repayment frequency

    Timestamp startDateTimestamp = getDate(); // Loan start date
    if (startDateTimestamp == null) {
        System.out.print("\nFailed to retrieve the loan start date. Schedule cannot be displayed.");
        return;
    }

    LocalDate loanStartDate = startDateTimestamp.toLocalDateTime().toLocalDate();
    System.out.println("\nRepayment Schedule:");
    System.out.printf("%-15s%-25s%-20s%n", "Installment No.", "Installment Due Date", "Amount (RM)");
    System.out.println("-------------------------------------------------------------");

    int monthsBtwInstallments = 12 / frequency; // Months between installments
    LocalDate firstInstallmentDate = loanStartDate.plusMonths(monthsBtwInstallments); // First due date
    double periodicInterestRate = (annualInterestRate / 100) / frequency;
    double installmentAmount = (principal_amount * periodicInterestRate) /
        (1 - Math.pow((1 + periodicInterestRate), -totalInstallments));

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate currentInstallmentDate = firstInstallmentDate;

    for (int i = 1; i <= totalInstallments; i++) {
        System.out.printf("%-15d%-25s%-20s%n", i, currentInstallmentDate.format(dateFormatter), df.format(installmentAmount));
        currentInstallmentDate = currentInstallmentDate.plusMonths(monthsBtwInstallments); // Increment installment date
    }
}

    
    public double getLoan(){
        double totalLoan = 0 ;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String query = "SELECT outstanding_balance FROM loansAccount WHERE user_id = ?";
            try(PreparedStatement ps = connection.prepareStatement(query)){
                ps.setInt(1, user_id);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        totalLoan = rs.getDouble("outstanding_balance");
                    }else{
                        totalLoan = 0;
                    }
                }
            }
        }catch(SQLException e){
            System.out.print("\nFailed in retrieved the loan of the user:" + e.getMessage());
        }
        return totalLoan;
    }
    
    public String getStatus(){
        String status = "";
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String query = "SELECT status FROM loansAccount WHERE user_id = ?";
            try(PreparedStatement ps = connection.prepareStatement(query)){
                ps.setInt(1, user_id);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        status = rs.getString("status");
                    }
                }
            }
        }catch(SQLException e){
            System.out.print("\nFailed in retrieved the status of this user:" + e.getMessage());
        }
        return status;
    }
    
    public void repayment(double repayment){
        double totalLoan = getLoan();
        double newLoan = totalLoan-repayment;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String query = "UPDATE loansAccount SET outstanding_balance = ? WHERE user_id = ?";
            try(PreparedStatement ps = connection.prepareStatement(query)){
                ps.setDouble(1, newLoan);
                ps.setInt(2, user_id);
                ps.executeUpdate();
                if(newLoan <= 0.01){
                    System.out.print("\nYou have finish your repayment. You can apply for new loan for now.");
                    updateStatus();
                    deleteLoanRecord();
                }
            }
        }catch(SQLException e){
            System.out.print("\nFailed in make the repayment:" + e.getMessage());
        }
    }
    
    public Timestamp getDate(){
        Timestamp date = new Timestamp(System.currentTimeMillis());
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String query = "SELECT created_at FROM loansAccount WHERE user_id = ?";
            try(PreparedStatement ps = connection.prepareStatement(query)){
                ps.setInt(1, user_id);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        date = rs.getTimestamp("created_at");
                    }else{
                        System.out.print("\nThis user does not created the loan account yet!");
                    }
                }
            }
        }catch(SQLException e){
            System.out.print("\nFailed in retrieved the date:" + e.getMessage());
        }
    return date;
    }
    
    public void updateDueDate(){
        
        Timestamp beforeDueDate = getDate();
        LocalDateTime formattedBeforeDueDate = beforeDueDate.toLocalDateTime();
        
        LocalDateTime dueDateTime;
        if(year >= 1){
            dueDateTime = formattedBeforeDueDate.plusYears((long)year);
        } else {
            long months = (long)(year*12);
            dueDateTime = formattedBeforeDueDate.plusMonths(months);
        }
        Timestamp newDueDate = Timestamp.valueOf(dueDateTime);
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String query = "UPDATE loansAccount SET next_due_date = ? WHERE user_id = ?";
            try(PreparedStatement ps = connection.prepareStatement(query)){
                ps.setTimestamp(1, newDueDate);
                ps.setInt(2, user_id);
                ps.executeUpdate();
            }
        }catch(SQLException e){
            System.out.print("\nFailed to update the due date:" + e.getMessage());
        }
        
    }
    
    public String getNewDueDate(){
        Timestamp newDueDate = new Timestamp (System.currentTimeMillis());
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String query = "SELECT next_due_date FROM loansAccount WHERE user_id = ?";
            try(PreparedStatement ps = connection.prepareStatement(query)){
                ps.setInt(1, user_id);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        newDueDate = rs.getTimestamp("next_due_date");
                    }
                }
            }
        }catch(SQLException e){
            System.out.print("\nFailed to get the new due date:" + e.getMessage());
        }
        LocalDateTime notFormatted = newDueDate.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return notFormatted.format(formatter);
    }
    
    public void updateStatus(){
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String query = "UPDATE loansAccount SET status = 'active' WHERE user_id = ?";
            try(PreparedStatement ps = connection.prepareStatement(query)){
                ps.setInt(1, user_id);
                int rowsAffected = ps.executeUpdate();
                if(rowsAffected>0){
                    System.out.print("\nYour loan status has been renew!");
                }else{
                    System.out.print("\nFailed to update loan status.");
                }
            }
        }catch(SQLException e){
            System.out.print("\nFailed in retrieved the status of this user:" + e.getMessage());
        }
    }
    
    public void deleteLoanRecord(){
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDatabase", "root", "0194436683LjL!")){
            String deleteQuery = "DELETE FROM loansAccount WHERE user_id = ?";
            try(PreparedStatement ps = connection.prepareStatement(deleteQuery)){
                ps.setInt(1, user_id);
                ps.executeUpdate();
            }
        }catch(SQLException e){
            System.out.print("\nFailed in delete the user info:" + e.getMessage());
        }
    }
    
    public boolean isLoanOverdue() {
        String dueDateStr = getNewDueDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date dueDate;
        
        try {
            dueDate = sdf.parse(dueDateStr);
            java.util.Date currentDate = new java.util.Date();
            if (getStatus().equals("repaid") && currentDate.after(dueDate)) {
            return true;
        }
        } catch (ParseException ex) {
            Logger.getLogger(InterfaceMackBank.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
}