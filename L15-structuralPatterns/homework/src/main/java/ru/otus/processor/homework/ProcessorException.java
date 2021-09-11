package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalTime;

public class ProcessorException implements Processor {
    @Override
    public Message process(Message message) throws TimeProcessorException {
        long secondsOfDay = LocalTime.now().toSecondOfDay();
        if (secondsOfDay % 2 == 0) throw new TimeProcessorException();
        return message;
    }
}
