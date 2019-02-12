package com.data.reader.app;

import com.data.reader.util.FileReader;

import java.io.File;

/**
 * This program load a text file and returns
 * the output according to the search criteria to STDOUT
 *
 * @author Armando Maso
 * @version 1.0
 *
 */
public class Main {

    /**
     * Init app for search by criteria
     * @param args
     * fileName = args[0], path to load the text file
     * argument = args[1], must be the value {ID} or {CITY} to specify the search field
     * searchCriteria = args[2], must be {ID value} or {CITY name} for specify the field value to search
     */
    public static void main(String[] args) {
        if (args.length == 3) {
            String fileName = args[0];
            String argument = args[1].toUpperCase();
            String searchCriteria = args[2].toUpperCase();

            File file = new File(fileName);
            if (file.exists())
                FileReader.readFile(fileName, argument, searchCriteria);
            else
                System.out.println("Please, you must provide a valid file to process!");

        } else {
            System.out.println("Please, you must enter all the necessary arguments!");
        }
    }
}
