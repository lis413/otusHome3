package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        Gson gson = new Gson();
        String gsonString = gson.toJson(data);
        try {
            FileWriter fileWriter = new FileWriter(new File(fileName));
            fileWriter.write(gsonString);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //формирует результирующий json и сохраняет его в файл
    }
}
