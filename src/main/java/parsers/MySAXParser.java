package parsers;

import generated.Medicine;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Чтение XML документа, используя SAX парсер
*/

public class MySAXParser implements Parser{
    private final List<Medicine.Drug> medicines;

    public MySAXParser() {
        medicines = new ArrayList<>();
    }

    @Override
    public List<Medicine.Drug> parse(String xmlFilePath) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                Medicine.Drug currentMedicine;
                final StringBuilder currentValue = new StringBuilder();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if ("Medicine".equalsIgnoreCase(qName)) {
                        currentMedicine = new Medicine.Drug();
                    }
                    currentValue.setLength(0);
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    currentValue.append(new String(ch, start, length).trim());
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    if (currentMedicine != null) {
                        switch (qName) {
                            case "Name":
                                currentMedicine.setName(currentValue.toString());
                                break;
                            case "Pharm":
                                currentMedicine.setPharm(currentValue.toString());
                                break;
                            case "Group":
                                currentMedicine.setGroup(currentValue.toString());
                                break;
                            case "Medicine":
                                medicines.add(currentMedicine);
                                currentMedicine = null;
                                break;
                        }
                    }
                }
            };

            saxParser.parse(new File(xmlFilePath), handler);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return medicines;
    }
}
