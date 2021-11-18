package ru.otus.crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.HashSet;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
        private Long id;
        private String name;
        private Address address;
        private String phones;

        public Client getClient(){
            Set<Phone> phonesSet = new HashSet<>();
            String[] ph = phones.split(",");
            for (int i = 0; i < ph.length; i++) {
                phonesSet.add(new Phone(ph[i], this.id));
            }
            Client client = new Client(id, name, address, phonesSet);
            return client;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }
}
