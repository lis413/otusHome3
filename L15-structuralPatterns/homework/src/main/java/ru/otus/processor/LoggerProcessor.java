package ru.otus.processor;

import ru.otus.model.Message;
import ru.otus.processor.homework.TimeProcessorException;

public class LoggerProcessor implements Processor {


    private final Processor processor;

    public LoggerProcessor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public Message process(Message message) throws TimeProcessorException {
        System.out.println("log processing message:" + message);
        return processor.process(message);
    }
}
