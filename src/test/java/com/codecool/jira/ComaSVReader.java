//package com.codecool;
//
//import com.opencsv.CSVReader;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class ComaSVReader {
//
//    public List<List<String>> readFromCsv(String fileName) {
//        List<List<String>> records = new ArrayList<>();
//        try (CSVReader csvReader = new CSVReader(new FileReader("src/test/resources/" + fileName));) {
//            String[] values = null;
//            while ((values = csvReader.readNext()) != null) {
//                records.add(Arrays.asList(values[0].split(";")));
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return records;
//    }
//}
