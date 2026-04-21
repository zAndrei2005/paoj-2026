package com.pao.project.model;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String nume, adresa;
    private String cnp;
    private List<Cont> listaConturi;

    public Client(String nume, String adresa, String cnp){
        this.nume = nume;
        this.adresa = adresa;
        this.cnp = cnp;
        this.listaConturi = new ArrayList<>();
    }

    public void adaugaCont(Cont contNou){
        if(contNou != null){
            this.listaConturi.add(contNou);
            System.out.println("Contul cu IBAN-ul " + contNou.getIban() + " a fost atașat clientului " + this.nume);
        }
        else{
            System.out.println("EROARE Contul furnizat este invalid!");
        }
    }

    public void afisareConturi() {
        System.out.println("Clientul " + this.nume + " deține următoarele conturi:");
        if (listaConturi.isEmpty()) {
            System.out.println("- Niciun cont deschis momentan.");
        } else {
            for (Cont c : listaConturi) {
                System.out.println("- IBAN: " + c.getIban() + " | Sold: " + c.getSold() + " RON");
            }
        }
    }

    public String getCnp() { return cnp; }
    public String getNume() { return nume; }
}