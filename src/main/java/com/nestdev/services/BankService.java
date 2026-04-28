package main.java.com.nestdev.services;

import main.java.com.nestdev.model.entities.Account;
import main.java.com.nestdev.model.entities.BrazilAccount;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class BankService {

    private List<Account> accounts;
    private Account loggedAccount = null;

    public BankService(List<Account> accounts) {
        this.accounts = accounts;
        loadAccountsFromFile();
    }


    // START DO SISTEMA
    public void start() {

        Scanner input = new Scanner(System.in);

        while (true) {

            if (loggedAccount == null) {
                System.out.println("\n=== MAIN MENU ===");
                System.out.println("1 - Create account");
                System.out.println("2 - Login");
                System.out.println("0 - Exit");
            } else {
                System.out.println("\n=== ACCOUNT MENU ===");
                System.out.println("1 - Deposit");
                System.out.println("2 - Withdraw");
                System.out.println("3 - Loan");
                System.out.println("4 - Transfer");
                System.out.println("5 - Logout");
            }

            System.out.print("Option: ");
            int option = input.nextInt();

            if (loggedAccount == null) {

                switch (option) {
                    case 1 -> createAccount(input);
                    case 2 -> login(input);
                    case 0 -> System.exit(0);
                    default -> System.out.println("Invalid option");
                }

            } else {

                switch (option) {
                    case 1 -> deposit(input);
                    case 2 -> withdraw(input);
                    case 3 -> loan(input);
                    case 4 -> transfer(input);
                    case 5 -> logout();
                    default -> System.out.println("Invalid option");
                }
            }
        }
    }

    public void logout() {
        loggedAccount = null;
        System.out.println("Logged out successfully!");
    }


    // CREATE ACCOUNT
    public void createAccount(Scanner input) {

        System.out.println("Register account:");

        System.out.print("Balance: ");
        double balance = input.nextDouble();

        input.nextLine();
        System.out.print("Holder: ");
        String holder = input.nextLine();

        System.out.print("Withdrawal limit: ");
        double limit = input.nextDouble();

        input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        int id = generateNewId();

        Account acc = new BrazilAccount(id, balance, holder, limit, password);
        accounts.add(acc);

        saveToFile(acc);

        System.out.println("Account created! ID: " + id);
    }


    // LOGIN
    public void login(Scanner input) {

        System.out.print("User id: ");
        int id = input.nextInt();
        input.nextLine();

        Account acc = findAccountById(id);

        if (acc == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Password: ");
        String pass = input.nextLine();

        int attempts = 3;

        while (!pass.equals(acc.getPassword()) && attempts > 1) {
            attempts--;
            System.out.println("Wrong password. Attempts: " + attempts);
            pass = input.nextLine();
        }

        if (!pass.equals(acc.getPassword())) {
            System.out.println("Account locked!");
            return;
        }

        loggedAccount = acc;

        System.out.println("Welcome " + acc.getHolder() + "!"
                + "\nId: " + acc.getId()
                + "\nPassword: " + "*".repeat(acc.getPassword().length())
                + "\nBalance: " + acc.getBalance());
    }


    // FIND ACCOUNT
    public Account findAccountById(int id) {
        for (Account acc : accounts) {
            if (acc.getId() == id) {
                return acc;
            }
        }
        return null;
    }


    // GENERATE ID
    public int generateNewId() {
        int max = 0;

        for (Account acc : accounts) {
            if (acc.getId() > max) {
                max = acc.getId();
            }
        }
        return max + 1;
    }


    // FILE LOAD
    private void loadAccountsFromFile() {

        String path = "C:\\Users\\David\\Documents\\ESTUDOS-PROGRAMACAO\\CURSO-DE-JAVA\\PROJETO\\AccountProject\\src\\main\\java\\com\\nestdev\\arq";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line = br.readLine();

            while (line != null) {

                String[] parts = line.split(",");

                int id = Integer.parseInt(parts[0]);
                double balance = Double.parseDouble(parts[1]);
                String holder = parts[2];
                double limit = Double.parseDouble(parts[3]);
                String password = parts[4];

                accounts.add(new BrazilAccount(id, balance, holder, limit, password));

                line = br.readLine();
            }

        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }


    // SAVE FILE
    private void saveToFile(Account acc) {

        String path = "C:\\Users\\David\\Documents\\ESTUDOS-PROGRAMACAO\\CURSO-DE-JAVA\\PROJETO\\AccountProject\\src\\main\\java\\com\\nestdev\\arq";

        String line = acc.getId() + "," +
                acc.getBalance() + "," +
                acc.getHolder() + "," +
                acc.getWithdrawalLimit() + "," +
                acc.getPassword();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    //SAVE ALL OPERATIONS TO FILE
    private void saveAllToFile() {

        String path = "C:\\Users\\David\\Documents\\ESTUDOS-PROGRAMACAO\\CURSO-DE-JAVA\\PROJETO\\AccountProject\\src\\main\\java\\com\\nestdev\\arq";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {

            for (Account acc : accounts) {

                String line = acc.getId() + "," +
                        acc.getBalance() + "," +
                        acc.getHolder() + "," +
                        acc.getWithdrawalLimit() + "," +
                        acc.getPassword();

                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }




    // OPERATIONS OF TRANSACTIONS


    //DEPOSIT
    public void deposit(Scanner input) {

        System.out.print("Deposit value: ");
        double value = input.nextDouble();

        loggedAccount.setBalance(loggedAccount.getBalance() + value);

        System.out.println("New balance: " + loggedAccount.getBalance());

        saveAllToFile();
    }


    //WITHDRAW
    public void withdraw(Scanner input) {

        System.out.print("Withdraw value: ");
        double value = input.nextDouble();

        if (value > loggedAccount.getWithdrawalLimit()) {
            System.out.println("Limit exceeded!");
            return;
        }

        if (value > loggedAccount.getBalance()) {
            System.out.println("Insufficient balance!");
            return;
        }

        loggedAccount.setBalance(loggedAccount.getBalance() - value);

        System.out.println("New balance: " + loggedAccount.getBalance());

        saveAllToFile();
    }

    //LOAN
    public void loan(Scanner input) {

        System.out.print("Loan value: ");
        double value = input.nextDouble();

        double fee = value * 0.1;

        loggedAccount.setBalance(loggedAccount.getBalance() + value - fee);

        System.out.println("Loan approved!");
        System.out.println("Fee: " + fee);

        saveAllToFile();
    }

    //TRANSFER
    public void transfer(Scanner input) {

        System.out.print("Receiver ID: ");
        int receiverId = input.nextInt();

        Account receiver = findAccountById(receiverId);

        if (receiver == null) {
            System.out.println("Receiver not found!");
            return;
        }

        System.out.print("Transfer value: ");
        double value = input.nextDouble();

        if (value > loggedAccount.getBalance()) {
            System.out.println("Insufficient balance!");
            return;
        }

        loggedAccount.setBalance(loggedAccount.getBalance() - value);
        receiver.setBalance(receiver.getBalance() + value);

        System.out.println("Transfer successful!");
        System.out.println("Balance = " + loggedAccount.getBalance());

        saveAllToFile();
    }
}