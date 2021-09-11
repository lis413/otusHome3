package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        Map<String, Double> map = new TreeMap<>();//группирует выходящий список по name, при этом суммирует поля value
        data.stream().forEach(x -> {
            String name = x.getName();
            if (map.containsKey(name)){
                double value = map.get(name) + x.getValue();
                map.put(name, value);
            } else {
                map.put(name, x.getValue());
            }

        });

        return map;
    }
}
