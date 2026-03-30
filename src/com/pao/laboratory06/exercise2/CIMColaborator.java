package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends PersoanaFizica {
    private boolean bonus = false;

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.salariu = in.nextDouble();

        String lineRest = in.nextLine().trim();
        if (lineRest.contains("DA")) {
            this.bonus = true;
        }
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNet = this.salariu * 12 * 0.55;
        if (areBonus()) {
            venitNet += venitNet * 0.10;
        }
        return venitNet;
    }

    @Override
    public boolean areBonus() { return bonus; }

    @Override
    public String tipContract() { return TipColaborator.CIM.name(); }
}