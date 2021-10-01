package ru.otus.model;

import java.util.ArrayList;
import java.util.List;

public class ObjectForMessage {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public static ObjectForMessage copy(ObjectForMessage objectForMessage){
        List<String> list = new ArrayList<>();
        for (String str: objectForMessage.getData()) {
            list.add(str);
        }
        ObjectForMessage object = new ObjectForMessage();
        object.setData(list);
        return object;
    }
}
