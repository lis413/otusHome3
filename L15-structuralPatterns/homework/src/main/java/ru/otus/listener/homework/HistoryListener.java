package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    private  final List<Message> history;


    public HistoryListener(){
        history = new ArrayList<>();
    }


    @Override
    public void onUpdated(Message msg) {
        int i = 0;
        for (Message m: history) {
            System.out.println(m.toString());
            if (m.getId() == msg.getId()){
                history.remove(m);
                history.add(Message.Builder.copy(msg));
                System.out.println("find");
                i++;
            }
        }
        if (i == 0){
            history.add(Message.Builder.copy(msg));
        }

        //throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        for (Message m: history) {
            if (m.getId() == id) return Optional.of(m);
        }
        return Optional.of(null);
    }
}
