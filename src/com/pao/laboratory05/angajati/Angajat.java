package com.pao.laboratory05.angajati;

public class Angajat implements Comparable<Angajat>{
    private String nume;
    private Departament department;
    private double salariu;

    public Angajat(String nume, Departament department, double salariu){
        this.nume = nume;
        this.department = department;
        this.salariu = salariu;
    }

    public String getNume() { return nume; }
    public Departament getDepartament() { return department; }
    public double getSalariu() { return salariu; }

    @Override
    public String toString() {
        return "Angajat{" +
                "nume='" + nume + '\'' +
                ", departament=" + department +
                ", salariu=" + salariu +
                '}';
    }

    @Override
    public int compareTo(Angajat altAngajat){
        return Double.compare(altAngajat.salariu, this.salariu);
    }
}