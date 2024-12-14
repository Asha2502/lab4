import generated.Medicine;
import parsers.MySAXParser;

import javax.swing.*;
import java.io.PrintStream;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String xmlFilePath = "src/main/java/xml/medicines.xml";
        String xsdFilePath = "src/main/resources/medicines.xsd";

        MySAXParser saxParser = new MySAXParser();
        List<Medicine.Drug> saxList = saxParser.parse(xmlFilePath);

        System.out.println("SAX Parsed Components:");
        saxList.forEach(System.out::println);
    }
}