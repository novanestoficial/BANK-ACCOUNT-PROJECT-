package main.java.com.nestdev.model.entities;

import main.java.com.nestdev.strategy.BankRules;
import main.java.com.nestdev.strategy.BrazilBank;

import java.util.List;

public class BrazilAccount extends Account{

    public BrazilAccount(Integer id, Double balance, String holder, Double withdrawalLimit, String password) {
        super(id, balance, holder, withdrawalLimit, password);
    }
    public double calculateInterest(double balance) {
        return balance + balance * 0.2;
    }

    public double calculateTransferFee(double value) {
        return value + value * 0.02;
    }



}
