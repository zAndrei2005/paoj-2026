package com.pao.dto;

public class CardDetail {
    public final String numarCard;
    public final boolean stareActiva;
    public final String ibanCont;
    public final String clientName;

    public CardDetail(String numarCard, boolean stareActiva, String ibanCont, String clientName) {
        this.numarCard = numarCard;
        this.stareActiva = stareActiva;
        this.ibanCont = ibanCont;
        this.clientName = clientName;
    }
}

