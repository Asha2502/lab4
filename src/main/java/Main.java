import generated.Medicine;
import parsers.MyDOMParser;
import parsers.MySAXParser;
import parsers.MyStAXParser;
import validator.XMLValidator;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String xmlFilePath = "src/main/resources/xml/medicines.xml";
        String xsdFilePath = "src/main/resources/medicines.xsd";

        //Validator checking
        XMLValidator.validateXML(xsdFilePath, xmlFilePath);

        MySAXParser saxParser = new MySAXParser();
        List<Medicine.Drug> saxList = saxParser.parse(xmlFilePath);

//        System.out.println("SAX Parsed Components:");
//        saxList.forEach(System.out::println);

        MyDOMParser domParser = new MyDOMParser();
        List<Medicine.Drug> domList = domParser.parse(xmlFilePath);

//        System.out.println("DOM Parsed Components:");
//        domList.forEach(System.out::println);

        MyStAXParser stAXParser = new MyStAXParser();
        List<Medicine.Drug> staxList = stAXParser.parse(xmlFilePath);

        System.out.println("StAX Parsed Components:");
        staxList.forEach(System.out::println);


    }
}