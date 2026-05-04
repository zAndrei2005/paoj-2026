package com.pao.laboratory09.exercise3;

import java.util.ArrayDeque;
import java.util.Deque;

public class CoadaTranzactii {
    private static final int CAPACITATE = 5;

    private final Deque<Tranzactie> coada = new ArrayDeque<>();
    private boolean inchisa;

    public synchronized void adauga(Tranzactie tranzactie) throws InterruptedException {
        while (coada.size() == CAPACITATE && !inchisa) {
            System.out.println("[" + tranzactie.getSursa() + "] astept loc...");
            wait();
        }
        if (inchisa) {
            return;
        }
        coada.addLast(tranzactie);
        notifyAll();
    }

    public synchronized Tranzactie extrage() throws InterruptedException {
        while (coada.isEmpty() && !inchisa) {
            wait();
        }
        if (coada.isEmpty()) {
            return null;
        }
        Tranzactie tranzactie = coada.removeFirst();
        notifyAll();
        return tranzactie;
    }

    public synchronized boolean esteGoala() {
        return coada.isEmpty();
    }

    public synchronized void inchide() {
        inchisa = true;
        notifyAll();
    }
}


