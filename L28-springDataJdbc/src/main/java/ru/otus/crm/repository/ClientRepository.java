package ru.otus.crm.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Set;

public interface ClientRepository extends CrudRepository<Client, Long> {

    List<Client> findAll();

    @Query("select max(id) from client")
    Long findMaxId();

}
