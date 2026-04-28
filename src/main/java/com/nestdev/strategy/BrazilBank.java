package main.java.com.nestdev.strategy;

public class BrazilBank implements BankRules {

    @Override
    public double calculateInterest(double balance) {
        return balance * 0.2;
    }

    @Override
    public double calculateTransferFee(double value) {
        return 5.0;
    }

    @Override
    public double getCurrentAccountLimit() {
        return 500.0;
    }
}
