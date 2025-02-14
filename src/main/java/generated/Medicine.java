//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.12.14 at 02:13:31 PM MSK 
//


package generated;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "drug"
})
@XmlRootElement(name = "Medicine")
public class Medicine {

    @XmlElement(name = "Drug", required = true)
    protected List<Medicine.Drug> drug;

    public List<Medicine.Drug> getDrug() {
        if (drug == null) {
            drug = new ArrayList<Medicine.Drug>();
        }
        return this.drug;
    }


    @Setter
    @Getter
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "name",
            "pharm",
            "group",
            "analogs",
            "versions"
    })
    public static class Drug {

        @XmlElement(name = "Name", required = true)
        protected String name;
        @XmlElement(name = "Pharm", required = true)
        protected String pharm;
        @XmlElement(name = "Group", required = true)
        protected String group;
        @XmlElement(name = "Analogs", required = true)
        protected Medicine.Drug.Analogs analogs;
        @XmlElement(name = "Versions", required = true)
        protected Medicine.Drug.Versions versions;

        @Override
        public String toString() {

            return "Drug{" +
                    "\nname = " + name +
                    "\npharm = " + pharm +
                    "\ngroup = " + group +
                    "\nanalogs = " + analogs +
                    "\nversions = " + versions +
                    "\n}";
        }


        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "analog"
        })
        @ToString
        public static class Analogs {

            @XmlElement(name = "Analog", required = true)
            protected List<String> analog;

            public List<String> getAnalog() {
                if (analog == null) {
                    analog = new ArrayList<String>();
                }
                return this.analog;
            }

            @Override
            public String toString() {
                return analog.toString();
            }
        }


        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "version"
        })
        public static class Versions {

            @XmlElement(name = "Version", required = true)
            protected List<Medicine.Drug.Versions.Version> version;

            public List<Medicine.Drug.Versions.Version> getVersion() {
                if (version == null) {
                    version = new ArrayList<Medicine.Drug.Versions.Version>();
                }
                return this.version;
            }

            @Override
            public String toString() {
                return "Versions{" + version + '}';
            }

            @Setter
            @Getter
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "type",
                    "manufacturer"
            })
            public static class Version {

                @XmlElement(name = "Type", required = true)
                protected String type;
                @XmlElement(name = "Manufacturer", required = true)
                protected Medicine.Drug.Versions.Version.Manufacturer manufacturer;

                @Override
                public String toString() {
                    return "\nversion{ " +
                            "type = " + type +
                            manufacturer +
                            '}';
                }


                @Getter
                @Setter
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "certificate",
                        "_package",
                        "dosage"
                })
                public static class Manufacturer {

                    @XmlElement(name = "Certificate", required = true)
                    protected Medicine.Drug.Versions.Version.Manufacturer.Certificate certificate;
                    @XmlElement(name = "Package", required = true)
                    protected Medicine.Drug.Versions.Version.Manufacturer.Package _package;
                    @XmlElement(name = "Dosage", required = true)
                    protected Medicine.Drug.Versions.Version.Manufacturer.Dosage dosage;


                    @Override
                    public String toString() {
                        return "\nManufacturer{" +
                                certificate +
                                _package + dosage +
                                '}';
                    }

                    @Setter
                    @Getter
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "number",
                            "issueDate",
                            "expiryDate",
                            "organization"
                    })
                    public static class Certificate {

                        @XmlElement(name = "Number", required = true)
                        protected String number;
                        @XmlElement(name = "IssueDate", required = true)
                        @XmlSchemaType(name = "date")
                        protected XMLGregorianCalendar issueDate;
                        @XmlElement(name = "ExpiryDate", required = true)
                        @XmlSchemaType(name = "date")
                        protected XMLGregorianCalendar expiryDate;
                        @XmlElement(name = "Organization", required = true)
                        protected String organization;

                        @Override
                        public String toString() {
                            return "\nCertificate{ " +
                                    "number = " + number +
                                    ", issueDate = " + issueDate +
                                    ", expiryDate = " + expiryDate +
                                    ", organization = " + organization + '}';
                        }
                    }


                    @Setter
                    @Getter
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "amount",
                            "frequency"
                    })
                    public static class Dosage {

                        @XmlElement(name = "Amount", required = true)
                        protected String amount;
                        @XmlElement(name = "Frequency", required = true)
                        protected String frequency;

                        @Override
                        public String toString() {
                            return "\nDosage{ " +
                                    "amount = " + amount +
                                    ", frequency = " + frequency +
                                    '}';
                        }
                    }


                    @Setter
                    @Getter
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "type",
                            "quantity",
                            "price"
                    })
                    public static class Package {

                        @XmlElement(name = "Type", required = true)
                        protected String type;
                        @XmlElement(name = "Quantity")
                        protected int quantity;
                        @XmlElement(name = "Price", required = true)
                        protected BigDecimal price;

                        @Override
                        public String toString() {
                            return "\nPackage{ " +
                                    "type = " + type +
                                    ", quantity = " + quantity +
                                    ", price = " + price +
                                    '}';
                        }
                    }
                }
            }
        }
    }
}
