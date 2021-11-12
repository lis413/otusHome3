package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.repository.ClientRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.*;

@Service
public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;

    @Autowired
    public DbServiceClientImpl(TransactionManager transactionManager, ClientRepository clientRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> {
            Long id = clientRepository.findMaxId();
            client.setId(id+1);
            client.getAddress().setClientId(id+1);
            Set<Phone> phone = new HashSet<Phone>();
            Phone phone1 = new Phone("123", 4L);
            for (Phone ph: client.getPhones()) {
                if (ph != null)
                ph.setClientId(id+1);
            }
            phone.add(phone1);
          //  client.setPhones(phone);
            var savedClient = clientRepository.save(client);
            log.info("saved client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        var clientOptional = clientRepository.findById(id);
        log.info("client: {}", clientOptional);
        return clientOptional;
    }

    @Override
    public List<Client> findAll() {
        //var clientList = new ArrayList<Client>();
       // clientRepository.findAll().forEach(clientList::add);

        var clientList = clientRepository.findAll();
        log.info("clientList:{}", clientList);
        return clientList;
    }


}
