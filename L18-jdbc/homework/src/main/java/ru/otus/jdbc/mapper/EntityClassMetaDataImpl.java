package ru.otus.jdbc.mapper;



import ru.otus.crm.model.IdOtus;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EntityClassMetaDataImpl implements EntityClassMetaData{

    Class className;

    public EntityClassMetaDataImpl(Class className) {
        this.className = className;
    }

    @Override
    public String getName() {
        return className.getSimpleName();
    }

    @Override
    public Constructor getConstructor() {
        try {
            return className.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Field getIdField() {
        Field[] field = className.getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            if (field[i].isAnnotationPresent(IdOtus.class)){
                return field[i];
            }
        }
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        Field[] field = className.getFields();
        return Arrays.asList(field);
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> list = new ArrayList<>();
        Field[] field = className.getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            if (!(field[i].isAnnotationPresent(IdOtus.class))){
                list.add(field[i]);
            }
        }
        return list;
    }
}
