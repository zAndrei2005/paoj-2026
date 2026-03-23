package com.pao.laboratory05.angajati;

import java.util.Arrays;

public class AngajatService{
    private Angajat[] angajati;

    private AngajatService(){
        this.angajati = new Angajat[0];
    }

    private static class Holder {
        private static final AngajatService INSTANCE = new AngajatService();
    }

    public static AngajatService getInstance() {
        return Holder.INSTANCE;
    }

    public void addAngajat(Angajat a){
        this.angajati = Arrays.copyOf(this.angajati, this.angajati.length + 1);
        this.angajati[this.angajati.length - 1] = a;
        System.out.println("Angajat adaugat " + a.getNume());
    }

    public void printAll(){
        for (int i = 0; i < angajati.length; i++){
            System.out.println("Nume: " + angajati[i].getNume());
        }
    }

    public void listBySalary(){
        Angajat[] copie = Arrays.copyOf(this.angajati,this.angajati.length);
        Arrays.sort(copie);
        for(int i = 0; i < copie.length; i++){
            System.out.println("Nume: " + copie[i].getNume());
        }
    }

    public void findByDepartament(String numeDept){
        int ok = 0;
        for(Angajat angajat : angajati){
            if(angajat.getDepartament().nume().equalsIgnoreCase(numeDept)){
                System.out.println("Nume: " + angajat.getNume());
                ok = 1;
            }
        }
        if (ok == 0)
            System.out.println("Nu exista angajati in acest departament " + numeDept);
    }
}