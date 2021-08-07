package ru.otus.homework.tests;

import ru.otus.homework.anotation.After;
import ru.otus.homework.anotation.Before;
import ru.otus.homework.anotation.Test;

public class TestClass {

    @Before
    public void testBefore(){
        System.out.println("Method before");
        System.out.println(this);
    }


    @Test
    public void sizeArray(){
        System.out.println("Test1");
        System.out.println(this);
    }

    @Test
    public void getFirstElement(){
        System.out.println("Test2");
        System.out.println(this);
    }

    @Test
    public void getLastElement(){
        System.out.println("Test3");
        System.out.println(this);
    }


    @After
    public void testAfter(){
        System.out.println("Method after");
    }
}
