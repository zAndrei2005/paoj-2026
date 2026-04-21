package com.pao.laboratory08.exercise1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";
    private static final String ALT_FILE_PATH = "paoj-2026/src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        List<Student> studenti = readStudents(resolveStudentsFilePath());
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextLine()) {
            return;
        }

        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            return;
        }

        String[] commandParts = line.split("\\s+", 2);
        String command = commandParts[0];

        if ("PRINT".equals(command)) {
            for (Student student : studenti) {
                System.out.println(student);
            }
            return;
        }

        if (commandParts.length < 2) {
            return;
        }

        String name = commandParts[1].trim();
        Student original = findByName(studenti, name);
        if (original == null) {
            return;
        }

        Student clone;
        if ("SHALLOW".equals(command)) {
            clone = original.shallowClone();
        } else if ("DEEP".equals(command)) {
            clone = original.deepClone();
        } else {
            return;
        }

        clone.getAdresa().setOras("MODIFICAT");
        System.out.println("Original: " + original);
        System.out.println("Clona: " + clone);
    }

    private static String resolveStudentsFilePath() throws FileNotFoundException {
        Path first = Paths.get(FILE_PATH);
        if (Files.exists(first)) {
            return first.toString();
        }

        Path second = Paths.get(ALT_FILE_PATH);
        if (Files.exists(second)) {
            return second.toString();
        }

        throw new FileNotFoundException(FILE_PATH + " (also tried " + ALT_FILE_PATH + ")");
    }

    private static List<Student> readStudents(String filePath) throws IOException {
        List<Student> studenti = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String csvLine = line.trim();
                if (csvLine.isEmpty()) {
                    continue;
                }

                String[] parts = csvLine.split(",", 4);
                String nume = parts[0].trim();
                int varsta = Integer.parseInt(parts[1].trim());
                String oras = parts[2].trim();
                String strada = parts[3].trim();
                studenti.add(new Student(nume, varsta, new Adresa(oras, strada)));
            }
        }
        return studenti;
    }

    private static Student findByName(List<Student> studenti, String name) {
        for (Student student : studenti) {
            if (student.getNume().equals(name)) {
                return student;
            }
        }
        return null;
    }
}
