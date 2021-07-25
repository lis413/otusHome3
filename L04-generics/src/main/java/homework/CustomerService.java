package homework;


import com.sun.net.httpserver.Authenticator;
import com.sun.source.tree.Tree;

import java.util.*;
import java.util.Map.*;
import java.util.concurrent.ThreadPoolExecutor;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    TreeMap<Customer, String> map = new TreeMap(newComparator());
//важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Entry<Customer, String> getSmallest() {
        TreeMap<Customer, String> mapCop = new TreeMap<>(newComparator());
        Customer customer = map.firstEntry().getKey().clone();
        String str = map.firstEntry().getValue();
        mapCop.put(customer, str);
        return mapCop.firstEntry();
    }

    public Entry<Customer, String> getNext(Customer customer) {
        SortedMap<Customer, String> subMap = map.tailMap(customer);
        if (subMap.size() == 0) return null;
        int i = 0;
        if (subMap.firstKey().equals(customer)) {
            if (subMap.size() > 1) {
                for (Map.Entry<Customer, String> entry : subMap.entrySet()) {
                    if (i == 1) {
                        return entry;
                    }
                    i++;
                }
            }
            return null;
        }
        if (subMap.size() > 0) {
            for (Map.Entry<Customer, String> entry : subMap.entrySet()) {
                return entry;
            }
        }
        return null;
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Comparator<Customer> newComparator(){
        return new Comparator<Customer>() {
            @Override
            public int compare(Customer o1, Customer o2) {
                if (o1.getScores() == o2.getScores()) return 0;
                if (o1.getScores() > o2.getScores()) return 1;
                else return -1;
            }
        };
    }
}
