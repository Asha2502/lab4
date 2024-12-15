package parsers;

import generated.Medicine;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MyStAXParser implements Parser{


    @Override
    public List<Medicine.Drug> parse(String xmlFilePath) {
        List<Medicine.Drug> drugs = new ArrayList<>();
        Medicine.Drug currentDrug = null;
        Medicine.Drug.Analogs analogs = null;
        Medicine.Drug.Versions versions = null;
        Medicine.Drug.Versions.Version currentVersion = null;
        Medicine.Drug.Versions.Version.Manufacturer manufacturer = null;
        Medicine.Drug.Versions.Version.Manufacturer.Certificate certificate = null;
        Medicine.Drug.Versions.Version.Manufacturer.Package _package = null;
        Medicine.Drug.Versions.Version.Manufacturer.Dosage dosage = null;

        try (InputStream inputStream = new FileInputStream(xmlFilePath)) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader reader = factory.createXMLEventReader(inputStream);

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String elementName = startElement.getName().getLocalPart();

                    switch (elementName) {
                        case "Drug":
                            currentDrug = new Medicine.Drug();
                            analogs = new Medicine.Drug.Analogs();
                            versions = new Medicine.Drug.Versions();
                            break;

                        case "Name":
                            currentDrug.setName(reader.getElementText());
                            break;

                        case "Pharm":
                            currentDrug.setPharm(reader.getElementText());
                            break;

                        case "Group":
                            currentDrug.setGroup(reader.getElementText());
                            break;

                        case "Analog":
                            analogs.getAnalog().add(reader.getElementText());
                            break;

                        case "Version":
                            currentVersion = new Medicine.Drug.Versions.Version();
                            manufacturer = new Medicine.Drug.Versions.Version.Manufacturer();
                            break;

                        case "Type":
                            if (currentVersion != null) {
                                currentVersion.setType(reader.getElementText());
                            } else if (_package != null) {
                                _package.setType(reader.getElementText());
                            }
                            break;

                        case "Certificate":
                            certificate = new Medicine.Drug.Versions.Version.Manufacturer.Certificate();
                            break;

                        case "Number":
                            certificate.setNumber(reader.getElementText());
                            break;

                        case "IssueDate":
                            certificate.setIssueDate(javax.xml.datatype.DatatypeFactory.newInstance()
                                    .newXMLGregorianCalendar(reader.getElementText()));
                            break;

                        case "ExpiryDate":
                            certificate.setExpiryDate(javax.xml.datatype.DatatypeFactory.newInstance()
                                    .newXMLGregorianCalendar(reader.getElementText()));
                            break;

                        case "Organization":
                            certificate.setOrganization(reader.getElementText());
                            break;

                        case "Package":
                            _package = new Medicine.Drug.Versions.Version.Manufacturer.Package();
                            break;

                        case "Quantity":
                            _package.setQuantity(Integer.parseInt(reader.getElementText()));
                            break;

                        case "Price":
                            _package.setPrice(new BigDecimal(reader.getElementText()));
                            break;

                        case "Dosage":
                            dosage = new Medicine.Drug.Versions.Version.Manufacturer.Dosage();
                            break;

                        case "Amount":
                            dosage.setAmount(reader.getElementText());
                            break;

                        case "Frequency":
                            dosage.setFrequency(reader.getElementText());
                            break;
                    }
                }

                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    String elementName = endElement.getName().getLocalPart();

                    switch (elementName) {
                        case "Drug":
                            currentDrug.setAnalogs(analogs);
                            currentDrug.setVersions(versions);
                            drugs.add(currentDrug);
                            break;

                        case "Version":
                            versions.getVersion().add(currentVersion);
                            break;

                        case "Certificate":
                            manufacturer.setCertificate(certificate);
                            break;

                        case "Package":
                            manufacturer.set_package(_package);
                            break;

                        case "Dosage":
                            manufacturer.setDosage(dosage);
                            break;

                        case "Manufacturer":
                            currentVersion.setManufacturer(manufacturer);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drugs;
    }
}
