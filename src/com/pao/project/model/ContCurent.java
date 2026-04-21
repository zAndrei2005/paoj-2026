package com.pao.project.model;

public class ContCurent extends Cont {
    private double limitaDescoperit;

    public ContCurent(String iban, double sold, double limitaDescoperit){
        super(iban, sold);
        this.limitaDescoperit = limitaDescoperit;
    }
    @Override
    public void retragere(double suma){
        if(suma <= suma + limitaDescoperit) {
            sold -= suma;
            System.out.println("Retragere reusita din cont: " + suma);
        }
        else
            System.out.println("Fonduri insuficiente (inclusiv limita descoperit)");
    }

    public double getLimitaDescoperit() { return limitaDescoperit; }
}