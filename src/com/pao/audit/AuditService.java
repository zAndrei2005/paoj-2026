package com.pao.audit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple thread-safe AuditService that appends action,timestamp,details to resources/audit.csv
 */
public class AuditService {
    private static final String AUDIT_PATH = "paoj-2026/resources/audit.csv";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private AuditService() {}

    private static class Holder {
        private static final AuditService INSTANCE = new AuditService();
    }

    public static AuditService getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void audit(String action, String details) {
        String ts = LocalDateTime.now().format(FMT);
        String line = String.format("%s,%s,%s%n", action, ts, escape(details));
        Path p = Paths.get(AUDIT_PATH);
        try {
            if (!Files.exists(p)) {
                Files.createDirectories(p.getParent());
                Files.createFile(p);
            }
            Files.writeString(p, line, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("Audit written: " + action);
        } catch (IOException e) {
            System.err.println("Failed to write audit log: " + e.getMessage());
        }
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replaceAll("[\\r\\n]", " ").replace(",", "_");
    }
}

