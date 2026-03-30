package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends PersoanaFizica implements IOperatiiCitireScriere {
    private double cheltuieliLunare;

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.salariu = in.nextDouble();
        this.cheltuieliLunare = in.nextDouble();
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNetBaza = (this.salariu - cheltuieliLunare) * 12;
        double salariuMinim = 4050.0;

        double impozit = 0.10 * venitNetBaza;

        double cass;
        if (venitNetBaza < 6 * salariuMinim) {
            cass = 0.10 * (6 * salariuMinim);
        } else if (venitNetBaza <= 72 * salariuMinim) {
            cass = 0.10 * venitNetBaza;
        } else {
            cass = 0.10 * (72 * salariuMinim);
        }

        double cas;
        if (venitNetBaza < 12 * salariuMinim) {
            cas = 0;
        } else if (venitNetBaza <= 24 * salariuMinim) {
            cas = 0.25 * (12 * salariuMinim);
        } else {
            cas = 0.25 * (24 * salariuMinim);
        }

        return venitNetBaza - impozit - cass - cas;
    }

    @Override
    public String tipContract() { return TipColaborator.PFA.name(); }
}