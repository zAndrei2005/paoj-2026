package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        List<Tranzactie> tranzactii = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());
            tranzactii.add(new Tranzactie(id, suma, data, tip));
        }

        Comparator<Tranzactie> cmpSuma = Comparator.comparingDouble(Tranzactie::getSuma);

        while (scanner.hasNext()) {
            String comanda = scanner.next();

            switch (comanda) {
                case "UNIQUE_IDS": {
                    LinkedHashSet<Integer> uniqueIds = new LinkedHashSet<>();
                    for (Tranzactie tranzactie : tranzactii) {
                        uniqueIds.add(tranzactie.getId());
                    }
                    System.out.println("IDs unice (" + uniqueIds.size() + "): " + uniqueIds);
                    break;
                }
                case "MONTHLY_REPORT": {
                    TreeMap<String, double[]> raport = new TreeMap<>();
                    for (Tranzactie tranzactie : tranzactii) {
                        String luna = tranzactie.getData().substring(0, 7);
                        double[] sume = raport.computeIfAbsent(luna, key -> new double[2]);
                        if (tranzactie.getTip() == TipTranzactie.CREDIT) {
                            sume[0] += tranzactie.getSuma();
                        } else {
                            sume[1] += tranzactie.getSuma();
                        }
                    }
                    for (Map.Entry<String, double[]> entry : raport.entrySet()) {
                        double[] sume = entry.getValue();
                        System.out.printf(Locale.US, "%s: CREDIT %.2f RON, DEBIT %.2f RON%n", entry.getKey(), sume[0], sume[1]);
                    }
                    break;
                }
                case "TOP": {
                    int topN = scanner.nextInt();
                    List<Tranzactie> copie = new ArrayList<>(tranzactii);
                    copie.sort(cmpSuma.reversed());

                    System.out.println("Top " + topN + ":");
                    int limita = Math.min(topN, copie.size());
                    for (int i = 0; i < limita; i++) {
                        System.out.println(copie.get(i));
                    }
                    break;
                }
                case "SORT_ASC": {
                    tranzactii.sort(cmpSuma);
                    printLista(tranzactii);
                    break;
                }
                case "SORT_DESC": {
                    tranzactii.sort(cmpSuma.reversed());
                    printLista(tranzactii);
                    break;
                }
                case "REVERSE": {
                    Collections.reverse(tranzactii);
                    printLista(tranzactii);
                    break;
                }
                case "MIN_MAX": {
                    if (tranzactii.isEmpty()) {
                        break;
                    }
                    Tranzactie min = Collections.min(tranzactii, cmpSuma);
                    Tranzactie max = Collections.max(tranzactii, cmpSuma);
                    System.out.println("MIN: " + min);
                    System.out.println("MAX: " + max);
                    break;
                }
                case "CME_DEMO": {
                    try {
                        for (Tranzactie tranzactie : tranzactii) {
                            tranzactii.remove(tranzactie);
                        }
                    } catch (ConcurrentModificationException exception) {
                        System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }

    private static void printLista(List<Tranzactie> tranzactii) {
        String output = tranzactii.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        if (!output.isEmpty()) {
            System.out.println(output);
        }
    }
}
