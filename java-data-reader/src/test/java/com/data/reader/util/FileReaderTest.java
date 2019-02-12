package com.data.reader.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Class for test parser process
 *
 * @author Armando Maso
 */

public class FileReaderTest {

    private static String ARGUMENT_ID = "ID";
    private static String ARGUMENT_CITY = "CITY";
    private static String SEARCH_CRITERIA_ID = "10863096N";
    private static String SEARCH_CRITERIA_CITY = "LISBOA";
    private static String DATA_FILE = "src/test/java/data.txt";
    private static String DATA_ENTRY_ONE = "D Johnathan Cooper,BARCELONA,10863096N";
    private static String DATA_ENTRY_TWO = "D Taylor Matthews ; LISBOA ; 58202263-G";
    private static String DATA_ENTRY_FORMATED_ONE = "Johnathan Cooper,BARCELONA,10863096N";
    private static String DATA_ENTRY_FORMATED_TWO = "Taylor Matthews,LISBOA,58202263G";
    private static String FORMAT_F1 = "F1";
    private static String FORMAT_F2 = "F2";

    /**
     * Method for test data load to parser by {ID} criteria
     */
    @Test
    public void testReadFileById() {
        FileReader.readFile(DATA_FILE, ARGUMENT_ID, SEARCH_CRITERIA_ID);
    }

    /**
     * Method for test data load to parser by {CITY} criteria
     */
    @Test
    public void testReadFileByCity() {
        FileReader.readFile(DATA_FILE, ARGUMENT_CITY, SEARCH_CRITERIA_CITY);
    }

    /**
     * Method for test data entry format
     */
    @Test
    public void testFormatingText() throws Exception {
        String textFormatedOne = FileReader.formatingText(DATA_ENTRY_ONE, FORMAT_F1);
        String textFormatedTwo = FileReader.formatingText(DATA_ENTRY_TWO, FORMAT_F2);
        Assert.assertEquals("process to formated text", DATA_ENTRY_FORMATED_ONE, textFormatedOne);
        Assert.assertEquals("process to formated text", DATA_ENTRY_FORMATED_TWO, textFormatedTwo);
    }

    /**
     * Methods for test print data by ID criteria
     */
    @Test
    public void printDataById() {
        FileReader.printDataByCriteria(DATA_ENTRY_FORMATED_ONE, ARGUMENT_ID, SEARCH_CRITERIA_ID);
    }

    /**
     * Methods for test print data by CITY criteria
     */
    @Test
    public void printDataByCity() {
        FileReader.printDataByCriteria(DATA_ENTRY_FORMATED_TWO, ARGUMENT_CITY, SEARCH_CRITERIA_CITY);
    }
}
