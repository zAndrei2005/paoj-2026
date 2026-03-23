package com.pao.laboratory05.biblioteca;

import java.util.Arrays;
import java.util.Comparator;

public class BibliotecaService {
    private Carte[] carti;

    private BibliotecaService() {
        this.carti = new Carte[0];
    }

    private static class Holder {
        private static final BibliotecaService INSTANCE = new BibliotecaService();
    }

    public static BibliotecaService getInstance() {
        return Holder.INSTANCE;
    }

    public void addCarte(Carte c){
        carti = Arrays.copyOf(this.carti, this.carti.length + 1);
        carti[carti.length - 1] = c;

        System.out.println("Am adaugat cartea " + c);
    }

    public void listSortedByRating() {
        Carte[] copy = Arrays.copyOf(this.carti, this.carti.length);
        Arrays.sort(copy);
        for(Carte c : copy)
            System.out.println("Carte " + c);
    }

    public void listSortedBy(Comparator<Carte> comparator){
        Carte[] copy = Arrays.copyOf(this.carti, this.carti.length);
        Arrays.sort(copy, comparator);
        for(Carte c : copy)
            System.out.println("Carte " + c);
    }
}