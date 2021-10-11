package ru.otus.services;

import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceClientImpl;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.WebServerWithFilterBasedSecurityDemo.HIBERNATE_CFG_FILE;

public class UtilServiceLClient {

    public DBServiceClient getDBServiceClient(){
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
///

        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        return dbServiceClient;
    }

    public void saveDataDB(){
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        System.out.println("_____________________________________________");
        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
///

        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        Address address1 = new Address("Leninina");
        Address address2 = new Address("Matrosova");
        Phone phone1 = new Phone("111");
        Phone phone2 = new Phone("222");
        Phone phone3 = new Phone("333");
        Phone phone4 = new Phone("444");
        List<Phone> list1 = new ArrayList<>();
        List<Phone> list2 = new ArrayList<>();
        list1.add(phone1); list1.add(phone2);
        list2.add(phone3); list2.add(phone4);

        dbServiceClient.saveClient(new Client("dbServiceFirst", address1, list1));

        System.out.println("SAVE CLIENT 2 +++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond", address2, list2));

        System.out.println("SAVE CLIENT 2 +++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

}
