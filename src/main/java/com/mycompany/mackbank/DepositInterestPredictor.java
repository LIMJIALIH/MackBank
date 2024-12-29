
package com.mycompany.mackbank;

import java.time.Year;
class DepositInterestPredictor {
    private double deposit;
    private int bankChoice;
    private int periodChoice;
    private int currentYear = Year.now().getValue();
        public DepositInterestPredictor(double deposit, int bankChoice, int periodChoice){
            this.deposit = deposit;
            this.bankChoice = bankChoice;
            this.periodChoice = periodChoice;
        }
        
        public double calculateInterest(){
        double interestRate = 0;
        switch(bankChoice){
            case 1 -> interestRate = 2.6;
            case 2 -> interestRate = 2.5;
            case 3 -> interestRate = 2.3;
            case 4 -> interestRate = 2.85;
            case 5 -> interestRate = 2.55;
            case 6 -> interestRate = 2.65;
            default -> System.out.println("Invalid choice, please try again");
        }
        
        int divisor = 0;
        String period ="";
        switch(periodChoice){
            case 1:
                if((currentYear%4 == 0 && currentYear %100 != 0) || currentYear%400 == 0){
                    divisor = 366;
                }//leap year
                else{
                divisor = 365; 
                }
                period = "Daily";
                break;
            case 2:
                divisor = 12;
                period = "Monthly";
                break;
            case 3:
                divisor = 1;
                period = "Annual";
                break;
            default:
                System.out.println("Invalid choice, please try again");
        }
        
        double predictedInterest = (deposit * interestRate/100)/divisor;
        System.out.printf("\n"+period + " Interest: RM  %.2f" , predictedInterest);
        System.out.println();
        return predictedInterest; 
    }
        
}
    
