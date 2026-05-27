package com.pao.project.service;

import com.pao.audit.AuditService;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.pao.project.model.*;

public class BancaService {
    private Map<String, Client> clientMap;
    private Map<String, Cont> conturiMap;
    private Set<Tranzactie> istoricTranzactii;

    private Map<String, Card> carduriMap;

    private int contorTranzactii = 1;
    private final AuditService auditService;

    public BancaService(){
        this.clientMap = new HashMap<>();
        this.conturiMap = new HashMap<>();
        this.istoricTranzactii = new TreeSet<>();
        this.carduriMap = new HashMap<>();
        this.auditService = AuditService.getInstance();
    }

    public void adaugaClient(Client client){
        auditService.audit("adauga_client", "cnp=" + client.getCnp() + ";nume=" + client.getNume());
        if(clientMap.containsKey(client.getCnp())){
            System.out.println("Clientul cu acest CNP exista deja");
        }
        else {
            clientMap.put(client.getCnp(), client);
            System.out.println("Succes: Clientul " + client.getNume() + " a fost adăugat.");
        }
    }

    public void deschideCont(String cnpClient, Cont contNou){
        auditService.audit("deschide_cont", "cnp=" + cnpClient + ";iban=" + contNou.getIban());
        Client client = clientMap.get(cnpClient);
        if (client != null){
            client.adaugaCont(contNou);
            conturiMap.put(contNou.getIban(), contNou);
            System.out.println("Succes: Contul " + contNou.getIban() + " a fost deschis.");
        }
        else
            System.out.println("Eroare: Clientul nu a fost găsit în sistem.");
    }

    public void depunere(String iban, double suma){
        auditService.audit("depunere", "iban=" + iban + ";suma=" + suma);
        Cont cont = conturiMap.get(iban);
        if (cont != null && suma > 0){
            double soldNou = cont.getSold() + suma;
            cont.setSold(soldNou);

            Tranzactie t = new Tranzactie(contorTranzactii++, suma, "Depunere");
            istoricTranzactii.add(t);
            System.out.println("Succes: Depunere finalizata!");
        }
    }

    public void retragere(String iban, double suma){
        auditService.audit("retragere", "iban=" + iban + ";suma=" + suma);
        Cont cont = conturiMap.get(iban);
        if (cont != null) {
            cont.retragere(suma);

            Tranzactie t = new Tranzactie(contorTranzactii++, suma, "Retragere");
            istoricTranzactii.add(t);
        }
    }

    public void transfer(String ibanSursa, String ibanDestinatie, double suma) {
        auditService.audit("transfer", "sursa=" + ibanSursa + ";dest=" + ibanDestinatie + ";suma=" + suma);
        Cont contSursa = conturiMap.get(ibanSursa);
        Cont contDest = conturiMap.get(ibanDestinatie);

        if (contSursa != null && contDest != null && suma > 0) {
            if (contSursa.getSold() >= suma) {
                contSursa.retragere(suma);
                contDest.setSold(contDest.getSold() + suma);

                Tranzactie t = new Tranzactie(contorTranzactii++, suma, "Transfer catre " + ibanDestinatie);
                istoricTranzactii.add(t);
                System.out.println("Succes: Transfer efectuat.");
            } else {
                System.out.println("Eroare: Fonduri insuficiente pentru transfer.");
            }
        }
    }

    public Card emitereCard(String iban, String numarCard, String pin) {
        auditService.audit("emitere_card", "iban=" + iban + ";card=" + numarCard);
        Cont cont = conturiMap.get(iban);
        if (cont != null && cont instanceof ContCurent) {
            Card cardNou = new Card(numarCard, pin, iban);

            carduriMap.put(numarCard, cardNou);

            System.out.println("Succes: Cardul " + numarCard + " a fost emis.");
            return cardNou;
        } else {
            System.out.println("Eroare: Cardurile pot fi emise doar pentru Conturi Curente valide.");
            return null;
        }
    }

    public void interogareSold(String iban) {
        auditService.audit("interogare_sold", "iban=" + iban);
        Cont cont = conturiMap.get(iban);
        if (cont != null) {
            System.out.println("Soldul pentru contul " + iban + " este: " + cont.getSold() + " RON");
        } else {
            System.out.println("Eroare: Contul nu există.");
        }
    }

    public void blocareCard(String numarCard) {
        auditService.audit("blocare_card", "card=" + numarCard);
        Card cardGasit = carduriMap.get(numarCard);
        if (cardGasit != null) {
            cardGasit.blocareCard();
        } else {
            System.out.println("Eroare: Cardul cu numărul " + numarCard + " nu a fost găsit în sistem.");
        }
    }

    public void afisareIstoricGlobal() {
        auditService.audit("istoric_global", "count=" + istoricTranzactii.size());
        System.out.println("--- ISTORIC GLOBAL TRANZACȚII ---");
        for (Tranzactie t : istoricTranzactii) {
            System.out.println(t.toString());
        }
    }

    public void generareExtras(String iban) {
        auditService.audit("generare_extras", "iban=" + iban);
        Cont cont = conturiMap.get(iban);
        if (cont != null) {
            Set<Tranzactie> tranzactiiFiltrate = new TreeSet<>();

            for (Tranzactie t : istoricTranzactii) {
                tranzactiiFiltrate.add(t);
            }

            ExtrasCont extras = new ExtrasCont(iban, tranzactiiFiltrate, cont.getSold());
            extras.afisareExtras();
        } else {
            System.out.println("Eroare: Contul nu există.");
        }
    }
}
