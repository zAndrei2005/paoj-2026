package com.pao.laboratory05.audit;

import com.pao.laboratory05.angajati.Angajat;
import com.pao.laboratory05.angajati.Departament;

public record AuditEntry(String action, String target, String timestamp) { }