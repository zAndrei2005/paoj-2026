package com.pao.project.model;
import java.sql.SQLOutput;

public class Card {
    private String numarCard, pin;
    private boolean stareActiva;
    private String ibanContCard;

    public Card(String numarCard, String pin, String ibanContCard){
        this.numarCard = numarCard;
        this.pin = pin;
        this.ibanContCard = ibanContCard;
        this.stareActiva = true;
    }

    public void blocareCard(){
        this.stareActiva = false;
        System.out.println("Cardul " + numarCard + " a fost blocat.");
    }

    public boolean validarePin(String pinIntrodus){
        if(!stareActiva) {
            System.out.println("Cardul acesta este blocat.");
            return false;
        }
        return this.pin.equals(pinIntrodus);
    }

    public void schimbarePin(String pinNou, String pinVechi){
        if(validarePin(pinVechi))
        {
            this.pin = pinNou;
            System.out.println("Pinul a fost schimbat cu succes!");
        }
        else
            System.out.println("Pinul vechi este incorect!");
    }

    public String getNumarCard() { return numarCard; }
    public String getIbanContCard() { return ibanContCard; }
    public boolean isStareActiva() { return stareActiva; }
}