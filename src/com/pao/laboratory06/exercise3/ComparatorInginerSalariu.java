package com.pao.laboratory06.exercise3;

import java.util.Comparator;

public class ComparatorInginerSalariu implements Comparator<Inginer> {
    @Override
    public int compare(Inginer stanga, Inginer dreapta) {
        if (stanga == null || dreapta == null) {
            throw new IllegalArgumentException("Inginerii comparati nu pot fi null.");
        }
        return Double.compare(dreapta.getSalariu(), stanga.getSalariu());
    }
}

