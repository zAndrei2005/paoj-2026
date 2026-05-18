package com.pao.laboratory06.exercise3;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Inginer[] ingineri = {
                new Inginer("Ionescu", "Mara", "0711000001", 9200, "mara", "parola1", 2500),
                new Inginer("Popa", "Dan", "0711000002", 8100, "dan", "parola2", 3000),
                new Inginer("Georgescu", "Ana", "0711000003", 10400, "ana", "parola3", 4500)
        };

        Arrays.sort(ingineri);
        System.out.println("Sortare naturala (dupa nume):");
        for (Inginer inginer : ingineri) {
            System.out.println(inginer);
        }

        Arrays.sort(ingineri, new ComparatorInginerSalariu());
        System.out.println("\nSortare alternativa (dupa salariu descrescator):");
        for (Inginer inginer : ingineri) {
            System.out.println(inginer);
        }

        PlataOnline accesPrinInterfata = ingineri[0];
        accesPrinInterfata.autentificare("ana", "parola3");
        System.out.println("\nSold prin referinta PlataOnline: " + accesPrinInterfata.consultareSold());
        System.out.println("Plata 500 reusita? " + accesPrinInterfata.efectuarePlata(500));

        PersoanaJuridica firmaCuTelefon = new PersoanaJuridica("Tech", "Solutions", "0722000001", "firma1", "pwd1", 9000);
        PersoanaJuridica firmaFaraTelefon = new PersoanaJuridica("NoPhone", "SRL", "", "firma2", "pwd2", 6000);

        PlataOnlineSMS smsClient = firmaCuTelefon;
        smsClient.autentificare("firma1", "pwd1");
        System.out.println("\nSMS valid trimis? " + smsClient.trimiteSMS("Plata procesata cu succes."));
        System.out.println("SMS invalid (gol) trimis? " + smsClient.trimiteSMS("   "));
        System.out.println("Mesaje salvate pentru firmaCuTelefon: " + firmaCuTelefon.getSmsTrimise());

        System.out.println("\nSMS pentru firma fara telefon? " + firmaFaraTelefon.trimiteSMS("Confirmare."));

        System.out.println("\nConstanta financiara TVA: " + ConstanteFinanciare.TVA.getValoare());

        try {
            trimiteSmsGeneric(ingineri[1], "Acest SMS trebuie sa pice.");
        } catch (UnsupportedOperationException ex) {
            System.out.println("Eroare asteptata (entitate fara SMS): " + ex.getMessage());
        }

        try {
            accesPrinInterfata.autentificare(null, "orice");
        } catch (IllegalArgumentException ex) {
            System.out.println("Eroare asteptata la autentificare: " + ex.getMessage());
        }
    }

    private static boolean trimiteSmsGeneric(PlataOnline client, String mesaj) {
        if (!(client instanceof PlataOnlineSMS clientSms)) {
            throw new UnsupportedOperationException("Entitatea nu are capabilitate SMS.");
        }
        return clientSms.trimiteSMS(mesaj);
    }
}
