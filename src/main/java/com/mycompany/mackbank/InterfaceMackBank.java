package com.mycompany.mackbank;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class InterfaceMackBank extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel cards;
    private int userId = -1;
    private String username = "";
    
    // Components for login/register panels
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField regEmailField;
    private JPasswordField regPasswordField;
    
    // Components for main panel
    private JLabel balanceLabel;
    private JLabel savingsLabel;
    private JLabel loanLabel;
    
    public InterfaceMackBank() {
        setTitle("MackBank");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        
        // Create panels
        createWelcomePanel();
        createLoginPanel();
        createRegisterPanel();
        createMainPanel();
        
        add(cards);
    }
    
    private void createWelcomePanel() {
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel titleLabel = new JLabel("Welcome to MackBank", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        
        loginButton.addActionListener(e -> cardLayout.show(cards, "login"));
        registerButton.addActionListener(e -> cardLayout.show(cards, "register"));
        
        welcomePanel.add(titleLabel, gbc);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 20)), gbc);
        welcomePanel.add(loginButton, gbc);
        welcomePanel.add(registerButton, gbc);
        
        cards.add(welcomePanel, "welcome");
    }
    
    private void createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");
        
        loginButton.addActionListener(e -> handleLogin());
        backButton.addActionListener(e -> cardLayout.show(cards, "welcome"));
        
        loginPanel.add(titleLabel, gbc);
        loginPanel.add(new JLabel("Email:"), gbc);
        loginPanel.add(emailField, gbc);
        loginPanel.add(new JLabel("Password:"), gbc);
        loginPanel.add(passwordField, gbc);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
        loginPanel.add(loginButton, gbc);
        loginPanel.add(backButton, gbc);
        
        cards.add(loginPanel, "login");
    }
    
    private void createRegisterPanel() {
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JLabel titleLabel = new JLabel("Register", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        nameField = new JTextField(20);
        JTextField regEmailField = new JTextField(20);
        JPasswordField regPasswordField = new JPasswordField(20);
        
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");
        
        registerButton.addActionListener(e -> handleRegister());
        backButton.addActionListener(e -> cardLayout.show(cards, "welcome"));
        
        registerPanel.add(titleLabel, gbc);
        registerPanel.add(new JLabel("Name:"), gbc);
        registerPanel.add(nameField, gbc);
        registerPanel.add(new JLabel("Email:"), gbc);
        registerPanel.add(regEmailField, gbc);
        registerPanel.add(new JLabel("Password:"), gbc);
        registerPanel.add(regPasswordField, gbc);
        registerPanel.add(Box.createRigidArea(new Dimension(0, 10)), gbc);
        registerPanel.add(registerButton, gbc);
        registerPanel.add(backButton, gbc);
        
        cards.add(registerPanel, "register");
    }
    
    private void createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Top panel with user info
        JPanel topPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        JLabel welcomeLabel = new JLabel();     ////////////
        balanceLabel = new JLabel("Balance: RM 0.00");
        savingsLabel = new JLabel("Savings: RM 0.00");
        loanLabel = new JLabel("Loan: RM 0.00");
        
        topPanel.add(welcomeLabel);
        topPanel.add(balanceLabel);
        topPanel.add(savingsLabel);
        topPanel.add(loanLabel);
        
        // Center panel with buttons
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JButton debitButton = new JButton("Debit");
        JButton creditButton = new JButton("Credit");
        JButton historyButton = new JButton("History");
        JButton savingsButton = new JButton("Savings");
        JButton loanButton = new JButton("Credit Loan");
        JButton predictorButton = new JButton("Deposit Interest Predictor");
        
        centerPanel.add(debitButton);
        centerPanel.add(creditButton);
        centerPanel.add(historyButton);
        centerPanel.add(savingsButton);
        centerPanel.add(loanButton);
        centerPanel.add(predictorButton);
        
        // Bottom panel with logout button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> handleLogout());
        bottomPanel.add(logoutButton);
        
        // Add action listeners
        debitButton.addActionListener(e -> handleDebit());
        creditButton.addActionListener(e -> handleCredit());
        historyButton.addActionListener(e -> handleHistory());
        savingsButton.addActionListener(e -> handleSavings());
        loanButton.addActionListener(e -> handleLoan());
        predictorButton.addActionListener(e -> handlePredictor());
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        cards.add(mainPanel, "main");
    }
    
    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        
        // Email validation
        if (!email.matches("^[a-zA-z0-9._%+-]+@(gmail\\.com|yahoo\\.com|icloud\\.com|hotmail\\.com)$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Password validation
        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            JOptionPane.showMessageDialog(this, "Invalid password format!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LoginAndRegister login = new LoginAndRegister(email, password);
        userId = login.Login();
        
        if (userId == -1 || userId == -2) {
            JOptionPane.showMessageDialog(this, "Login failed!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Savings savings = new Savings(userId);
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream newOut = new PrintStream(baos);
        System.setOut(newOut);
    
        // Call the transfer method
        savings.transferSavingsToBalance();
    
        // Restore original System.out and get the captured output
        System.out.flush();
        System.setOut(originalOut);
        String output = baos.toString();
    
        // Check if transfer occurred by looking for success message
        if (output.contains("Savings has been transfer back to balance successfully!")) {
        JOptionPane.showMessageDialog(this,
            "Your savings have been automatically transferred back to your balance.",
            "Savings Transfer Notice",
            JOptionPane.INFORMATION_MESSAGE);
        }
        username = login.getName();
        updateMainPanel();
        
        // Check for loan after successful login
        CreditLoan loan = new CreditLoan(userId);
        if (loan.getLoan() > 0 && loan.getStatus().equals("repaid")) {
            // Create a custom dialog for the loan reminder
            JDialog reminderDialog = new JDialog(this, "Loan Payment Reminder", true);
            reminderDialog.setSize(400, 200);
            reminderDialog.setLocationRelativeTo(this);
            
            JPanel reminderPanel = new JPanel(new BorderLayout(10, 10));
            reminderPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Create a formatted reminder message
            JLabel messageLabel = new JLabel(String.format(
                "<html><div style='text-align: center;'>" +
                "<b>Important Reminder</b><br><br>" +
                "Dear %s,<br>" +
                "You have a loan payment of RM%.2f due before %s.<br><br>" +
                "Please ensure timely payment to avoid any late fees.</div></html>",
                username, loan.getLoan(), loan.getNewDueDate()
            ));
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton payNowButton = new JButton("Pay Now");
            JButton remindLaterButton = new JButton("Remind Me Later");
            
            payNowButton.addActionListener(e -> {
                reminderDialog.dispose();
                handleLoan(); // Open the loan payment interface
            });
            
            remindLaterButton.addActionListener(e -> {
                reminderDialog.dispose();
            });
            
            buttonPanel.add(payNowButton);
            buttonPanel.add(remindLaterButton);
            
            reminderPanel.add(messageLabel, BorderLayout.CENTER);
            reminderPanel.add(buttonPanel, BorderLayout.SOUTH);
            
            reminderDialog.add(reminderPanel);
            reminderDialog.setVisible(true);
        }
        cardLayout.show(cards, "main");
    }
    
private void handleRegister() {
    String name = nameField.getText();
    String email = regEmailField.getText();
    String password = new String(regPasswordField.getPassword());
    
    // Validation
    if (!name.matches("[a-zA-z0-9 ]+")) {
        JOptionPane.showMessageDialog(this, "Invalid name format!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Password validation
    if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$")) {
        JOptionPane.showMessageDialog(this, 
            "Password must be at least 8 characters long and contain at least one letter, one number, and one special character (@$!%*?&)", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    try {
        LoginAndRegister register = new LoginAndRegister(name, email, password);
        register.Register();
        
        // Clear the fields and switch to login panel only if no exception was thrown
        // and no error message was shown (registration was successful)
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        cardLayout.show(cards, "login");
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "An unexpected error occurred during registration.", 
            "Error", JOptionPane.ERROR_MESSAGE);
        System.out.println("Registration error: " + e.getMessage());
    }
}
    
    private void handleLogout() {
        userId = -1;
        username = "";
        
        emailField.setText("");
        passwordField.setText("");
        nameField.setText("");
        
        cardLayout.show(cards, "welcome");
    }
    
    private void updateMainPanel() {
        DebitAndCredit transaction = new DebitAndCredit(userId);
        Savings savings = new Savings(userId);
        CreditLoan loan = new CreditLoan(userId);
        
        JPanel mainPanel = (JPanel)cards.getComponent(3);
        JPanel topPanel = (JPanel)mainPanel.getComponent(0);
        JLabel welcomeLabel = (JLabel)topPanel.getComponent(0);
        
        welcomeLabel.setText("Welcome, " + username);
        
        balanceLabel.setText(String.format("Balance: RM %.2f", transaction.getBalance()));
        savingsLabel.setText(String.format("Savings: RM %.2f", savings.getSavings()));
        loanLabel.setText(String.format("Loan: RM %.2f", loan.getLoan()));
    }
    
    
    
    private void handleDebit() {
        CreditLoan loan = new CreditLoan(userId);
        
        if (loan.isLoanOverdue()) {
            JOptionPane.showMessageDialog(this, 
                "You have an overdue loan payment. Please settle your loan before making any debit transactions.", 
                "Transaction Blocked", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String amountStr = JOptionPane.showInputDialog(this, "Enter debit amount:");
        if (amountStr == null) return;
        
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0 || amount > 10000) {
                JOptionPane.showMessageDialog(this, "Invalid amount! Must be between 0 and 10000.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String description = JOptionPane.showInputDialog(this, "Enter description:");
            if (description == null || description.length() > 100) {
                JOptionPane.showMessageDialog(this, "Invalid description!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            DebitAndCredit transaction = new DebitAndCredit(userId);
            transaction.debit(amount, description);
            updateMainPanel();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleCredit() {
        CreditLoan loan = new CreditLoan(userId);
        
        if (loan.isLoanOverdue()) {
            JOptionPane.showMessageDialog(this, 
                "You have an overdue loan payment. Please settle your loan before making any credit transactions.", 
                "Transaction Blocked", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String amountStr = JOptionPane.showInputDialog(this, "Enter credit amount:");
        if (amountStr == null) return;
        
        try {
            double amount = Double.parseDouble(amountStr);
            DebitAndCredit transaction = new DebitAndCredit(userId);
            
            if (amount <= 0 || amount > 2500 || amount > transaction.getBalance()) {
                JOptionPane.showMessageDialog(this, "Invalid amount!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String description = JOptionPane.showInputDialog(this, "Enter description:");
            if (description == null || description.length() > 100) {
                JOptionPane.showMessageDialog(this, "Invalid description!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            transaction.credit(amount, description);
            updateMainPanel();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleHistory() {
     // Create history dialog
    JDialog historyDialog = new JDialog(this, "Transaction History", true);
    historyDialog.setSize(600, 400);
    historyDialog.setLocationRelativeTo(this);
    
    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    // Display area for history
    JTextArea historyDisplay = new JTextArea();
    historyDisplay.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(historyDisplay);
    
    // Options panel using BoxLayout for vertical stacking
    JPanel optionsPanel = new JPanel();
    optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
    
    // Main options
    JLabel mainLabel = new JLabel("Select History Option:");
    String[] mainOptions = {
        "1. Original History",
        "2. Filter the History",
        "3. Sorting the History",
        "4. Filtering and Sorting the History"
    };
    JComboBox<String> mainChoices = new JComboBox<>(mainOptions);
    
    // Filter options
    JLabel filterLabel = new JLabel("Select Filter Option:");
    String[] filterOptions = {
        "1. Filter by Date Range",
        "2. Filter by Transaction type",
        "3. Filter by Amount Range"
    };
    JComboBox<String> filterChoices = new JComboBox<>(filterOptions);
    
    // Date range options
    JLabel dateLabel = new JLabel("Select Date Range:");
    String[] dateRangeOptions = {
        "1. Filter by last week",
        "2. Filter by last month",
        "3. Filter by last year"
    };
    JComboBox<String> dateRangeChoices = new JComboBox<>(dateRangeOptions);
    
    // Transaction type options
    JLabel typeLabel = new JLabel("Select Transaction Type:");
    String[] transactionTypeOptions = {
        "1. Debit",
        "2. Credit"
    };
    JComboBox<String> transactionTypeChoices = new JComboBox<>(transactionTypeOptions);
    
    // Amount range panel
    JPanel amountPanel = new JPanel(new GridLayout(2, 2, 5, 5));
    JTextField initialAmountField = new JTextField(10);
    JTextField endAmountField = new JTextField(10);
    amountPanel.add(new JLabel("Initial Amount: RM"));
    amountPanel.add(initialAmountField);
    amountPanel.add(new JLabel("End Amount: RM"));
    amountPanel.add(endAmountField);
    
    // Sort options
    JLabel sortLabel = new JLabel("Select Sort Option:");
    String[] sortOptions = {
        "1. Sort by Date (Ascending)",
        "2. Sort by Date (Descending)",
        "3. Sort by Amount (Ascending)",
        "4. Sort by Amount (Descending)"
    };
    JComboBox<String> sortChoices = new JComboBox<>(sortOptions);
    
    // Initially hide all secondary options
    filterLabel.setVisible(false);
    filterChoices.setVisible(false);
    dateLabel.setVisible(false);
    dateRangeChoices.setVisible(false);
    typeLabel.setVisible(false);
    transactionTypeChoices.setVisible(false);
    amountPanel.setVisible(false);
    sortLabel.setVisible(false);
    sortChoices.setVisible(false);
    
    // Add components to options panel
    optionsPanel.add(mainLabel);
    optionsPanel.add(Box.createVerticalStrut(5));
    optionsPanel.add(mainChoices);
    optionsPanel.add(Box.createVerticalStrut(10));
    optionsPanel.add(filterLabel);
    optionsPanel.add(Box.createVerticalStrut(5));
    optionsPanel.add(filterChoices);
    optionsPanel.add(Box.createVerticalStrut(10));
    optionsPanel.add(dateLabel);
    optionsPanel.add(Box.createVerticalStrut(5));
    optionsPanel.add(dateRangeChoices);
    optionsPanel.add(Box.createVerticalStrut(10));
    optionsPanel.add(typeLabel);
    optionsPanel.add(Box.createVerticalStrut(5));
    optionsPanel.add(transactionTypeChoices);
    optionsPanel.add(Box.createVerticalStrut(10));
    optionsPanel.add(amountPanel);
    optionsPanel.add(Box.createVerticalStrut(10));
    optionsPanel.add(sortLabel);
    optionsPanel.add(Box.createVerticalStrut(5));
    optionsPanel.add(sortChoices);
    
    // Button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    JButton showButton = new JButton("Show History");
    JButton exportButton = new JButton("Export to CSV");
    JButton closeButton = new JButton("Close");
    buttonPanel.add(showButton);
    buttonPanel.add(exportButton);
    buttonPanel.add(closeButton);
    
    // Main choices action listener
    mainChoices.addActionListener(e -> {
        int choice = mainChoices.getSelectedIndex() + 1;
        
        // Reset visibility of all components
        filterLabel.setVisible(false);
        filterChoices.setVisible(false);
        dateLabel.setVisible(false);
        dateRangeChoices.setVisible(false);
        typeLabel.setVisible(false);
        transactionTypeChoices.setVisible(false);
        amountPanel.setVisible(false);
        sortLabel.setVisible(false);
        sortChoices.setVisible(false);
        
        // Show relevant components based on choice
        if (choice == 2 || choice == 4) {
            filterLabel.setVisible(true);
            filterChoices.setVisible(true);
        }
        if (choice == 3 || choice == 4) {
            sortLabel.setVisible(true);
            sortChoices.setVisible(true);
        }
    });
    
    // Filter choices action listener
    filterChoices.addActionListener(e -> {
        int choice = filterChoices.getSelectedIndex() + 1;
        
        // Hide all filter-specific components
        dateLabel.setVisible(false);
        dateRangeChoices.setVisible(false);
        typeLabel.setVisible(false);
        transactionTypeChoices.setVisible(false);
        amountPanel.setVisible(false);
        
        // Show relevant components based on choice
        switch (choice) {
            case 1:
                dateLabel.setVisible(true);
                dateRangeChoices.setVisible(true);
                break;
            case 2:
                typeLabel.setVisible(true);
                transactionTypeChoices.setVisible(true);
                break;
            case 3:
                amountPanel.setVisible(true);
                break;
        }
    });
    
    // Show button action listener
    showButton.addActionListener(e -> {
        History history = null;
    int mainChoice = mainChoices.getSelectedIndex() + 1;
    
    PrintStream old = System.out;
    try {
        // Redirect System.out to capture the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        if (mainChoice == 1) {
            history = new History(userId, mainChoice);
        } else if (mainChoice == 2) {
            int filterChoice = filterChoices.getSelectedIndex() + 1;
            if (filterChoice == 1) {
                history = new History(userId, mainChoice, filterChoice, 
                    dateRangeChoices.getSelectedIndex() + 1);
            } else if (filterChoice == 2) {
                history = new History(userId, mainChoice, filterChoice,
                    transactionTypeChoices.getSelectedIndex() + 1);
            } else if (filterChoice == 3) {
                try {
                    double initialAmount = Double.parseDouble(initialAmountField.getText());
                    double endAmount = Double.parseDouble(endAmountField.getText());
                    if (initialAmount < 0 || endAmount < 0) {
                        System.setOut(old);  // Restore original System.out
                        JOptionPane.showMessageDialog(historyDialog,
                            "Amounts must be positive!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    history = new History(userId, mainChoice, filterChoice, 
                        initialAmount, endAmount);
                } catch (NumberFormatException ex) {
                    System.setOut(old);  // Restore original System.out
                    JOptionPane.showMessageDialog(historyDialog,
                        "Invalid amount format!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        } else if (mainChoice == 3) {
            history = new History(userId, mainChoice, 
                sortChoices.getSelectedIndex() + 1);
        } else if (mainChoice == 4) {
            int filterChoice = filterChoices.getSelectedIndex() + 1;
            int sortType = sortChoices.getSelectedIndex() + 1;
            
            if (filterChoice == 3) {
                try {
                    double initialAmount = Double.parseDouble(initialAmountField.getText());
                    double endAmount = Double.parseDouble(endAmountField.getText());
                    if (initialAmount < 0 || endAmount < 0) {
                        System.setOut(old);  // Restore original System.out
                        JOptionPane.showMessageDialog(historyDialog,
                            "Amounts must be positive!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    history = new History(userId, mainChoice, filterChoice,
                        initialAmount, endAmount, sortType);
                } catch (NumberFormatException ex) {
                    System.setOut(old);  // Restore original System.out
                    JOptionPane.showMessageDialog(historyDialog,
                        "Invalid amount format!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                if (filterChoice == 1) {
                    history = new History(userId, mainChoice, filterChoice,
                        dateRangeChoices.getSelectedIndex() + 1, sortType);
                } else if (filterChoice == 2) {
                    history = new History(userId, mainChoice, filterChoice,
                        transactionTypeChoices.getSelectedIndex() + 1, sortType);
                }
            }
        }
        
        if (history != null) {
            history.displayHistory();  // Call displayHistory() instead of toString()
            
            // Restore the original System.out and get the captured output
            System.out.flush();
            System.setOut(old);
            
            // Set the captured output to the text area
            historyDisplay.setText(baos.toString());
        }
    } catch (Exception ex) {
        // Make sure to restore System.out in case of exception
        System.setOut(old);
        JOptionPane.showMessageDialog(historyDialog,
            "Error processing history: " + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
});
    
    // Export button action listener
    exportButton.addActionListener(e -> {
        String fileName = JOptionPane.showInputDialog(historyDialog, "Enter CSV file name:");
        if (fileName != null && !fileName.trim().isEmpty()) {
            try {
                History history = null;
                int mainChoice = mainChoices.getSelectedIndex() + 1;
                
                // Use the same logic as show button to create History object
                if (mainChoice == 1) {
                    history = new History(userId, mainChoice);
                } else if (mainChoice == 2) {
                    int filterChoice = filterChoices.getSelectedIndex() + 1;
                    if (filterChoice == 1) {
                        history = new History(userId, mainChoice, filterChoice, 
                            dateRangeChoices.getSelectedIndex() + 1);
                    } else if (filterChoice == 2) {
                        history = new History(userId, mainChoice, filterChoice,
                            transactionTypeChoices.getSelectedIndex() + 1);
                    } else if (filterChoice == 3) {
                        double initialAmount = Double.parseDouble(initialAmountField.getText());
                        double endAmount = Double.parseDouble(endAmountField.getText());
                        if (initialAmount < 0 || endAmount < 0) {
                            JOptionPane.showMessageDialog(historyDialog,
                                "Amounts must be positive!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        history = new History(userId, mainChoice, filterChoice, 
                            initialAmount, endAmount);
                    }
                } else if (mainChoice == 3) {
                    history = new History(userId, mainChoice, 
                        sortChoices.getSelectedIndex() + 1);
                } else if (mainChoice == 4) {
                    int filterChoice = filterChoices.getSelectedIndex() + 1;
                    int sortType = sortChoices.getSelectedIndex() + 1;
                    
                    if (filterChoice == 3) {
                        double initialAmount = Double.parseDouble(initialAmountField.getText());
                        double endAmount = Double.parseDouble(endAmountField.getText());
                        if (initialAmount < 0 || endAmount < 0) {
                            JOptionPane.showMessageDialog(historyDialog,
                                "Amounts must be positive!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        history = new History(userId, mainChoice, filterChoice,
                            initialAmount, endAmount, sortType);
                    } else {
                        if (filterChoice == 1) {
                            history = new History(userId, mainChoice, filterChoice,
                                dateRangeChoices.getSelectedIndex() + 1, sortType);
                        } else if (filterChoice == 2) {
                            history = new History(userId, mainChoice, filterChoice,
                                transactionTypeChoices.getSelectedIndex() + 1, sortType);
                        }
                    }
                }
                
                if (history != null) {
                    history.formCSV(fileName + ".csv");
                    JOptionPane.showMessageDialog(historyDialog, 
                        "CSV file created successfully!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(historyDialog,
                    "Error creating CSV file: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    });
    
    // Close button action listener
    closeButton.addActionListener(e -> historyDialog.dispose());
    
    // Add all panels to main panel
    mainPanel.add(optionsPanel, BorderLayout.NORTH);
    mainPanel.add(scrollPane, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
    historyDialog.add(mainPanel);
    historyDialog.setVisible(true);
    }
    
    private void handleSavings() {
        Savings savings = new Savings(userId);
        
        if (savings.getStatus().equals("not activate")) {
            int choice = JOptionPane.showConfirmDialog(this, 
                "Would you like to activate savings?", 
                "Activate Savings", 
                JOptionPane.YES_NO_OPTION);
                
            if (choice == JOptionPane.YES_OPTION) {
                String percentageStr = JOptionPane.showInputDialog(this, 
                    "Enter percentage to deduct from next debit (1-99):");
                    
                if (percentageStr != null) {
                    try {
                        int percentage = Integer.parseInt(percentageStr);
                        if (percentage > 0 && percentage < 100) {
                            Savings detailSavings = new Savings(userId, "activate", percentage);
                            detailSavings.openSavings();
                            JOptionPane.showMessageDialog(this, 
                                "Savings activated successfully!");
                            updateMainPanel();
                        } else {
                            JOptionPane.showMessageDialog(this, 
                                "Invalid percentage! Must be between 1 and 99.", 
                                "Error", 
                                JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, 
                            "Invalid input! Please enter a valid number.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            String percentageStr = JOptionPane.showInputDialog(this, 
                "Enter new savings percentage (1-99):");
                
            if (percentageStr != null) {
                try {
                    int percentage = Integer.parseInt(percentageStr);
                    if (percentage > 0 && percentage < 100) {
                        savings.setPercentage(percentage);
                        JOptionPane.showMessageDialog(this, 
                            "Savings percentage updated successfully!");
                        updateMainPanel();
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Invalid percentage! Must be between 1 and 99.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, 
                        "Invalid input! Please enter a valid number.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void handleLoan() {
    CreditLoan loan = new CreditLoan(userId);
    
    String[] options = {"Apply Credit Loan", "Repay Credit Loan"};  // Removed View Schedule option
    int choice = JOptionPane.showOptionDialog(this, 
        "Select an option:", 
        "Credit Loan", 
        JOptionPane.DEFAULT_OPTION, 
        JOptionPane.QUESTION_MESSAGE, 
        null, 
        options, 
        options[0]);
        
    if (choice == 0) { // Apply Credit Loan
        if (loan.getStatus().equals("repaid")) {
            JOptionPane.showMessageDialog(this, 
                "You have an existing loan. Please finish repayment first.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get principal amount with validation
        double principal = 0;
        while (true) {
            String principalStr = JOptionPane.showInputDialog(this, "Enter principal amount:");
            if (principalStr == null) return; // User cancelled
            
            try {
                principal = Double.parseDouble(principalStr);
                if (principal <= 0) {
                    JOptionPane.showMessageDialog(this,
                        "Principal amount cannot be 0 or negative.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Invalid number format!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // Get interest rate with validation
        double interest = 0;
        while (true) {
            String interestStr = JOptionPane.showInputDialog(this, "Enter interest rate (%):");
            if (interestStr == null) return; // User cancelled
            
            try {
                interest = Double.parseDouble(interestStr);
                if (interest < 0 || interest > 100) {
                    JOptionPane.showMessageDialog(this,
                        "Interest rate must be between 0 and 100.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Invalid number format!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // Get repayment period with validation
        double year = 0;
        while (true) {
            String yearStr = JOptionPane.showInputDialog(this, "Enter repayment period (years):");
            if (yearStr == null) return; // User cancelled
            
            try {
                year = Double.parseDouble(yearStr);
                if (year <= 0) {
                    JOptionPane.showMessageDialog(this,
                        "Repayment period cannot be 0 or negative.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Invalid number format!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // Get repayment frequency
        String[] frequencyOptions = {"Monthly", "Quarterly"};
        int frequencyChoice = JOptionPane.showOptionDialog(this,
            "Select repayment frequency:",
            "Repayment Frequency",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            frequencyOptions,
            frequencyOptions[0]);
            
        if (frequencyChoice == JOptionPane.CLOSED_OPTION) return; // User cancelled
        
        // Create loan and show schedule
        CreditLoan detailLoan = new CreditLoan(userId, principal, interest, year, frequencyChoice + 1);
        detailLoan.OpenLoan();
        detailLoan.updateDueDate();
        
        // Create and show loan schedule dialog
        JDialog scheduleDialog = new JDialog(this, "Loan Repayment Schedule", true);
        scheduleDialog.setSize(600, 400);
        scheduleDialog.setLocationRelativeTo(this);
        
        JPanel schedulePanel = new JPanel(new BorderLayout(10, 10));
        schedulePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Redirect System.out to capture the schedule
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        
        // Display the schedule
        detailLoan.displaySchedule();
        
        // Restore System.out and get the captured output
        System.setOut(originalOut);
        String scheduleText = baos.toString();
        
        // Create text area for schedule
        JTextArea scheduleArea = new JTextArea(scheduleText);
        scheduleArea.setEditable(false);
        scheduleArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(scheduleArea);
        
        // Add close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> scheduleDialog.dispose());
        
        schedulePanel.add(scrollPane, BorderLayout.CENTER);
        schedulePanel.add(closeButton, BorderLayout.SOUTH);
        
        scheduleDialog.add(schedulePanel);
        scheduleDialog.setVisible(true);
        
        updateMainPanel();
        
    } else if (choice == 1) { // Repay Credit Loan
        if (!loan.getStatus().equals("repaid")) {
            JOptionPane.showMessageDialog(this,
                "You have not taken any loan or have already made full repayment.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this,
            String.format("You have RM%.2f to pay before %s",
            loan.getLoan(),
            loan.getNewDueDate()));
            
        // Get repayment amount with validation
        while (true) {
            String repaymentStr = JOptionPane.showInputDialog(this, "Enter repayment amount:");
            if (repaymentStr == null) return; // User cancelled
            
            try {
                double repayment = Double.parseDouble(repaymentStr);
                if (repayment <= 0) {
                    JOptionPane.showMessageDialog(this,
                        "Repayment amount must be positive!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                if (repayment > loan.getLoan()) {
                    JOptionPane.showMessageDialog(this,
                        "Repayment amount cannot exceed loan amount!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                
                loan.repayment(repayment);
                updateMainPanel();
                break;
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                    "Invalid number format!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
    
    private void handlePredictor() {
        JDialog predictorDialog = new JDialog(this, "Deposit Interest Predictor", true);
        predictorDialog.setSize(400, 300);
        predictorDialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Input fields
        JTextField depositField = new JTextField(10);
        String[] banks = {"RHB (2.6%)", "MayBank (2.5%)", "Hong Leong (2.3%)", 
                         "Aliance (2.85%)", "Ambank (2.55%)", "Standard Chartered (2.65%)"};
        JComboBox<String> bankCombo = new JComboBox<>(banks);
        String[] periods = {"Daily", "Monthly", "Annually"};
        JComboBox<String> periodCombo = new JComboBox<>(periods);
        
        panel.add(new JLabel("Deposit Amount (RM):"), gbc);
        panel.add(depositField, gbc);
        panel.add(new JLabel("Select Bank:"), gbc);
        panel.add(bankCombo, gbc);
        panel.add(new JLabel("Select Period:"), gbc);
        panel.add(periodCombo, gbc);
        
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(e -> {
            try {
                double deposit = Double.parseDouble(depositField.getText());
                if (deposit <= 0) {
                    JOptionPane.showMessageDialog(predictorDialog, 
                        "Deposit amount must be positive!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                DepositInterestPredictor predictor = new DepositInterestPredictor(
                    deposit,
                    bankCombo.getSelectedIndex() + 1,
                    periodCombo.getSelectedIndex() + 1
                );
                
                String result = String.valueOf(predictor.calculateInterest());
                JOptionPane.showMessageDialog(predictorDialog, 
                    result, 
                    "Calculation Result", 
                    JOptionPane.INFORMATION_MESSAGE);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(predictorDialog, 
                    "Invalid deposit amount!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        panel.add(calculateButton, gbc);
        
        predictorDialog.add(panel);
        predictorDialog.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfaceMackBank().setVisible(true);
        });
    }
}
