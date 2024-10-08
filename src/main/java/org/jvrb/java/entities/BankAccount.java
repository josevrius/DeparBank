package org.jvrb.java.entities;

import org.jvrb.java.exceptions.AccountException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public final class BankAccount {

    private String number;
    private String holder;
    private double balance;
    private List<String> historic = new ArrayList<>();

    public BankAccount(String number, String holder, double balance) throws AccountException {
        if (validateNumber(number)) {
            this.number = number;
        } else {
            throw new AccountException("ERROR: Cuenta no válida");
        }
        if (validateHolder(holder)) {
            this.holder = holder;
        } else {
            throw new AccountException("ERROR: Titular no válido");
        }
        if (validateBalance(balance)) {
            this.balance = balance;
        } else {
            throw new AccountException("ERROR: Saldo no válido");
        }
    }

    public void showInfo() {
        System.out.println("IBAN ......: " + number);
        System.out.println("Titular ...: " + holder);
        System.out.println("Saldo .....: " + balance + '€');
    }

    public void showIBAN() {
        System.out.println("IBAN ......: " + number);
    }

    public void showHolder() {
        System.out.println("Titular ...: " + holder);
    }

    public void showBalance() {
        System.out.println("Saldo .....: " + balance + '€');
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            historic.add("+" + amount + '€');
            limitHistoric();
            saveHistoric();
            System.out.println("Ingreso ...: " + amount + '€');
            warnAuthority(amount);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance - amount >= -50) {
            balance -= amount;
            historic.add("-" + amount + '€');
            limitHistoric();
            saveHistoric();
            System.out.println("Retirada ..: " + amount + '€');
            if (balance < 0) {
                System.out.println("AVISO: Saldo negativo");
            }
            warnAuthority(amount);
        }
    }

    public void showHistoric() {
        System.out.println();
        System.out.println("HISTORIAL DE MOVIMIENTOS");
        System.out.println("========================");
        for (String movement : historic) {
            System.out.println(movement);
        }
    }

    public void loadHistoric() {
        try (BufferedReader bf = new BufferedReader(new FileReader(number + ".txt"))) {
            String line;
            while ((line = bf.readLine()) != null) {
                historic.add(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void saveHistoric() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(number + ".txt"))) {
            for (String movement : historic) {
                bw.write(movement + System.lineSeparator());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void limitHistoric() {
        if (historic.size() > 100) {
            historic.removeFirst();
        }
    }

    private void warnAuthority(double amount) {
        if (amount > 3000) {
            System.out.println("AVISO: Notificación a hacienda");
        }
    }

    private boolean validateNumber(String number) {
        return number.matches("[A-Z]{2}\\d{22}");
    }

    private boolean validateHolder(String holder) {
        return holder.matches("[A-Za-zÁÉÍÓÚÜÑáéíóúüñ ]{1,30}");
    }

    private boolean validateBalance(double balance) {
        return balance >= 0;
    }
}
