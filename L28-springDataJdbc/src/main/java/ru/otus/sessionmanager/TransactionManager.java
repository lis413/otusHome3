package ru.otus.sessionmanager;

import org.springframework.stereotype.Component;


public interface TransactionManager {

    <T> T doInTransaction(TransactionAction<T> action);
}
