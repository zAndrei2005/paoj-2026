package com.pao.dto;

public class AccountWithClient {
    public final String iban;
    public final double sold;
    public final String clientCnp;
    public final String clientName;

    public AccountWithClient(String iban, double sold, String clientCnp, String clientName) {
        this.iban = iban;
        this.sold = sold;
        this.clientCnp = clientCnp;
        this.clientName = clientName;
    }
}

