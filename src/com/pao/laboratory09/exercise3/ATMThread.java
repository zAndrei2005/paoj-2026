package com.pao.laboratory09.exercise3;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class ATMThread extends Thread {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private final int atmId;
    private final CoadaTranzactii coada;

    public ATMThread(int atmId, CoadaTranzactii coada) {
        super("ATM-" + atmId);
        this.atmId = atmId;
        this.coada = coada;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            int id = ID_GENERATOR.getAndIncrement();
            double suma = 100.0 + atmId * 25.0 + i * 12.5;
            String data = String.format(Locale.US, "2026-05-%02d", id);
            Tranzactie tranzactie = new Tranzactie(id, suma, data, getName());

            System.out.printf(Locale.US, "[%s] trimite: Tranzactie #%d %.2f RON%n", getName(), id, suma);
            try {
                coada.adauga(tranzactie);
                if (i < 3) {
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

