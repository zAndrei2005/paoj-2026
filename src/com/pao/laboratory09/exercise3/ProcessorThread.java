package com.pao.laboratory09.exercise3;

import java.util.Locale;

public class ProcessorThread implements Runnable {
    public volatile boolean activ = true;

    private final CoadaTranzactii coada;

    public ProcessorThread(CoadaTranzactii coada) {
        this.coada = coada;
    }

    @Override
    public void run() {
        try {
            while (activ || !coada.esteGoala()) {
                Tranzactie tranzactie = coada.extrage();
                if (tranzactie == null) {
                    continue;
                }
                System.out.printf(Locale.US, "[Processor] Factura #%d - %.2f RON | %s%n",
                        tranzactie.getId(),
                        tranzactie.getSuma(),
                        tranzactie.getData());
                Thread.sleep(80);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


