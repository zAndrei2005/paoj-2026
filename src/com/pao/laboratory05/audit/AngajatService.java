package com.pao.laboratory05.audit;

import com.pao.laboratory05.angajati.Angajat;
import com.pao.laboratory05.angajati.Departament;

import java.time.LocalDateTime;
import java.util.Arrays;

public class AngajatService {
    private AuditEntry[] auditLog;
    private Angajat[] angajati;

    private AngajatService(){
        this.auditLog = new AuditEntry[0];
        this.angajati = new Angajat[0];
    }

    private static class Holder {
        private static final AngajatService INSTANCE = new AngajatService();
    }

    public static AngajatService getInstance() {
        return AngajatService.Holder.INSTANCE;
    }

    private void logAction(String action, String target) {
        AuditEntry entry = new AuditEntry(action, target, LocalDateTime.now().toString());

        auditLog = Arrays.copyOf(this.auditLog, this.auditLog.length + 1);
        auditLog[auditLog.length - 1] = entry;
    }

    public void addAngajat(Angajat a){
        angajati = Arrays.copyOf(this.angajati, this.angajati.length + 1);
        angajati[angajati.length - 1] = a;

        logAction("ADD", a.getNume());
    }

    public void findByDepartament(String numeDept){
        logAction("FIND_BY_DEPT", numeDept);
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

    public void printAuditLog(){
        for(AuditEntry a : auditLog){
            System.out.println(a);
        }
    }

    public void listBySalary(){
        Angajat[] copie = Arrays.copyOf(this.angajati,this.angajati.length);
        Arrays.sort(copie);
        for(int i = 0; i < copie.length; i++){
            System.out.println("Nume: " + copie[i].getNume());
        }
    }

}