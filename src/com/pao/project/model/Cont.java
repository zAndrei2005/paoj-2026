package com.pao.project.model;

public abstract class Cont {
    protected String iban;
    protected double sold;
    protected String moneda;


    public Cont(String iban, double sold){
        this.iban = iban;
        this.sold = sold;
    }

    public String getIban() { return iban; }
    public double getSold() { return sold; }
    public String getMoneda() { return moneda; }

    public void setIban(String ibanNou) { this.iban = ibanNou; }
    public void setSold(double soldNou) {
        if(soldNou > 0)
            this.sold = soldNou;
        else
            System.out.print("Soldul nu poate fi negativ! \n");
    }

    public abstract void retragere(double suma);

    public void depunere(double suma) {
        this.sold += suma;
    }
}