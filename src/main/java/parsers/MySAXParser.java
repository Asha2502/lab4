package parsers;

import generated.Medicine;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.xml.datatype.DatatypeFactory.newInstance;

/**
 * Чтение XML документа, используя SAX парсер
 */

public class MySAXParser implements Parser {
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

                Medicine.Drug currentMedicine = null;
                List<String> currentAnalogs = null;
                Medicine.Drug.Versions.Version currentVersion = null;
                Medicine.Drug.Versions.Version.Manufacturer currentManufacturer = null;
                Medicine.Drug.Versions.Version.Manufacturer.Certificate currentCertificate = null;
                Medicine.Drug.Versions.Version.Manufacturer.Package currentPackage = null;
                Medicine.Drug.Versions.Version.Manufacturer.Dosage currentDosage = null;

                final StringBuilder currentValue = new StringBuilder();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    switch (qName) {
                        case "Drug":
                            currentMedicine = new Medicine.Drug();
                            break;
                        case "Analogs":
                            currentAnalogs = new ArrayList<>();
                            break;
                        case "Version":
                            currentVersion = new Medicine.Drug.Versions.Version();
                            break;
                        case "Manufacturer":
                            currentManufacturer = new Medicine.Drug.Versions.Version.Manufacturer();
                            break;
                        case "Certificate":
                            currentCertificate = new Medicine.Drug.Versions.Version.Manufacturer.Certificate();
                            break;
                        case "Package":
                            currentPackage = new Medicine.Drug.Versions.Version.Manufacturer.Package();
                            break;
                        case "Dosage":
                            currentDosage = new Medicine.Drug.Versions.Version.Manufacturer.Dosage();
                            break;
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
                            case "Analog":
                                if (currentAnalogs != null) {
                                    currentAnalogs.add(currentValue.toString());
                                }
                                break;
                            case "Analogs":
                                Medicine.Drug.Analogs analogs = new Medicine.Drug.Analogs();
                                analogs.getAnalog().addAll(currentAnalogs);
                                currentMedicine.setAnalogs(analogs);
                                currentAnalogs = null;
                                break;
                            case "IssueDate":
                                try {
                                    currentCertificate.setIssueDate(newInstance().newXMLGregorianCalendar(currentValue.toString()));
                                } catch (DatatypeConfigurationException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case "ExpiryDate":
                                try {
                                    currentCertificate.setExpiryDate(newInstance().newXMLGregorianCalendar(currentValue.toString()));
                                } catch (DatatypeConfigurationException e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case "Organization":
                                currentCertificate.setOrganization(currentValue.toString());
                                break;
                            case "Number":
                                currentCertificate.setNumber(currentValue.toString());
                                break;
                            case "Type":
                                if (currentPackage != null) currentPackage.setType(currentValue.toString());
                                else currentVersion.setType(currentValue.toString());
                                break;
                            case "Quantity":
                                currentPackage.setQuantity(Integer.parseInt(currentValue.toString()));
                                break;
                            case "Price":
                                currentPackage.setPrice(new BigDecimal(currentValue.toString()));
                                break;
                            case "Amount":
                                currentDosage.setAmount(currentValue.toString());
                                break;
                            case "Frequency":
                                currentDosage.setFrequency(currentValue.toString());
                                break;
                            case "Dosage":
                                currentManufacturer.setDosage(currentDosage);
                                currentDosage = null;
                                break;
                            case "Package":
                                currentManufacturer.set_package(currentPackage);
                                currentPackage = null;
                                break;
                            case "Certificate":
                                currentManufacturer.setCertificate(currentCertificate);
                                currentCertificate = null;
                                break;
                            case "Manufacturer":
                                currentVersion.setManufacturer(currentManufacturer);
                                currentManufacturer = null;
                                break;
                            case "Version":
                                if (currentMedicine.getVersions() == null) {
                                    currentMedicine.setVersions(new Medicine.Drug.Versions());
                                }
                                currentMedicine.getVersions().getVersion().add(currentVersion);
                                currentVersion = null;
                                break;
                            case "Drug":
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
