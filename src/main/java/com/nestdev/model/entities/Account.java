package main.java.com.nestdev.model.entities;

import java.util.Objects;

public class Account {
    private Integer id;
    private Double balance;
    private String holder;
    private Double withdrawalLimit;
    private String password;

    public Account(Integer id, Double balance, String holder, Double withdrawalLimit, String password) {
        this.id = id;
        this.balance = balance;
        this.holder = holder;
        this.withdrawalLimit = withdrawalLimit;
        this.password = password;
    }

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public Double getBalance() {
        return balance;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public Double getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setWithdrawalLimit(Double withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User: " + id + "\n"
                + "Balance: " + balance + "\n"
                + "Holder: " + holder + "\n"
                + "Withdraw Limit: " + withdrawalLimit + "\n";
    }



}
