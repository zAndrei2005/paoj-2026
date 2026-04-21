package com.pao.project.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class ExtrasCont {
    private String iban;
    private LocalDateTime dataGenerare;
    private Set<Tranzactie> istoricTranzactii;
    private double soldLaZi;

    public ExtrasCont(String iban, Set<Tranzactie> istoricTranzactii, double soldLaZi){
        this.iban = iban;
        this.istoricTranzactii = istoricTranzactii;
        this.soldLaZi = soldLaZi;
        this.dataGenerare = LocalDateTime.now();
    }

    public void afisareExtras() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println("=========================================");
        System.out.println("            EXTRAS DE CONT               ");
        System.out.println("=========================================");
        System.out.println("Cont IBAN: " + this.iban);
        System.out.println("Data generării: " + this.dataGenerare.format(formatter));
        System.out.println("Sold curent: " + this.soldLaZi + " RON");
        System.out.println("-----------------------------------------");
        System.out.println("          ISTORIC TRANZACȚII             ");
        System.out.println("-----------------------------------------");

        if (istoricTranzactii.isEmpty()) {
            System.out.println("Nu există tranzacții înregistrate pentru acest cont.");
        } else {
            for (Tranzactie t : istoricTranzactii) {
                System.out.println(t.toString());
            }
        }
        System.out.println("=========================================");
    }

    public String getIban() { return iban; }
    public LocalDateTime getDataGenerare() { return dataGenerare; }
    public Set<Tranzactie> getIstoricTranzactii() { return istoricTranzactii; }
    public double getSoldLaZi() { return soldLaZi; }
}