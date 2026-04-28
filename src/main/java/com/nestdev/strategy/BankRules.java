package main.java.com.nestdev.strategy;

public interface BankRules {
    double calculateInterest(double balance);
    double calculateTransferFee(double value);
    double getCurrentAccountLimit();
}
