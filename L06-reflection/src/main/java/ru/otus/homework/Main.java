package ru.otus.homework;

public class Main {
    public static void main(String[] args) {
        TestFramework testFramework = new TestFramework();
        try {
            testFramework.analyz(TestClass.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
