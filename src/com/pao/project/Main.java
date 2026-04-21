package com.pao.project;

import com.pao.project.model.*;
import com.pao.project.service.BancaService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        BancaService banca = new BancaService();
        Scanner scanner = new Scanner(System.in);
        boolean aplicatiePornita = true;

        // --- POPULARE INIȚIALĂ
        Client clientTest = new Client("Ion Popescu", "Bucuresti, Sector 1", "12345");
        banca.adaugaClient(clientTest);

        ContCurent contTest = new ContCurent("RO01", 1000, 500);
        banca.deschideCont("12345", contTest);
        // -------------------------------------------------------------------

        System.out.println("=== BUN VENIT ÎN SISTEMUL BANCAR ===");

        while (aplicatiePornita) {
            System.out.println("\n--- MENIU PRINCIPAL ---");
            System.out.println("1. Adăugare client nou");
            System.out.println("2. Deschidere cont");
            System.out.println("3. Interogare sold");
            System.out.println("4. Depunere numerar");
            System.out.println("5. Retragere numerar");
            System.out.println("6. Transfer bancar");
            System.out.println("7. Emitere card");
            System.out.println("8. Blocare card");
            System.out.println("9. Afișare istoric tranzacții (Global)");
            System.out.println("10. Generare extras de cont");
            System.out.println("0. Ieșire din aplicație");
            System.out.print("Alegeți o opțiune: ");

            int optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1:
                    System.out.println("--- Adăugare Client ---");
                    System.out.print("Introduceți Numele Complet: ");
                    String nume = scanner.nextLine();
                    System.out.print("Introduceți Adresa completă: ");
                    String adresa = scanner.nextLine();
                    System.out.print("Introduceți CNP: ");
                    String cnp = scanner.nextLine();

                    Client clientNou = new Client(nume, adresa, cnp);
                    banca.adaugaClient(clientNou);
                    break;

                case 2:
                    System.out.println("--- Deschidere Cont ---");
                    System.out.print("Introduceți CNP-ul clientului: ");
                    String cnpCont = scanner.nextLine();
                    System.out.print("Introduceți IBAN-ul noului cont: ");
                    String ibanNou = scanner.nextLine();
                    System.out.print("Tip cont (1 - Curent, 2 - Economii): ");
                    int tipCont = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Introduceți soldul inițial: ");
                    double soldInitial = scanner.nextDouble();
                    scanner.nextLine();

                    if (tipCont == 1) {
                        ContCurent cc = new ContCurent(ibanNou, soldInitial, 1000);
                        banca.deschideCont(cnpCont, cc);
                    } else if (tipCont == 2) {
                        ContEconomii ce = new ContEconomii(ibanNou, soldInitial, 5.0);
                        banca.deschideCont(cnpCont, ce);
                    } else {
                        System.out.println("Tip de cont invalid!");
                    }
                    break;

                case 3:
                    System.out.println("--- Interogare Sold ---");
                    System.out.print("Introduceți IBAN-ul: ");
                    String ibanSold = scanner.nextLine();
                    banca.interogareSold(ibanSold);
                    break;

                case 4:
                    System.out.println("--- Depunere Numerar ---");
                    System.out.print("Introduceți IBAN-ul: ");
                    String ibanDepunere = scanner.nextLine();
                    System.out.print("Suma de depus: ");
                    double sumaDepunere = scanner.nextDouble();
                    banca.depunere(ibanDepunere, sumaDepunere);
                    break;

                case 5:
                    System.out.println("--- Retragere Numerar ---");
                    System.out.print("Introduceți IBAN-ul: ");
                    String ibanRetragere = scanner.nextLine();
                    System.out.print("Suma de retras: ");
                    double sumaRetragere = scanner.nextDouble();
                    banca.retragere(ibanRetragere, sumaRetragere);
                    break;

                case 6:
                    System.out.println("--- Transfer Bancar ---");
                    System.out.print("IBAN Sursă: ");
                    String ibanSursa = scanner.nextLine();
                    System.out.print("IBAN Destinație: ");
                    String ibanDest = scanner.nextLine();
                    System.out.print("Suma de transferat: ");
                    double sumaTransfer = scanner.nextDouble();
                    banca.transfer(ibanSursa, ibanDest, sumaTransfer);
                    break;

                case 7:
                    System.out.println("--- Emitere Card ---");
                    System.out.print("Introduceți IBAN-ul contului curent: ");
                    String ibanCard = scanner.nextLine();
                    System.out.print("Introduceți un număr de card (16 cifre): ");
                    String nrCard = scanner.nextLine();
                    System.out.print("Introduceți PIN-ul dorit: ");
                    String pin = scanner.nextLine();
                    banca.emitereCard(ibanCard, nrCard, pin);
                    break;

                case 8:
                    System.out.println("--- Blocare Card ---");
                    System.out.print("Introduceți numărul cardului pe care doriți să-l blocați: ");
                    String numarCardDeBlocat = scanner.nextLine();
                    banca.blocareCard(numarCardDeBlocat);
                    break;

                case 9:
                    System.out.println("--- Istoric Tranzacții ---");
                    banca.afisareIstoricGlobal();
                    break;

                case 10:
                    System.out.println("--- Generare Extras Cont ---");
                    System.out.print("Introduceți IBAN-ul: ");
                    String ibanExtras = scanner.nextLine();
                    banca.generareExtras(ibanExtras);
                    break;

                case 0:
                    System.out.println("Sistemul se închide... La revedere!");
                    aplicatiePornita = false;
                    break;

                default:
                    System.out.println("Eroare: Opțiune invalidă! Vă rugăm să alegeți un număr de la 0 la 10.");
                    break;
            }
        }

        scanner.close();
    }
}