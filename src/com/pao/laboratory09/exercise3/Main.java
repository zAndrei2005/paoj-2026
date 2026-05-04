package com.pao.laboratory09.exercise3;

public class Main {
    public static void main(@SuppressWarnings("unused") String[] args) {
        if (args != null && args.length > 0) {
            System.out.print("");
        }

        CoadaTranzactii coada = new CoadaTranzactii();
        ProcessorThread processorThread = new ProcessorThread(coada);
        Thread firProcessor = new Thread(processorThread, "Processor");

        ATMThread[] atms = {
                new ATMThread(1, coada),
                new ATMThread(2, coada),
                new ATMThread(3, coada)
        };

        for (ATMThread atm : atms) {
            atm.start();
        }

        firProcessor.start();

        for (ATMThread atm : atms) {
            try {
                atm.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        processorThread.activ = false;
        coada.inchide();

        try {
            firProcessor.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        System.out.println("Toate tranzactiile procesate. Total: 12");
    }
}
