
package com.mycompany.mackbank;

import java.util.Scanner;
public class MackBank {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        int user_id;
        String username;
        int option;
        while(true){
            System.out.print("\n== WELCOME TO MACKBANK ==");
            System.out.print("\n1. Login");
            System.out.print("\n2. Register");
            System.out.print("\n>");
            option = sc.nextInt();
            while(option != 1 && option != 2){
                System.out.print("\nInvalid option. Please choose a valid option.");
                System.out.print("\n>");
                option = sc.nextInt();
            }
            sc.nextLine();
            if(option == 1){
                System.out.print("\n== Please enter your email and password ==");
                System.out.print("\nEmail : ");
                String email =sc.next();
                while(true){
                    if(email.matches("^[a-zA-Z0-9._%+-]+@(gmail\\.com|yahoo\\.com|icloud\\.com|hotmail\\.com)$")){
                       break;
                    }else{
                        System.out.print("\nInvalid Email, Please enter another email(Your email must contain @ and .com in order to become a valid email) : ");
                        email = sc.next();
                    }
                }
                
                System.out.print("\nPassword : ");
                String password = sc.next();
                //Check valid password
                while(true){
                    if(password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")){ //_underscore can check
                        break;
                    }else{
                        System.out.print("\nInvalid password, Please enter another password(Your password must have at least 8 characters, and contains both alphanumeric and special character):");
                        password = sc.next();
                    }
                }
                //Create login constructor
                LoginAndRegister login = new LoginAndRegister(email,password);
                user_id = login.Login(); //Store data in database and obtain the userId
                if(user_id == -1 || user_id == -2){
                    continue;
                }
                username = login.getName();
                
                break;
                
            }
            
            else if(option == 2 ){
                System.out.print("\n== Please fill in the form ==");
                System.out.print("\nName (Your name must only include alphanumeric): ");
                String name =sc.nextLine();
                //Check valid name
                while(true){
                if(name.matches("[a-zA-Z0-9 ]+")){
                    break;
                }else{
                    System.out.print("\nYou enter an invalid name,please enter another name(Your name must include alphanumeric only):");
                    name = sc.nextLine();
                }
                }
                
                sc.nextLine();
                
                System.out.print("\nEmail : ");
                String email = sc.next();
                boolean validEmail =false;
                //Check valid email
                while(validEmail ==false){
                    if(email.matches("^[a-zA-z0-9._%+-]+@(gmail\\.com|yahoo\\.com|icloud\\.com|hotmail\\.com)$")){
                       validEmail = true;
                    }else{
                        System.out.print("\nInvalid Email, Please enter another email(Your email must contain @ and .com in order to become a valid email) : ");
                        email = sc.next();
                    }
                }
                
                System.out.print("\nPassword : ");
                String password = sc.next();
                //Check valid password
                while(true){
                    if(password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")){
                        break;
                    }else{
                        System.out.print("\nInvalid password, Please enter another password(Your password must have at least 8 characters, and contains both alphanumeric and special character):");
                        password = sc.next();
                    }
                }
                //Create constructor
                LoginAndRegister register = new LoginAndRegister(name, email, password);
                register.Register(); //Store data in database
            }else{
                System.out.print("\nInvalid option! Please select again!");
            }
            System.out.print("\n1.Login");
            System.out.print("\n2. Register");
            System.out.print("\n>");
            option = sc.nextInt();
        }
        
        
        exit:{
            
        while(true){
            
        DebitAndCredit transaction = new DebitAndCredit(user_id);
        Savings savings = new Savings(user_id);
        CreditLoan loan = new CreditLoan(user_id);
        savings.transferSavingsToBalance();
        
        
        
        System.out.print("\n\n== Welcome, " + username + " ==");
        System.out.print("\nBalance: " + transaction.getBalance());
        System.out.print("\nSavings: " + savings.getSavings());
        System.out.print("\nLoan:" + loan.getLoan());
        if ( loan.getLoan() > 0 && loan.getStatus().equals("repaid")) {
        
        System.out.printf("\n%s, you have RM%.2f due before %s.\n", username, loan.getLoan(), loan.getNewDueDate());
        
        }
        
            System.out.print("\n== Transaction ==");
            System.out.print("\n1.Debit");
            System.out.print("\n2.Credit");
            System.out.print("\n3.History");
            System.out.print("\n4.Savings");
            System.out.print("\n5.Credit Loan");
            System.out.print("\n6.Deposit Interest Predictor");
            System.out.print("\n7.Logout");
            System.out.print("\n");
            System.out.print("\n>");
            option = sc.nextInt();
            
            switch(option){
                case 1:{
                    System.out.print("\n== Debit ==");
                    System.out.print("\nEnter amount: ");
                    double debit = sc.nextDouble();
                    while(true){
                        if(debit<=0){
                            System.out.print("\nAmount of debit must be positive!Please reenter another amount: ");
                            debit = sc.nextDouble();
                        }else if(debit>10000){
                            System.out.print("\nThe amount exceeds the debit limit 10000!Please enter reanother amount: ");
                            debit = sc.nextDouble();
                        }else{
                            break;
                        }
                    }
                    
                    sc.nextLine();
                    
                    System.out.print("\nEnter description: ");
                    String description = sc.nextLine();
                    
                    while (description.length() > 100) {
                      System.out.println("The description is too long. Please reenter description.");
                      System.out.print("Enter description: ");
                      description = sc.nextLine();
                    }
                    transaction.debit(debit, description);
                    break;
                }
                case 2:{
                    System.out.print("\n== Credit ==");
                    System.out.print("\nEnter amount:");
                    double credit = sc.nextDouble();
                    while(true){
                        if (credit <= 0) {
                            System.out.println("The amount of credit must be positive. Please reenter another amount.");
                            credit = sc.nextDouble();
                    }else if (credit > 2500) {
                            System.out.println("The amount exceeds the credit limit 2500. Please reenter another amount.");
                            credit = sc.nextDouble();
                    }else if (credit > transaction.getBalance()) {
                            System.out.println("The amount exceeds your balance. Please reenter another amount.");
                            credit = sc.nextDouble();
                    }else {
                    break;
                    }
                }
                    
                    sc.nextLine();
                    
                    System.out.print("Enter description: ");
                    String description = sc.nextLine();
                    while (description.length() > 100) {
                            System.out.println("The description is too long. Please reenter description.");
                            System.out.print("Enter description: ");
                            description = sc.nextLine();
                        }
                    transaction.credit(credit, description);
                    break;
                    }
                case 3:{
                    
                    System.out.print("\n1. Original History");
                    System.out.print("\n2. Filter the History");
                    System.out.print("\n3. Sorting the History");
                    System.out.print("\n4. Filtering and Sorting the History");
                    System.out.print("\n>");
                    int chooseHistory = sc.nextInt();
                    
                    while(chooseHistory<1 || chooseHistory>4){
                        System.out.print("\nInvalid choice, please select your option:");
                        chooseHistory = sc.nextInt();
                    }
                    
                    if(chooseHistory == 1){
                        History history = new History(user_id, chooseHistory);
                        history.displayHistory();
                        System.out.print("\nDo you wish to get a CSV file?(Y for yes and N for no):");
                        char csv = sc.next().charAt(0);
                        while(csv != 'Y' && csv != 'y' && csv != 'N' && csv != 'n'){
                            System.out.print("\nInvalid input, please enter either Y or N only:");
                            csv = sc.next().charAt(0);
                        }
                        if(csv == 'Y' || csv == 'y'){
                            sc.nextLine();
                            System.out.print("\nWhat name is the csv file:");
                            String name = sc.nextLine();
                            String csvName = name + ".csv";
                            history.formCSV(csvName);
                        }
                        
                    }
                    
                    else if(chooseHistory == 2){
                        System.out.print("\n1. Filter by Date Range");
                        System.out.print("\n2. Filter by Transaction type");
                        System.out.print("\n3. Filter by Amount Range");
                        System.out.print("\n>");
                        int filterChoice = sc.nextInt();
                        
                        while(filterChoice < 1 || filterChoice >3){
                            System.out.print("\nInvalid choice, please select your option:");
                            filterChoice = sc.nextInt();
                        }
                        if(filterChoice == 1){
                            System.out.print("\n1. Filter by last week");
                            System.out.print("\n2. Filter by last month");
                            System.out.print("\n3. Filter by last year");
                            System.out.print("\n>");
                            int filterDate = sc.nextInt();
                            
                            while(filterDate<1 || filterDate > 3){
                                System.out.print("\nInvalid choice, please select your option:");
                                filterDate = sc.nextInt();
                            }
                        History filterDateHistory = new History(user_id, chooseHistory, filterChoice, filterDate);
                        
                        filterDateHistory.displayHistory();
                        System.out.print("\nDo you wish to get a CSV file?(Y for yes and N for no):");
                        char csv = sc.next().charAt(0);
                        while(csv != 'Y' && csv != 'y' && csv != 'N' && csv != 'n'){
                            System.out.print("\nInvalid input, please enter either Y or N only:");
                            csv = sc.next().charAt(0);
                        }
                        if(csv == 'Y' || csv == 'y'){
                            sc.nextLine();
                            System.out.print("\nWhat name is the csv file:");
                            String name = sc.nextLine();
                            String csvName = name + ".csv";
                            filterDateHistory.formCSV(csvName);
                        }
                        
                        }
                        else if(filterChoice == 2){
                            System.out.print("\n1. Debit");
                            System.out.print("\n2. Credit");
                            System.out.print("\n>");
                            int filterType = sc.nextInt();
                            
                            while(filterType != 1 && filterType != 2){
                                System.out.print("\nInvalid choice, please select your option:");
                                filterType = sc.nextInt();
                            }
                            History filterTypeHistory = new History(user_id, chooseHistory , filterChoice, filterType);
                            filterTypeHistory.displayHistory();
                            
                            System.out.print("\nDo you wish to get a CSV file?(Y for yes and N for no):");
                            char csv = sc.next().charAt(0);
                        while(csv != 'Y' && csv != 'y' && csv != 'N' && csv != 'n'){
                            System.out.print("\nInvalid input, please enter either Y or N only:");
                            csv = sc.next().charAt(0);
                        }
                        if(csv == 'Y' || csv == 'y'){
                            sc.nextLine();
                            System.out.print("\nWhat name is the csv file:");
                            String name = sc.nextLine();
                            String csvName = name + ".csv";
                            filterTypeHistory.formCSV(csvName);
                        }
                        }
                        else if(filterChoice == 3){
                            System.out.print("\nEnter the amount range to filter");
                            System.out.print("\nInitial Amount: RM");
                            double initialAmount = sc.nextDouble();
                            System.out.print("\nEnd Amount: RM");
                            double endAmount = sc.nextDouble();
                            
                            while(initialAmount<0 || endAmount<0){
                                System.out.print("\nPlease make sure that Initial Amount and End Amount are positive.");
                                if(initialAmount < 0){
                                    System.out.print("\nInitial Amount: RM");
                                    initialAmount = sc.nextDouble();
                                }
                                if(endAmount < 0 ){
                                    System.out.print("\nEnd Amount : RM");
                                    endAmount = sc.nextDouble();
                                }
                            }
                            History filterAmountHistory = new History(user_id, chooseHistory, filterChoice, initialAmount,endAmount);
                            filterAmountHistory.displayHistory();
                            System.out.print("\nDo you wish to get a CSV file?(Y for yes and N for no):");
                        char csv = sc.next().charAt(0);
                        while(csv != 'Y' && csv != 'y' && csv != 'N' && csv != 'n'){
                            System.out.print("\nInvalid input, please enter either Y or N only:");
                            csv = sc.next().charAt(0);
                        }
                        if(csv == 'Y' || csv == 'y'){
                            sc.nextLine();
                            System.out.print("\nWhat name is the csv file:");
                            String name = sc.nextLine();
                            String csvName = name + ".csv";
                            filterAmountHistory.formCSV(csvName);
                        }
                        }
                    }
                    
                    else if(chooseHistory == 3){
                        System.out.print("\n1. Sort by Date (Ascending)");
                        System.out.print("\n2. Sort by Date (Decending)");
                        System.out.print("\n3. Sort by Amount (Ascending)");
                        System.out.print("\n4. Sort by Amount (Decending)");
                        System.out.print("\n>");
                        int sortChoice = sc.nextInt();
                        
                        while(sortChoice < 1 || sortChoice > 4){
                            System.out.print("\nInvalid choice, please select your option:");
                            sortChoice = sc.nextInt();
                        }
                        
                        History sortHistory = new History(user_id, chooseHistory, sortChoice);
                        sortHistory.displayHistory();
                        System.out.print("\nDo you wish to get a CSV file?(Y for yes and N for no):");
                        char csv = sc.next().charAt(0);
                        while(csv != 'Y' && csv != 'y' && csv != 'N' && csv != 'n'){
                            System.out.print("\nInvalid input, please enter either Y or N only:");
                            csv = sc.next().charAt(0);
                        }
                        if(csv == 'Y' || csv == 'y'){
                            sc.nextLine();
                            System.out.print("\nWhat name is the csv file:");
                            String name = sc.nextLine();
                            String csvName = name + ".csv";
                            sortHistory.formCSV(csvName);
                        }
                        
                    }
                    
                    else if(chooseHistory == 4){
                        int filterType = 0;
                        int sortType = 0;
                        double initialAmount = 0;
                        double endAmount = 0;
                        System.out.print("\n1. Filter by Date Range");
                        System.out.print("\n2. Filter by Transaction type");
                        System.out.print("\n3. Filter by Amount Range");
                        System.out.print("\n>");
                        int filterChoice = sc.nextInt();
                        
                        while(filterChoice < 1 && filterChoice >3){
                            System.out.print("\nInvalid choice, please select your option:");
                            filterChoice = sc.nextInt();
                        }
                        if(filterChoice == 1){
                            System.out.print("\n1. Filter by last week");
                            System.out.print("\n2. Filter by last month");
                            System.out.print("\n3. Filter by last year");
                            System.out.print("\n>");
                            filterType = sc.nextInt();
                            
                            while(filterType<1 && filterType > 3){
                                System.out.print("\nInvalid choice, please select your option:");
                                filterType = sc.nextInt();
                            }
                        }
                        else if(filterChoice == 2){
                            System.out.print("\n1. Debit");
                            System.out.print("\n2. Credit");
                            System.out.print("\n>");
                            filterType = sc.nextInt();
                            
                            while(filterType != 1 && filterType != 2){
                                System.out.print("\nInvalid choice, please select your option:");
                                filterType = sc.nextInt();
                            }
                        }
                        else if(filterChoice == 3){
                            System.out.print("\nEnter the amount range to filter");
                            System.out.print("\nInitial Amount: RM");
                            initialAmount = sc.nextDouble();
                            System.out.print("\nEnd Amount: RM");
                            endAmount = sc.nextDouble();
                            
                            while(initialAmount<0 || endAmount<0){
                                System.out.print("\nPlease make sure that Initial Amount and End Amount are positive.");
                                if(initialAmount < 0){
                                    System.out.print("\nInitial Amount: RM");
                                    initialAmount = sc.nextDouble();
                                }
                                if(endAmount < 0 ){
                                    System.out.print("\nEnd Amount : RM");
                                    endAmount = sc.nextDouble();
                                }
                            }
                        }
                        System.out.print("\n1. Sort by Date (Ascending)");
                        System.out.print("\n2. Sort by Date (Decending)");
                        System.out.print("\n3. Sort by Amount (Ascending)");
                        System.out.print("\n4. Sort by Amount (Decending)");
                        System.out.print("\n>");
                        sortType = sc.nextInt();
                        
                        while(sortType < 1 && sortType > 4){
                            System.out.print("\nInvalid choice, please select your option:");
                            sortType = sc.nextInt();
                        }
                        if(filterType != 3){
                            History filterSortHistory = new History(user_id, chooseHistory, filterChoice, filterType, sortType);
                            filterSortHistory.displayHistory();
                            
                            System.out.print("\nDo you wish to get a CSV file?(Y for yes and N for no):");
                        char csv = sc.next().charAt(0);
                        while(csv != 'Y' && csv != 'y' && csv != 'N' && csv != 'n'){
                            System.out.print("\nInvalid input, please enter either Y or N only:");
                            csv = sc.next().charAt(0);
                        }
                        if(csv == 'Y' || csv == 'y'){
                            sc.nextLine();
                            System.out.print("\nWhat name is the csv file:");
                            String name = sc.nextLine();
                            String csvName = name + ".csv";
                            filterSortHistory.formCSV(csvName);
                        }
                        }
                        
                        else{
                            History filterSortHistory = new History(user_id, chooseHistory, filterChoice, initialAmount, endAmount, sortType);
                            filterSortHistory.displayHistory();
                            System.out.print("\nDo you wish to get a CSV file?(Y for yes and N for no):");
                        char csv = sc.next().charAt(0);
                        while(csv != 'Y' && csv != 'y' && csv != 'N' && csv != 'n'){
                            System.out.print("\nInvalid input, please enter either Y or N only:");
                            csv = sc.next().charAt(0);
                        }
                        if(csv == 'Y' || csv == 'y'){
                            sc.nextLine();
                            System.out.print("\nWhat name is the csv file:");
                            String name = sc.nextLine();
                            String csvName = name + ".csv";
                            filterSortHistory.formCSV(csvName);
                        }
                        }
                        
                    }
                    break;
                }
                case 4:{
                    
                    System.out.println("== Savings ==");
                    if(savings.getStatus().equals("not activate")){
                        
                    while(true){
                        System.out.print("Are you sure you want to activate it? (Y/N) :");
                        char activation=sc.next().toUpperCase().charAt(0);
                        if(activation!='Y' && activation!='N'){
                        System.out.println("Please enter only Y or N.");
                        }else if(activation =='N'){
                        System.out.print("\nSavings activation canceled.");
                        break;
                        }else{
                        System.out.print("\nPlease enter the percentage you wish to deduct from the next debit:");
                        while(!sc.hasNextInt()){
                            System.out.print("\nInvalid input. Please enter a valid integer percentage.");
                            sc.next();
                        }
                        int percentage = sc.nextInt();
                        while(percentage <= 0 || percentage >=100){
                            System.out.print("\nInvalid percentage. Please enter a value between 1 and 100.");
                            percentage = sc.nextInt();
                        }
                        Savings detailSavings = new Savings (user_id,"activate",percentage);
                        detailSavings.openSavings();
                        System.out.print("\nSavings activated successfully!");
                        break;
                        }
                    }
                }
                    else{
                    
                    System.out.print("\nYour savings account is already activated!");
                    System.out.print("Please enter the percentage you wish to deduct from the next debit:");
                    while(!sc.hasNextInt()){
                            System.out.print("\nInvalid input. Please enter a valid integer percentage.");
                            sc.next();
                        }
                    int percentage = sc.nextInt();
                    while (percentage <=0 || percentage>=100){
                            System.out.print("\nInvalid input of percentage, the savings percentage must be between 1 and 100.");
                            percentage = sc.nextInt();
                        }
                    savings.setPercentage(percentage);
                        System.out.print("\nSavings percentage updated successfully!");
                    }
                    break;
                }
                    
                case 5: {
                    
                System.out.println("1. Apply credit loans");
                System.out.println("2. Repay credit loan");
                System.out.print("\n> ");
                int CLchoice = sc.nextInt();
                
                while (CLchoice != 1 && CLchoice != 2) {
                System.out.print("\nInvalid choice. Please input another choice: ");
                System.out.print("\n> ");
                CLchoice = sc.nextInt();
                }
                
                while(CLchoice == 1 && loan.getStatus().equals("repaid")){
                    System.out.print("\nYou have apply loan before. Please finish the repayment first.");
                    CLchoice = 3;
                    break;
                    
                }
            
            switch (CLchoice) {
                case 1: {
                System.out.print("\nPrincipal Amount: ");
                double principalAmount = sc.nextDouble();

            while (principalAmount <= 0) {
                System.out.print("\nPrincipal Amount cannot be 0.");
                System.out.print("\nPlease enter another principal amount: ");
                principalAmount = sc.nextDouble();
            }

            System.out.print("Interest Rate in %: ");
            double annualInterestRate = sc.nextDouble();
            while (annualInterestRate < 0 || annualInterestRate > 100) {
                System.out.print("\nInterest Rate cannot be larger than 100 or smaller than 0.");
                System.out.print("\nPlease enter another interest rate: ");
                annualInterestRate = sc.nextDouble();
            }

            System.out.print("Repayment Period in years: ");
            double year = sc.nextDouble();
            while (year <= 0) {
                System.out.print("\nRepayment period cannot be 0 or negative.");
                System.out.print("\nPlease enter another repayment period: ");
                year = sc.nextDouble();
            }

            System.out.print("Choose repayment frequency (1 for monthly, 2 for quarterly): ");
            int frequencyChoice = sc.nextInt();
            while (frequencyChoice != 1 && frequencyChoice != 2) {
                System.out.print("\nRepayment frequency must be 1 or 2 only.");
                System.out.print("\nPlease enter another repayment frequency: ");
                frequencyChoice = sc.nextInt();
            }
            CreditLoan detailLoan = new CreditLoan(user_id,principalAmount,annualInterestRate,year,frequencyChoice);
            detailLoan.OpenLoan();
            detailLoan.updateDueDate();
            detailLoan.displaySchedule();
            
            break;
        }

        case 2: {
            if(loan.getStatus().equals("active")){
                System.out.print("\nYou does not make loan before or you have make full repayment before.");
                break;
            }
            if(loan.getStatus().equals("repaid")){
            System.out.print("\nYou have RM" + loan.getLoan() + " need to pay before " + loan.getNewDueDate());
            
            System.out.print("\nPlease enter the amount that you want to repay:RM");
            double repayment = sc.nextDouble();
            
            while(repayment>loan.getLoan()){
                System.out.print("\nYou cannot pay more than the loan. Please enter another amount that you want to pay:\nRM");
                repayment = sc.nextDouble();
            }
            
            loan.repayment(repayment);
            break;}
            else{
                break;
            }
            }
        default: break;
        }
            break;
    }
                case 6:{
                    System.out.print("Please enter deposit amount: RM ");
                        double deposit = sc.nextDouble();
                        
                        while(deposit<=0){
                            System.out.print("\nDeposit cannot be negative. Please enter a positive deposit:");
                            deposit = sc.nextDouble();
                        }   
                    System.out.println("\nPlease select a bank: ");
                    
                    System.out.printf("%-3s %-25s %-5s\n", "No.", "Bank", "Interest Rate");
                    System.out.printf("%-3s %-25s %-5s\n", "1.", "RHB", "2.6%");
                    System.out.printf("%-3s %-25s %-5s\n", "2.", "MayBank", "2.5%");
                    System.out.printf("%-3s %-25s %-5s\n", "3.", "Hong Leong", "2.3%");
                    System.out.printf("%-3s %-25s %-5s\n", "4.", "Aliance", "2.85%");
                    System.out.printf("%-3s %-25s %-5s\n", "5.", "Ambank", "2.55%");
                    System.out.printf("%-3s %-25s %-5s\n", "6.", "Standard Chartered", "2.65%");
                    
                    System.out.print("Choice: ");
                    int bankChoice =  sc.nextInt();
                    
                    while(!(bankChoice >= 1 && bankChoice <= 6)){
                        System.out.print("\nInvalid bank choice. Please make another choice:");
                        bankChoice = sc.nextInt();
                    }
                    
                    System.out.println("\nPlease select a period");
                    System.out.println("1. Daily");
                    System.out.println("2. Monthly");
                    System.out.println("3. Annually");
                    System.out.print("Choice: ");
                    int periodChoice = sc.nextInt();
                    
                    while(periodChoice<1 && periodChoice >3){
                        System.out.print("\nYou select an invalid period. Please select between 1,2 and 3 only:");
                        periodChoice = sc.nextInt();
                    }
                    DepositInterestPredictor predictor = new DepositInterestPredictor(deposit,bankChoice,periodChoice);
                    predictor.calculateInterest();
                    break;
                }
                case 7:{
                    break exit;
                }
                default:{
                    System.out.print("\nYou have enter an invalid option! Please enter enter another option.");
                    break;
                }
                }
            }
        }
        System.out.print("\nThank you for using MackBank!!!"); 
    }
}
    