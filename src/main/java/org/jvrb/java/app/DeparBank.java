package org.jvrb.java.app;

import org.jvrb.java.entities.BankAccount;
import org.jvrb.java.exceptions.AccountException;

public class DeparBank {

    public static void main(String[] args) {

        try {
            BankAccount account = new BankAccount("ES1234567890123456789012", "Pepe Botica", 500);
            account.loadHistoric();
            System.out.println();
            account.showInfo();
            account.deposit(100);
            account.withdraw(50);
            account.showBalance();
            account.showHistoric();
        } catch (AccountException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }
}