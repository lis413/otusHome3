package homework;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    Stack<Customer> list = new Stack<>();
    public void add(Customer customer) {
        list.add(customer);
    }

    public Customer take() {
        return list.pop();
    }
}
