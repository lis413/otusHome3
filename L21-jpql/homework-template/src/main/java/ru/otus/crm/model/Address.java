package ru.otus.crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String street;

    public Address(String street) {
        this.street = street;
    }
}
