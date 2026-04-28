package main.java.com.nestdev.services;

import main.java.com.nestdev.model.entities.Account;
import java.util.List;

public class BankService {

    private List<Account> accounts;

    public BankService(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Account findAccountById(int id) {
        for (Account acc : accounts) {
            if (acc.getId() == id) {
                return acc;
            }
        }
        return null;
    }

    public int generateNewId() {
        int maxId = 0;
        for (Account acc : accounts) {
            if (acc.getId() > maxId) {
                maxId = acc.getId();
            }
        }
        return maxId + 1;

    }
}