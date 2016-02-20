package de.smava.bank.client.rest.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for account complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="account">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="businessIdentifierCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iban" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "account", propOrder = {
        "businessIdentifierCode",
        "iban",
        "id"
})
@XmlRootElement
public class Account {

    protected String businessIdentifierCode;
    protected String iban;
    protected Long id;

    /**
     * Gets the value of the businessIdentifierCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBusinessIdentifierCode() {
        return businessIdentifierCode;
    }

    /**
     * Sets the value of the businessIdentifierCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBusinessIdentifierCode(String value) {
        this.businessIdentifierCode = value;
    }

    /**
     * Gets the value of the iban property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIban() {
        return iban;
    }

    /**
     * Sets the value of the iban property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIban(String value) {
        this.iban = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setId(Long value) {
        this.id = value;
    }

}
