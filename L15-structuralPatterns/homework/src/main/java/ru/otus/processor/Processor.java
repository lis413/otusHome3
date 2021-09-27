package ru.otus.processor;

import ru.otus.model.Message;
import ru.otus.processor.homework.TimeProcessorException;

public interface Processor {

    Message process(Message message) throws TimeProcessorException;

    //todo: 2. Сделать процессор, который поменяет местами значения field11 и field12

    //todo: 3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
    //         Секунда должна определяьться во время выполнения.
    //         Тест - важная часть задания
}
