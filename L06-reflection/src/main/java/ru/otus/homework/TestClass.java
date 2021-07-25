package ru.otus.homework;

public class TestClass {

    @Before
    public void testBefore(){
        System.out.println("Method before");
    }


    @Test
    public void sizeArray(){
        System.out.println("Test1");
    }

    @Test
    public void getFirstElement(){
        System.out.println("Test2");
    }

    @Test
    public void getLastElement(){
        System.out.println("Test3");
    }


    @After
    public void testAfter(){
        System.out.println("Method after");
    }
}
