package com.pao.laboratory10.exercise3;

import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<TranzactieStream> tranzactii = Arrays.asList(
                new TranzactieStream(1, 1500.00, "2024-01-15", TipTranzactie.CREDIT, "CONT_A"),
                new TranzactieStream(2, 320.50, "2024-01-19", TipTranzactie.DEBIT, "CONT_B"),
                new TranzactieStream(3, 420.00, "2024-02-03", TipTranzactie.CREDIT, "CONT_C"),
                new TranzactieStream(4, 1200.00, "2024-02-10", TipTranzactie.DEBIT, "CONT_A"),
                new TranzactieStream(5, 200.00, "2024-02-26", TipTranzactie.CREDIT, "CONT_B"),
                new TranzactieStream(6, 860.00, "2024-03-01", TipTranzactie.DEBIT, "CONT_D"),
                new TranzactieStream(7, 980.00, "2024-03-07", TipTranzactie.CREDIT, "CONT_C"),
                new TranzactieStream(8, 110.00, "2024-03-12", TipTranzactie.DEBIT, "CONT_B"),
                new TranzactieStream(9, 610.00, "2024-04-05", TipTranzactie.CREDIT, "CONT_E"),
                new TranzactieStream(10, 270.00, "2024-04-17", TipTranzactie.DEBIT, "CONT_A")
        );

        System.out.println("1) Tranzactii CREDIT:");
        tranzactii.stream()
                .filter(t -> t.tip == TipTranzactie.CREDIT)
                .forEach(System.out::println);

        System.out.println("2) Total procesat:");
        double total = tranzactii.stream().mapToDouble(t -> t.suma).sum();
        System.out.printf(Locale.US, "Total procesat: %.2f RON%n", total);

        System.out.println("3) Total per luna:");
        Map<String, Double> totalPeLuna = tranzactii.stream()
                .collect(Collectors.groupingBy(
                        t -> t.data.substring(0, 7),
                        TreeMap::new,
                        Collectors.summingDouble(t -> t.suma)
                ));
        totalPeLuna.forEach((luna, suma) -> System.out.printf(Locale.US, "%s: %.2f RON%n", luna, suma));

        System.out.println("4) Top 3 tranzactii:");
        tranzactii.stream()
                .sorted(Comparator.comparingDouble((TranzactieStream t) -> t.suma).reversed())
                .limit(3)
                .forEach(System.out::println);

        System.out.println("5) Conturi sursa unice:");
        List<String> conturiUnice = tranzactii.stream()
                .map(t -> t.contSursa)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Conturi sursa unice: " + conturiUnice);

        System.out.println("6) Suma medie:");
        double medie = tranzactii.stream().mapToDouble(t -> t.suma).average().orElse(0.0);
        System.out.printf(Locale.US, "Suma medie: %.2f RON%n", medie);

        System.out.println("7) Extras de cont lunar:");
        Map<String, List<TranzactieStream>> extrasPeLuna = tranzactii.stream()
                .collect(Collectors.groupingBy(t -> t.data.substring(0, 7), TreeMap::new, Collectors.toList()));
        extrasPeLuna.forEach((luna, lista) -> {
            double totalLunar = lista.stream().mapToDouble(t -> t.suma).sum();
            System.out.printf(Locale.US, "EXTRAS DE CONT - %s: %d tranzactii, total: %.2f RON%n", luna, lista.size(), totalLunar);
        });
    }

    private static class TranzactieStream {
        private final int id;
        private final double suma;
        private final String data;
        private final TipTranzactie tip;
        private final String contSursa;

        private TranzactieStream(int id, double suma, String data, TipTranzactie tip, String contSursa) {
            this.id = id;
            this.suma = suma;
            this.data = data;
            this.tip = tip;
            this.contSursa = contSursa;
        }

        @Override
        public String toString() {
            return String.format(Locale.US, "[%d] %s %s: %.2f RON (%s)", id, data, tip, suma, contSursa);
        }
    }
}
