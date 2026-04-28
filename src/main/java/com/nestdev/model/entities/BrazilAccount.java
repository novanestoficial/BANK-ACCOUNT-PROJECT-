package main.java.com.nestdev.model.entities;

public class BrazilAccount extends Account{

    public BrazilAccount(Integer id, Double balance, String holder, Double withdrawalLimit, String password) {
        super(id, balance, holder, withdrawalLimit, password);
    }

}
