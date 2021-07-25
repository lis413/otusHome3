package ru.otus.homework;

import javax.lang.model.util.ElementScanner6;
import javax.xml.catalog.Catalog;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestFramework {
    public void analyz(Class<?> clazz) throws Exception {
        Method[] methods = clazz.getMethods();
        List<Method> listBefore = new ArrayList<>();
        List<Method> listAfter = new ArrayList<>();
        try {
            for (Method method : methods) {
                if (method.isAnnotationPresent(ru.otus.homework.Before.class)) {
                    listBefore.add(method);
                } else if (method.isAnnotationPresent(ru.otus.homework.After.class)) {
                    listAfter.add(method);
                }
            }
            for (Method method : methods) {
                if (method.isAnnotationPresent(ru.otus.homework.Test.class)) {
                    for (Method before : listBefore) {
                        before.invoke(clazz.newInstance());
                    }
                    method.invoke(new TestClass());
                    for (Method after : listAfter) {
                        after.invoke(new TestClass());
                    }
                }
            }
        } catch (Exception e){
            System.out.println("Exception = " + e );
        }

    }
}
