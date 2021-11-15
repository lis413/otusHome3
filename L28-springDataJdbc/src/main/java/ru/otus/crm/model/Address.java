package ru.otus.crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("address")
@Data

public class Address {
    @Id
    private Long id;

    private String street;

    private Long clientId;

   //private boolean isNew;


    public Address(String street) {
        this.id = null;
        this.street = street;
    }

    public Address(Long id, String street, Long clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }

    public Address(String street, Long clientId) {
        this.street = street;
        this.clientId = clientId;
    }

   // public boolean isNew(){
   //     return isNew;
   // }



    public void test(){

    }

    public Address() {
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", clientId=" + clientId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
