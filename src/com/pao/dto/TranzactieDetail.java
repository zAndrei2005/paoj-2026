package com.pao.dto;

import java.time.LocalDateTime;

public class TranzactieDetail {
    public final int id;
    public final String iban;
    public final double suma;
    public final String tip;
    public final LocalDateTime data;

    public TranzactieDetail(int id, String iban, double suma, String tip, LocalDateTime data) {
        this.id = id;
        this.iban = iban;
        this.suma = suma;
        this.tip = tip;
        this.data = data;
    }
}

