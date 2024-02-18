package com.alten.test.demo.utils;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;

public class SequenceManager {
    private static final String SEQUENCES_FILE = "src/main/resources/sequences.json";
    private static final String PRODUCT_SEQUENCE_NAME = "product_sequence";

    public static int getNextSequence() {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(SEQUENCES_FILE);
            JSONObject sequences = (JSONObject) parser.parse(reader);
            int nextValue = ((Integer) sequences.get(PRODUCT_SEQUENCE_NAME)).intValue();
            sequences.put(PRODUCT_SEQUENCE_NAME, nextValue + 1);
            FileWriter writer = new FileWriter(SEQUENCES_FILE);
            writer.write(sequences.toJSONString());
            writer.close();
            return nextValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
