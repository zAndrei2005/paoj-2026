package com.pao.laboratory07.exercise3;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());
        List<Comanda> comenzi = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String line = sc.nextLine().trim();
            String[] tokens = line.split(" ");
            if (tokens[0].equals("STANDARD")) {
                String nume = tokens[1];
                double pret = Double.parseDouble(tokens[2]);
                String client = tokens[3];
                Comanda c = new ComandaStandard(nume, pret, client);
                comenzi.add(c);
            } else if (tokens[0].equals("DISCOUNTED")) {
                String nume = tokens[1];
                double pret = Double.parseDouble(tokens[2]);
                int discount = Integer.parseInt(tokens[3]);
                String client = tokens[4];
                Comanda c = new ComandaRedusa(nume, pret, discount, client);
                comenzi.add(c);
            } else if (tokens[0].equals("GIFT")) {
                String nume = tokens[1];
                String client = tokens[2];
                Comanda c = new ComandaGratuita(nume, client);
                comenzi.add(c);
            }
        }
        for (Comanda c : comenzi) {
            System.out.println(c.descriere());
        }
        System.out.println();
        while (true) {
            String command = sc.nextLine().trim();
            if (command.equals("QUIT")) {
                break;
            } else if (command.equals("STATS")) {
                System.out.println("--- STATS ---");
                Map<String, Double> averages = comenzi.stream()
                    .collect(Collectors.groupingBy(Comanda::getType, Collectors.averagingDouble(Comanda::pretFinal)));
                if (averages.containsKey("STANDARD")) {
                    System.out.printf("STANDARD: medie = %.2f lei\n", averages.get("STANDARD"));
                }
                if (averages.containsKey("DISCOUNTED")) {
                    System.out.printf("DISCOUNTED: medie = %.2f lei\n", averages.get("DISCOUNTED"));
                }
                if (averages.containsKey("GIFT")) {
                    System.out.printf("GIFT: medie = %.2f lei\n", averages.get("GIFT"));
                }
                System.out.println();
            } else if (command.startsWith("FILTER ")) {
                String[] parts = command.split(" ");
                double threshold = Double.parseDouble(parts[1]);
                System.out.println("--- FILTER (>= " + String.format("%.2f", threshold) + ") ---");
                comenzi.stream()
                    .filter(c -> c.pretFinal() >= threshold)
                    .forEach(c -> System.out.println(c.shortDescriere()));
                System.out.println();
            } else if (command.equals("SORT")) {
                System.out.println("--- SORT (by client, then by pret) ---");
                comenzi.stream()
                    .sorted(Comparator.comparing(Comanda::getClient).thenComparing(Comanda::pretFinal))
                    .forEach(c -> System.out.println(c.shortDescriere()));
                System.out.println();
            } else if (command.equals("SPECIAL")) {
                System.out.println("--- SPECIAL (discount > 15%) ---");
                comenzi.stream()
                    .filter(c -> c.getDiscount() > 15)
                    .forEach(c -> System.out.println(c.shortDescriere()));
                System.out.println();
            }
        }
    }
}
