package com.pao.project.model;

public class ContEconomii extends Cont {
    private double rataDobanda;

    public ContEconomii(String iban, double sold, double rataDobanda){
        super(iban, sold);
        this.rataDobanda = rataDobanda;
    }

    public void aplicaDobanda() {
        double dobanda = getSold() * (rataDobanda / 100);
        setSold(getSold() + dobanda);
    }

    public double getRataDobanda() { return rataDobanda; }
    public void setRataDobanda(double rata) {
        this.rataDobanda = rata;
    }

    @Override
    public void retragere(double suma){
        if (suma <= 0) {
            System.out.println("Eroare: Suma retrasă trebuie să fie mai mare decât 0.");
            return;
        }

        if (this.sold >= suma) {
            this.sold -= suma;
            System.out.println("Succes: Ai retras " + suma + " RON din contul de economii.");
            System.out.println("Sold nou: " + this.sold + " RON.");
        } else {
            System.out.println("Eroare: Fonduri insuficiente! Sold disponibil: " + this.sold + " RON.");
        }
    }

    @Override
    public String toString() {
        return "ContEconomii{" +
                "iban='" + getIban() + '\'' +
                ", sold=" + getSold() +
                ", moneda='" + getMoneda() + '\'' +
                ", rataDobanzii=" + rataDobanda + "%" +
                '}';
    }
}