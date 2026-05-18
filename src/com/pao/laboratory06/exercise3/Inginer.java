package com.pao.laboratory06.exercise3;

public class Inginer extends Angajat implements PlataOnline, Comparable<Inginer> {
    private final String userValid;
    private final String parolaValida;
    private double sold;
    private boolean autentificat;

    public Inginer(String nume, String prenume, String telefon, double salariu, String userValid, String parolaValida, double soldInitial) {
        super(nume, prenume, telefon, salariu);
        if (userValid == null || userValid.isBlank()) {
            throw new IllegalArgumentException("User-ul implicit nu poate fi null/gol.");
        }
        if (parolaValida == null || parolaValida.isBlank()) {
            throw new IllegalArgumentException("Parola implicita nu poate fi null/gol.");
        }
        if (soldInitial < 0) {
            throw new IllegalArgumentException("Soldul initial nu poate fi negativ.");
        }

        this.userValid = userValid;
        this.parolaValida = parolaValida;
        this.sold = soldInitial;
        this.autentificat = false;
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.isBlank() || parola == null || parola.isBlank()) {
            throw new IllegalArgumentException("User/parola nu pot fi null/goale.");
        }
        this.autentificat = userValid.equals(user) && parolaValida.equals(parola);
    }

    @Override
    public double consultareSold() {
        return sold;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (!autentificat || suma <= 0 || suma > sold) {
            return false;
        }
        sold -= suma;
        return true;
    }

    @Override
    public int compareTo(Inginer altInginer) {
        if (altInginer == null) {
            throw new IllegalArgumentException("Inginerul comparat nu poate fi null.");
        }
        return this.getNume().compareToIgnoreCase(altInginer.getNume());
    }

    @Override
    public String toString() {
        return "Inginer{" +
                "nume='" + getNume() + '\'' +
                ", prenume='" + getPrenume() + '\'' +
                ", salariu=" + getSalariu() +
                ", sold=" + sold +
                '}';
    }
}


