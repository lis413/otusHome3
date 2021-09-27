package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalTime;

public class ProcessorException implements Processor {
    private final TimeProvider timeProvider;

    public ProcessorException(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    @Override
    public Message process(Message message) throws TimeProcessorException {
        var second = timeProvider.getTime().getSecond();
        if (second % 2 == 0) throw new TimeProcessorException();
        return message;
    }
}
