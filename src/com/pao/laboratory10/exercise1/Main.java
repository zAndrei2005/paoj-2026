package com.pao.laboratory10.exercise1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList<Tranzactie> coada = new LinkedList<>();

        while (scanner.hasNext()) {
            String comanda = scanner.next();
            switch (comanda) {
                case "ENQUEUE": {
                    int id = scanner.nextInt();
                    double suma = scanner.nextDouble();
                    String data = scanner.next();
                    TipTranzactie tip = TipTranzactie.valueOf(scanner.next());
                    coada.addLast(new Tranzactie(id, suma, data, tip));
                    break;
                }
                case "DEQUEUE": {
                    if (coada.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        System.out.println("Procesat: " + coada.removeFirst());
                    }
                    break;
                }
                case "PUSH": {
                    int id = scanner.nextInt();
                    double suma = scanner.nextDouble();
                    String data = scanner.next();
                    TipTranzactie tip = TipTranzactie.valueOf(scanner.next());
                    coada.addFirst(new Tranzactie(id, suma, data, tip));
                    break;
                }
                case "POP": {
                    if (coada.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        System.out.println("Extras: " + coada.removeFirst());
                    }
                    break;
                }
                case "REMOVE_DEBIT": {
                    int eliminate = 0;
                    Iterator<Tranzactie> iterator = coada.iterator();
                    while (iterator.hasNext()) {
                        if (iterator.next().getTip() == TipTranzactie.DEBIT) {
                            iterator.remove();
                            eliminate++;
                        }
                    }
                    System.out.println("Eliminat " + eliminate + " tranzactii DEBIT.");
                    break;
                }
                case "REMOVE_BELOW": {
                    double prag = scanner.nextDouble();
                    int eliminate = 0;
                    Iterator<Tranzactie> iterator = coada.iterator();
                    while (iterator.hasNext()) {
                        if (iterator.next().getSuma() < prag) {
                            iterator.remove();
                            eliminate++;
                        }
                    }
                    System.out.printf("Eliminat %d tranzactii sub %.2f RON.%n", eliminate, prag);
                    break;
                }
                case "PRINT": {
                    for (Tranzactie tranzactie : coada) {
                        System.out.println(tranzactie);
                    }
                    break;
                }
                case "SIZE": {
                    System.out.println("Dimensiune coada: " + coada.size());
                    break;
                }
                default:
                    // Ignoram comenzi necunoscute pentru robustete la input invalid.
                    break;
            }
        }
    }
}
