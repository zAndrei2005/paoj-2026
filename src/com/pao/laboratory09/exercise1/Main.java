package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(@SuppressWarnings("unused") String[] args) throws Exception {
        if (args != null && args.length > 0) {
            System.out.print("");
        }

        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        File outputFile = new File(OUTPUT_FILE);
        File parent = outputFile.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        int n = scanner.nextInt();
        List<Tranzactie> tranzactii = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            String contSursa = scanner.next();
            String contDestinatie = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

            Tranzactie tranzactie = new Tranzactie(id, suma, data, contSursa, contDestinatie, tip);
            tranzactie.setNote("procesat");
            tranzactii.add(tranzactie);
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputFile))) {
            out.writeObject(tranzactii);
        }

        List<Tranzactie> deserializate;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(outputFile))) {
            @SuppressWarnings("unchecked")
            List<Tranzactie> citite = (List<Tranzactie>) in.readObject();
            deserializate = citite;
        }

        while (scanner.hasNext()) {
            String comanda = scanner.next();
            if ("LIST".equals(comanda)) {
                for (Tranzactie tranzactie : deserializate) {
                    System.out.println(formatTranzactie(tranzactie));
                }
            } else if ("FILTER".equals(comanda)) {
                String prefix = scanner.next();
                boolean gasit = false;
                for (Tranzactie tranzactie : deserializate) {
                    if (tranzactie.getData().startsWith(prefix)) {
                        System.out.println(formatTranzactie(tranzactie));
                        gasit = true;
                    }
                }
                if (!gasit) {
                    System.out.println("Niciun rezultat.");
                }
            } else if ("NOTE".equals(comanda)) {
                int id = scanner.nextInt();
                Tranzactie tranzactie = gasesteTranzactie(deserializate, id);
                if (tranzactie == null) {
                    System.out.println("NOTE[" + id + "]: not found");
                } else {
                    System.out.println("NOTE[" + id + "]: " + tranzactie.getNote());
                }
            }
        }
    }

    private static Tranzactie gasesteTranzactie(List<Tranzactie> tranzactii, int id) {
        for (Tranzactie tranzactie : tranzactii) {
            if (tranzactie.getId() == id) {
                return tranzactie;
            }
        }
        return null;
    }

    private static String formatTranzactie(Tranzactie tranzactie) {
        return String.format(Locale.US, "[%d] %s %s: %.2f RON | %s -> %s",
                tranzactie.getId(),
                tranzactie.getData(),
                tranzactie.getTip(),
                tranzactie.getSuma(),
                tranzactie.getContSursa(),
                tranzactie.getContDestinatie());
    }
}
