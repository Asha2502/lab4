package transform;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XSLTransformer {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("src/main/resources/xml/medicines.xml");
            File xslFile = new File("src/main/resources/xsl/medicines.xsl");

            File outputFile = new File("output.html");

            TransformerFactory factory = TransformerFactory.newInstance();

            Source xsl = new StreamSource(xslFile);
            Transformer transformer = factory.newTransformer(xsl);

            Source xml = new StreamSource(xmlFile);
            Result output = new StreamResult(outputFile);

            transformer.transform(xml, output);

            System.out.println("Трансформация завершена. Файл создан: output.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
