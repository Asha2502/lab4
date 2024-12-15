package parsers;

import generated.Medicine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MyDOMParser implements Parser {

    @Override
    public List<Medicine.Drug> parse(String xmlFilePath) {
        List<Medicine.Drug> drugs = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File(xmlFilePath));
            document.getDocumentElement().normalize();

            NodeList drugNodes = document.getElementsByTagName("Drug");

            for (int i = 0; i < drugNodes.getLength(); i++) {
                Node drugNode = drugNodes.item(i);

                if (drugNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element drugElement = (Element) drugNode;

                    Medicine.Drug drug = new Medicine.Drug();
                    drug.setName(getTagValue("Name", drugElement));
                    drug.setPharm(getTagValue("Pharm", drugElement));
                    drug.setGroup(getTagValue("Group", drugElement));

                    // Parse Analogs
                    NodeList analogNodes = drugElement.getElementsByTagName("Analog");
                    Medicine.Drug.Analogs analogs = new Medicine.Drug.Analogs();
                    for (int j = 0; j < analogNodes.getLength(); j++) {
                        analogs.getAnalog().add(analogNodes.item(j).getTextContent());
                    }
                    drug.setAnalogs(analogs);

                    // Parse Versions
                    NodeList versionNodes = drugElement.getElementsByTagName("Version");
                    Medicine.Drug.Versions versions = new Medicine.Drug.Versions();
                    for (int j = 0; j < versionNodes.getLength(); j++) {
                        Node versionNode = versionNodes.item(j);
                        if (versionNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element versionElement = (Element) versionNode;

                            Medicine.Drug.Versions.Version version = new Medicine.Drug.Versions.Version();
                            version.setType(getTagValue("Type", versionElement));

                            // Parse Manufacturer
                            Element manufacturerElement = (Element) versionElement.getElementsByTagName("Manufacturer").item(0);
                            Medicine.Drug.Versions.Version.Manufacturer manufacturer = new Medicine.Drug.Versions.Version.Manufacturer();

                            // Parse Certificate
                            Element certificateElement = (Element) manufacturerElement.getElementsByTagName("Certificate").item(0);
                            Medicine.Drug.Versions.Version.Manufacturer.Certificate certificate = new Medicine.Drug.Versions.Version.Manufacturer.Certificate();
                            certificate.setNumber(getTagValue("Number", certificateElement));
                            certificate.setIssueDate(javax.xml.datatype.DatatypeFactory.newInstance()
                                    .newXMLGregorianCalendar(getTagValue("IssueDate", certificateElement)));
                            certificate.setExpiryDate(javax.xml.datatype.DatatypeFactory.newInstance()
                                    .newXMLGregorianCalendar(getTagValue("ExpiryDate", certificateElement)));
                            certificate.setOrganization(getTagValue("Organization", certificateElement));
                            manufacturer.setCertificate(certificate);

                            // Parse Package
                            Element packageElement = (Element) manufacturerElement.getElementsByTagName("Package").item(0);
                            Medicine.Drug.Versions.Version.Manufacturer.Package _package = new Medicine.Drug.Versions.Version.Manufacturer.Package();
                            _package.setType(getTagValue("Type", packageElement));
                            _package.setQuantity(Integer.parseInt(getTagValue("Quantity", packageElement)));
                            _package.setPrice(new BigDecimal(getTagValue("Price", packageElement)));
                            manufacturer.set_package(_package);

                            // Parse Dosage
                            Element dosageElement = (Element) manufacturerElement.getElementsByTagName("Dosage").item(0);
                            Medicine.Drug.Versions.Version.Manufacturer.Dosage dosage = new Medicine.Drug.Versions.Version.Manufacturer.Dosage();
                            dosage.setAmount(getTagValue("Amount", dosageElement));
                            dosage.setFrequency(getTagValue("Frequency", dosageElement));
                            manufacturer.setDosage(dosage);


                            version.setManufacturer(manufacturer);

                            versions.getVersion().add(version);
                        }
                    }
                    drug.setVersions(versions);

                    drugs.add(drug);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drugs;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}
