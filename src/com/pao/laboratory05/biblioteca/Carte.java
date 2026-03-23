package com.pao.laboratory05.biblioteca;

public class Carte implements Comparable<Carte> {
    private String titlu, autor;
    private int an;
    private double rating;

    public int getAn() {return an;}
    public String getAutor() {return autor;}
    public String getTitlu() {return titlu;}

    public Carte(String titlu, String autor, int an, double rating){
        this.titlu = titlu;
        this.autor = autor;
        this.an = an;
        this.rating = rating;
    }

    @Override
    public String toString(){
        return "Carte{titlu= " + titlu + "autor= " + autor + " an= " + an + " rating= " + rating;
    }

    @Override
    public int compareTo(Carte c){
        return Double.compare(c.rating, this.rating);
    }
}