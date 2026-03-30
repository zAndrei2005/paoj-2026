package com.pao.laboratory06.exercise2;

import com.pao.laboratory06.exercise1.Angajat;
import java.util.Locale;

public abstract class Colaborator extends Angajat implements IOperatiiCitireScriere {
    protected String prenume;

    public Colaborator() {
        super("", 0.0);
    }

    public abstract double calculeazaVenitNetAnual();

    public String descriere() {
        return String.format(Locale.US, "%s: %s %s, venit net anual: %.2f lei",
                tipContract(), nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public void afiseaza() {
        System.out.println(descriere());
    }
}