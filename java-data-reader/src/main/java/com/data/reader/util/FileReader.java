package com.data.reader.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * This class allows to parse the data
 * loaded from the text file
 *
 * @author Armando Maso
 */
public class FileReader {

    private static int MATCH = 0;
    private static String CITY_CRITERIA = "CITY";
    private static String ID_CRITERIA = "ID";
    private static String FORMAT = "F";
    private static String FORMAT_F1 = "F1";
    private static String FORMAT_F2 = "F2";
    private static String DATA_LINE = "D ";
    private static String SEPARATOR_CODE = "-";
    private static String SEPARATOR_COMMA = ",";
    private static String SEPARATOR_SEMICOLON = " ; ";

    /**
     * Method for parse input data from file text
     *
     * @param file
     * @param argument
     * @param searchCriteria
     */
    public static void readFile(String file, String argument, String searchCriteria) {
        try {
            String format = "";
            MATCH = 0;
            InputStream inputStream = new FileInputStream(file);
            Scanner fileScanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
            while (fileScanner.hasNextLine()) {
                String data = fileScanner.nextLine();
                if (data.startsWith(FORMAT)) {
                    format = data.trim();
                    continue;
                }
                if (data.startsWith(DATA_LINE)) {
                    try {
                        String infoParsed = formatingText(data, format);
                        printDataByCriteria(infoParsed, argument, searchCriteria);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.printf("%d matches for argument %s and search criteria %s", MATCH, argument, searchCriteria);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to format data entry
     *
     * @param data
     * @param format
     * @return data entry formatted
     */
    public static String formatingText(String data, String format) throws Exception {
        StringBuilder dataBuilder = new StringBuilder(data);
        dataBuilder.deleteCharAt(dataBuilder.indexOf(DATA_LINE));
        if (format.equals(FORMAT_F2)) {
            dataBuilder.deleteCharAt(dataBuilder.lastIndexOf(SEPARATOR_CODE));
            data = dataBuilder.toString().replace(SEPARATOR_SEMICOLON, SEPARATOR_COMMA);
        } else {
            data = dataBuilder.toString();
        }
        return data.trim();
    }

    /**
     * Method for print data
     *
     * @param info
     * @param arguments
     * @param searchCriteria
     */
    public static void printDataByCriteria(String info, String arguments, String searchCriteria) {
        if (arguments.equals(CITY_CRITERIA) || arguments.equals(ID_CRITERIA)) {
            String[] data = info.split(",");
            if (data[1].equals(searchCriteria) || data[2].equals(searchCriteria)) {
                System.out.println(info.replace("," + searchCriteria, ""));
                MATCH++;
            }
        }
    }
}
