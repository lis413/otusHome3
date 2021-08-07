package ru.otus.homework.tests;

import ru.otus.homework.anotation.After;
import ru.otus.homework.anotation.Before;
import ru.otus.homework.anotation.Test;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestFramework {
    public static void analyze(Class<?> clazz) {
        List<Method> listBefore = new ArrayList<>();
        List<Method> listAfter = new ArrayList<>();
        List<Method> listTest = new ArrayList<>();
        divideMethode(clazz, listBefore, listAfter, listTest);
        int stat = 0;
        int error = 0;
        for (Method method : listTest) {
            Object object = null;
            try {
                object = clazz.getDeclaredConstructor().newInstance();
                printMethod(listBefore, object);
                method.invoke(object);
                stat++;
            } catch (Exception e) {
                error++;
                System.out.println("Exception = " + e);
            } finally {
                printMethod(listAfter, object);
            }
        }
        printStat(stat, error);

    }

    private static void printStat(int stat, int error) {
        System.out.println("Всего выполнено = " + stat);
        System.out.println("Выполнено успешно = " + (stat - error));
        System.out.println("Выполнено с ошибкой = " + error);
    }

    private static void printMethod(List<Method> methods, Object object) {
        for (Method before : methods) {
            try {
                before.invoke(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void divideMethode(Class<?> clazz, List<Method> listBefore, List<Method> listAfter, List<Method> listTest) {
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
