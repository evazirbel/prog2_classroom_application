package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MajorMapReader {
    private File majorMapFile;

    public MajorMapReader(String fileName) {
        majorMapFile = new File(fileName);

    }
    public Map<String, String> readMap() {
        Map<String, String> majorMap = new HashMap<>();

        try(Scanner majorScanner = new Scanner(majorMapFile)){
            while(majorScanner.hasNextLine()){
                String line = majorScanner.nextLine();
                String[] tokens = line.split("\t");
                majorMap.put(tokens[0].trim(), tokens[1].trim());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Fehler: " + e.getMessage());
        }return majorMap;

    }



}
