package com.pao.laboratory05.audit;

import com.pao.laboratory05.angajati.Angajat;
import com.pao.laboratory05.angajati.Departament;

import java.util.Scanner;

/**
 * Exercise 4 (Bonus) — Audit Log
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 4 (Bonus) — Audit"
 *
 * Extinde soluția de la Exercise 3 cu un sistem de audit bazat pe record.
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AngajatService service = AngajatService.getInstance();
        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("4. Afișează audit log");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");

            int optiune = scanner.nextInt();
            scanner.nextLine();

            if (optiune == 0) {
                System.out.println("La revedere!");
                break;
            }

            switch (optiune) {
                case 1:
                    System.out.print("Nume angajat: ");
                    String nume = scanner.nextLine();

                    System.out.print("Nume departament: ");
                    String numeDept = scanner.nextLine();

                    System.out.print("Locație departament: ");
                    String locatieDept = scanner.nextLine();

                    System.out.print("Salariu: ");
                    double salariu = scanner.nextDouble();
                    scanner.nextLine();

                    Departament dept = new Departament(numeDept, locatieDept);
                    Angajat angajatNou = new Angajat(nume, dept, salariu);

                    service.addAngajat(angajatNou);
                    break;

                case 2:
                    System.out.println("Lista angajaților (sortată descrescător după salariu):");
                    service.listBySalary();
                    break;

                case 3:
                    System.out.print("Introduceți numele departamentului căutat: ");
                    String cautaDept = scanner.nextLine();
                    service.findByDepartament(cautaDept);
                    break;

                case 4:
                    service.printAuditLog();
                    break;

                default:
                    System.out.println("Opțiune invalidă! Încearcă din nou.");
            }
        }
        scanner.close();
    }
}
