package main.java.com.nestdev;

import main.java.com.nestdev.model.entities.Account;
import main.java.com.nestdev.model.entities.BrazilAccount;
import main.java.com.nestdev.services.BankService;
import main.java.com.nestdev.strategy.BrazilBank;

import javax.xml.transform.Source;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Faz a leitura do caminho do arquivo
        String path = "C:\\Users\\David\\Documents\\ESTUDOS-PROGRAMACAO\\CURSO-DE-JAVA\\PROJETO\\AccountProject\\src\\main\\java\\com\\nestdev\\arq";
        //Cria a lista de uam conta
        List<Account> list = new ArrayList<>();
        //Instancia uma conta de determinado país(POLIMORFISMO)

        //Le o arquivo e o separa com base nas vírgulas e adicionando os itens na lista como um OBJ do país
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line = br.readLine();
            while (line != null) {
                //Separa
                String[] parts = line.split(",");
                //Separa id
                int id = Integer.parseInt(parts[0]);
                //Separa o saldo
                double balance = Double.parseDouble(parts[1]);
                //Separa o titular
                String holder = parts[2];
                //Separa o limite
                double withdrawLimit = Double.parseDouble(parts[3]);
                //Separa a senha
                String password = parts[4];
                //Adiciona na lista
                list.add(new BrazilAccount(id, balance, holder, withdrawLimit, password));
                line = br.readLine();
            }


        } catch (IOException e) {
            System.out.println("ERROR!: " +  e.getMessage());;
        }

        BankService service = new BankService(list);

        //Para ler arquivos
        Scanner input = new Scanner(System.in);

        //Opção de usuário
        System.out.println("Welcome to NESTDEV! Select one of the options below:");
        System.out.println("1 - Create an account");
        System.out.println("2 - Access account");
        System.out.print("OPTION: ");
        int option = input.nextInt();

        String name;

        //Caso option seja 1 ou 2 e se não for dá erro
        switch (option) {
            case 1:
                System.out.println("Register your account below: ");
                System.out.println("Id(Automatic)");

                System.out.print("Current balance: ");
                double newBalance = input.nextDouble();

                System.out.print("Your name: ");
                input.nextLine();
                String newHolder = input.nextLine();

                System.out.print("Your Withdrawal Limit: ");
                double newWithdrawalLimit = input.nextDouble();

                System.out.print("Password: ");
                input.nextLine();
                String password = input.nextLine();

                //Gera um novo id
                int newId = service.generateNewId();

                // instancia uma nova conta
                BrazilAccount newAccount = new BrazilAccount(newId, newBalance, newHolder, newWithdrawalLimit, password);
                list.add(newAccount);

                //Transforma em String
                String lineAdd = newId + "," + newBalance + "," + newHolder + "," + newWithdrawalLimit + "," + password;

                //Adiciona o usuario no arquivo de texto
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
                    bw.write(lineAdd);
                    bw.newLine();
                } catch (IOException e) {
                    System.out.println("ERROR writing file: " + e.getMessage());
                }

                System.out.println("Account created successfully! - " + lineAdd);
                break;



            case 2:

                //Começa o processo de acessar a conta com id
                System.out.println("Please provide the following information: ");
                System.out.print("User id: ");
                int id = input.nextInt();
                input.nextLine();


                //Pesquisa o id da conta
                Account foundAccount = service.findAccountById(id);

                //Se foundAccount for nulo é porque nenhum obj foi encontrado para ele apontar
                if (foundAccount == null) {
                    System.out.println("Account not found!");
                    break;
                }

                //Começa o processo de inserir a senha
                System.out.print("Enter your password: ");
                String userPassword = input.nextLine();


                //Se a senha estiver incorreta o usuário vai ter apenas mais três oportunidades de inseri-la novamente
                int attempts = 3;
                
                while (!userPassword.equals(foundAccount.getPassword()) && attempts > 1) {
                    attempts--;
                    System.out.println("ERRO! INVALID PASSWORD! Attempts left: " + attempts);
                    System.out.print("Enter again: ");
                    userPassword = input.nextLine();
                }

                //Caso ele não acerte em nenhuma tentativa o sistema é bloqueado
                if (!userPassword.equals(foundAccount.getPassword())) {
                    System.out.println("Account locked!");
                    break;
                }

                //Caso a senha esteja correta o login é realizado mostrando os dados e borrando a senha com "*"
                System.out.println("Login successful! Welcome " + foundAccount.getHolder() + "\nid: " + foundAccount.getId() +
                        "\nPassword: " + "*".repeat(foundAccount.getPassword().length()));
                break;
        }

        System.out.println("");









    }
}
