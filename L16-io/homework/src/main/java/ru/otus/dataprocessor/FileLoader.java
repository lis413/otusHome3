package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.model.Measurement;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileLoader implements Loader {
    String fileName;
    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        JSONParser jsonParser = new JSONParser();
        List<Measurement> list = new ArrayList<>();
        try {
            JSONArray measurements = (JSONArray) jsonParser.parse(new FileReader(fileName));
            ArrayList<Measurement> yourArray = new Gson().fromJson(String.valueOf(measurements), new TypeToken<List<Measurement>>(){}.getType());
            return yourArray;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //читает файл, парсит и возвращает результат
        return null;
    }
}
