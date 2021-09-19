package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    private final List<Message> history;


    public HistoryListener(){
        history = new ArrayList<>();
    }


    @Override
    public void onUpdated(Message msg) {
        history.add(msg);
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        for (Message m: history) {
            if (m.getId() == id) return Optional.of(m);
        }
        throw new UnsupportedOperationException();
    }
}
