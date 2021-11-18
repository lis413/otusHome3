package ru.otus.crm.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;



//import javax.persistence.*;

@Data
@Table("client")
public class Client implements Cloneable, Persistable<Long> {

    @Id
    private Long id;
    private String name;


    @MappedCollection (idColumn = "client_id")
    private Address address;
    
    @MappedCollection (idColumn = "client_id")
    private Set<Phone> phones = new HashSet<>();


    @Transient
    private boolean isNew = true;
    
    

    public Client(Long id, String name, Address address, Set<Phone> phones, boolean isNew) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.isNew = isNew;
    }

    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this(id, name, address, phones, true);
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(String name, Address address, Set<Phone> phones) {
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public Client(String name, Address address) {
        this.name = name;
        this.address = address;
    }



    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phones);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
               // ", phones=" + phones +
                '}';
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public Client() {
    }

    public boolean isNew(){
        return isNew;
    }

    public String getNumberPhones(){
        StringBuffer str = new StringBuffer();
        for (Phone phone : phones) {
            str.append(phone.getNumber()).append(", ");
        }
        return str.toString();
    }


}
