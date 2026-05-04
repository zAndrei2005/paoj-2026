package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;
    private static final ByteOrder ORDER = ByteOrder.LITTLE_ENDIAN;

    private enum Status {
        PENDING,
        PROCESSED,
        REJECTED
    }

    private static final class Inregistrare {
        private final int id;
        private final double suma;
        private final String data;
        private final TipTranzactie tip;
        private final Status status;

        private Inregistrare(int id, double suma, String data, TipTranzactie tip, Status status) {
            this.id = id;
            this.suma = suma;
            this.data = data;
            this.tip = tip;
            this.status = status;
        }
    }

    public static void main(String[] args) throws Exception {
        if (args != null && args.length > 0) {
            System.out.print("");
        }

        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        int n = scanner.nextInt();
        List<Inregistrare> inregistrari = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());
            inregistrari.add(new Inregistrare(id, suma, data, tip, Status.PENDING));
        }

        java.nio.file.Path outputPath = Paths.get(OUTPUT_FILE);
        java.nio.file.Path parent = outputPath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            for (Inregistrare inregistrare : inregistrari) {
                out.write(encodeRecord(inregistrare));
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (scanner.hasNext()) {
                String comanda = scanner.next();
                if ("READ".equals(comanda)) {
                    int idx = scanner.nextInt();
                    Inregistrare inregistrare = readRecord(raf, idx);
                    if (inregistrare != null) {
                        System.out.println(formatRecord(idx, inregistrare));
                    }
                } else if ("UPDATE".equals(comanda)) {
                    int idx = scanner.nextInt();
                    Status status = Status.valueOf(scanner.next());
                    updateStatus(raf, idx, status);
                    System.out.println("Updated [" + idx + "]: " + status);
                } else if ("PRINT_ALL".equals(comanda)) {
                    for (int idx = 0; idx < inregistrari.size(); idx++) {
                        Inregistrare inregistrare = readRecord(raf, idx);
                        if (inregistrare != null) {
                            System.out.println(formatRecord(idx, inregistrare));
                        }
                    }
                }
            }
        }
    }

    private static byte[] encodeRecord(Inregistrare inregistrare) {
        ByteBuffer buffer = ByteBuffer.allocate(RECORD_SIZE).order(ORDER);
        buffer.putInt(inregistrare.id);
        buffer.putDouble(inregistrare.suma);

        byte[] dataBytes = new byte[10];
        Arrays.fill(dataBytes, (byte) ' ');
        byte[] rawData = inregistrare.data.getBytes(StandardCharsets.US_ASCII);
        System.arraycopy(rawData, 0, dataBytes, 0, Math.min(rawData.length, dataBytes.length));
        buffer.position(12);
        buffer.put(dataBytes);
        buffer.put(22, (byte) (inregistrare.tip == TipTranzactie.CREDIT ? 0 : 1));
        buffer.put(23, statusCode(inregistrare.status));
        return buffer.array();
    }

    private static Inregistrare readRecord(RandomAccessFile raf, int idx) throws IOException {
        if (idx < 0 || (long) idx * RECORD_SIZE >= raf.length()) {
            return null;
        }

        byte[] bytes = new byte[RECORD_SIZE];
        raf.seek((long) idx * RECORD_SIZE);
        raf.readFully(bytes);

        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ORDER);
        int id = buffer.getInt();
        double suma = buffer.getDouble();

        byte[] dataBytes = new byte[10];
        buffer.position(12);
        buffer.get(dataBytes);
        String data = new String(dataBytes, StandardCharsets.US_ASCII).trim();

        TipTranzactie tip = buffer.get(22) == 0 ? TipTranzactie.CREDIT : TipTranzactie.DEBIT;
        Status status = statusFromCode(buffer.get(23));
        return new Inregistrare(id, suma, data, tip, status);
    }

    private static void updateStatus(RandomAccessFile raf, int idx, Status status) throws IOException {
        if (idx < 0 || (long) idx * RECORD_SIZE >= raf.length()) {
            return;
        }
        raf.seek((long) idx * RECORD_SIZE + 23);
        raf.writeByte(statusCode(status));
    }

    private static String formatRecord(int idx, Inregistrare inregistrare) {
        return String.format(Locale.US, "[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s",
                idx,
                inregistrare.id,
                inregistrare.data,
                inregistrare.tip,
                inregistrare.suma,
                inregistrare.status);
    }

    private static byte statusCode(Status status) {
        if (status == Status.PROCESSED) {
            return 1;
        }
        if (status == Status.REJECTED) {
            return 2;
        }
        return 0;
    }

    private static Status statusFromCode(byte code) {
        if (code == 1) {
            return Status.PROCESSED;
        }
        if (code == 2) {
            return Status.REJECTED;
        }
        return Status.PENDING;
    }
}
