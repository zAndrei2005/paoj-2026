package com.pao.laboratory06.exercise3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersoanaJuridica extends Persoana implements PlataOnlineSMS {
    private final String userValid;
    private final String parolaValida;
    private final List<String> smsTrimise;
    private double sold;
    private boolean autentificat;

    public PersoanaJuridica(String nume, String prenume, String telefon, String userValid, String parolaValida, double soldInitial) {
        super(nume, prenume, telefon);
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
        this.smsTrimise = new ArrayList<>();
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
    public boolean trimiteSMS(String mesaj) {
        if (mesaj == null || mesaj.isBlank()) {
            return false;
        }
        if (getTelefon() == null || getTelefon().isBlank()) {
            return false;
        }
        smsTrimise.add(mesaj);
        return true;
    }

    public List<String> getSmsTrimise() {
        return Collections.unmodifiableList(smsTrimise);
    }
}

