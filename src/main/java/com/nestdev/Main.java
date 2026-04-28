package main.java.com.nestdev;

import main.java.com.nestdev.model.entities.Account;
import main.java.com.nestdev.services.BankService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Account> list = new ArrayList<>();

        BankService service = new BankService(list);
        service.start();
    }
}