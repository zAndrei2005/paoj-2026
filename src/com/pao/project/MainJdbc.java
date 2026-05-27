package com.pao.project;

import com.pao.db.DatabaseInitializer;
import com.pao.project.model.Client;
import com.pao.project.model.Card;
import com.pao.project.model.ContCurent;
import com.pao.project.service.JdbcBancaService;
import com.pao.repository.ClientRepository;
import com.pao.repository.CardRepository;
import com.pao.repository.ContRepository;
import com.pao.repository.TranzactieRepository;

public class MainJdbc {
    public static void main(String[] args) {
        DatabaseInitializer.runSchema();

        ClientRepository clientRepo = new ClientRepository();
        ContRepository contRepo = new ContRepository();
        CardRepository cardRepo = new CardRepository();
        TranzactieRepository tranzRepo = new TranzactieRepository();

        Client c1 = new Client("Alice", "Strada A 1", "111111111");
        Client c2 = new Client("Bob", "Strada B 2", "222222222");
        try {
            if (clientRepo.findById(c1.getCnp()).isEmpty()) {
                clientRepo.save(c1);
            }
            if (clientRepo.findById(c2.getCnp()).isEmpty()) {
                clientRepo.save(c2);
            }
        } catch (Exception e) {
            System.err.println("Failed to save clients: " + e.getMessage());
            return;
        }

        ContCurent acc1 = new ContCurent("RO111", 1000, 500);
        ContCurent acc2 = new ContCurent("RO222", 200, 500);
        try {
            if (contRepo.findById(acc1.getIban()).isEmpty()) {
                contRepo.saveForClient(acc1, c1.getCnp());
            }
            if (contRepo.findById(acc2.getIban()).isEmpty()) {
                contRepo.saveForClient(acc2, c2.getCnp());
            }
        } catch (Exception e) {
            System.err.println("Failed to save accounts: " + e.getMessage());
            return;
        }

        try {
            if (cardRepo.findById("CARD001").isEmpty()) {
                cardRepo.save(new Card("CARD001", "1234", acc1.getIban()));
            }
        } catch (Exception e) {
            System.err.println("Failed to save card: " + e.getMessage());
            return;
        }

        JdbcBancaService svc = new JdbcBancaService();
        try {
            svc.transfer("RO111", "RO222", 150, "system-demo");
            System.out.println("Transfer succeeded");
        } catch (Exception e) {
            System.err.println("Transfer failed: " + e.getMessage());
        }

        try {
            System.out.println("Clients in DB: " + clientRepo.findAll().size());
            System.out.println("Accounts in DB: " + contRepo.findAll().size());
            System.out.println("Cards in DB: " + cardRepo.findAll().size());
            System.out.println("Transactions in DB: " + tranzRepo.findAll().size());
            System.out.println("JOIN conturi-clienti: " + contRepo.findAccountsWithClients().size());
            System.out.println("JOIN carduri-clienti: " + cardRepo.findCardsWithOwner().size());
            System.out.println("JOIN tranzactii recente: " + tranzRepo.findRecentTranzactiiWithAccount(5).size());
        } catch (Exception e) {
            System.err.println("Demo query failed: " + e.getMessage());
        }
    }
}

