package ru.otus.homework.tests;

import ru.otus.homework.anotation.After;
import ru.otus.homework.anotation.Before;
import ru.otus.homework.anotation.Test;
import ru.otus.homework.tests.TestClass;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestFramework {
    static List<Method> listBefore = new ArrayList<>();
    static List<Method> listAfter = new ArrayList<>();
    static List<Method> listTest = new ArrayList<>();
    public static void analyze(Class<?> clazz) {
        divideMethode(clazz);
            int stat = 0;
            int error = 0;
            for (Method method : listTest) {
                Object object = null;
                try {
                    object = clazz.newInstance();
                for (Method before : listBefore) {
                    before.invoke(object);
                }
                method.invoke(object);
                stat++;
                } catch (Exception e) {
                    error++;
                    System.out.println("Exception = " + e);
                } finally {
                    for (Method after : listAfter) {
                        try {
                            after.invoke(object);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        System.out.println("Всего выполнено = " + stat);
        System.out.println("Выполнено успешно = " + (stat - error));
        System.out.println("Выполнено с ошибкой = " + error);

    }

    private static void divideMethode(Class<?> clazz){
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                listBefore.add(method);
            } else if (method.isAnnotationPresent(After.class)) {
                listAfter.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                listTest.add(method);
            }
        }
    }

}
