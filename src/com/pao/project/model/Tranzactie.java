package com.pao.project.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tranzactie implements Comparable<Tranzactie>{
    private int id;
    private double suma;
    private String tip;
    private LocalDateTime data;

    public Tranzactie(int id, double suma, String tip){
        this.id = id;
        this.suma = suma;
        this.tip = tip;
        this.data = LocalDateTime.now();
    }

    @Override
    public int compareTo(Tranzactie altaTranzactie){
        int compareData = altaTranzactie.data.compareTo(this.data);

        if(compareData == 0)
            return Integer.compare(this.id, altaTranzactie.id);

        return compareData;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dataFormatata = data.format(formatter);

        return "Tranzactia #" + id + " | Tip: " + tip + " | Suma: " + suma + " RON | Data: " + dataFormatata;
    }

    public int getId() { return id; }
    public double getSuma() { return suma; }
    public String getTip() { return tip; }
    public LocalDateTime getData() { return data; }
}