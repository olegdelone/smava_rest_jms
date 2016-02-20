package de.smava.bank.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by olegdelone on 23.01.2016.
 */
@Entity
@Table( name = "ACCOUNT" )
@XmlRootElement
public class Account {

    public Account() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(nullable = false, name = "IBAN")
    private String iban;

    @Column(nullable = false, name = "BUSINESS_IDENTIFIER_CODE")
    private String businessIdentifierCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBusinessIdentifierCode() {
        return businessIdentifierCode;
    }

    public void setBusinessIdentifierCode(String businessIdentifierCode) {
        this.businessIdentifierCode = businessIdentifierCode;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", iban='" + iban + '\'' +
                ", businessIdentifierCode='" + businessIdentifierCode + '\'' +
                '}';
    }
}
