package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorChangeField implements Processor {
    @Override
    public Message process(Message message) {
        String field = message.getField12();
        return message.toBuilder().field12(message.getField11()).field11(field).build();
    }
}
