package homework;

import java.util.Comparator;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public class Customer implements Cloneable{
    private final long id;
    private String name;
    private long scores;

    //todo: 1. в этом классе надо исправить ошибки

    public Customer(long id, String name, long scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScores() {
        return scores;
    }

    public void setScores(long scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scores=" + scores +
                '}';
    }

   // @Override
    public int compare(Object o1, Object o2) {
        if ((long)o1 == (long)o2) return 0;
        if ((long)o1 > (long)2) return 1;
        else return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

       if (id == customer.id) return true;
       else
           return false;
       // if (id != customer.id) return false;
       // if (scores != customer.scores) return false;
       // return name != null ? name.equals(customer.name) : customer.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
       // result = 31 * result + (name != null ? name.hashCode() : 0);
       // result = 31 * result + (int) (scores ^ (scores >>> 32));
        return result;
    }

//    @Override
//    public int compareTo(Object o) {
//        if (this == null) return 1;
//        if (this.getId() == ((Customer)o).getId()) return 0;
//        if (this.getId() > ((Customer)o).getId()) return 1;
//        else return -1;
//    }

    public static void main(String[] args) {
        TreeMap<Integer, String> treemap = new TreeMap<Integer, String>();
        SortedMap<Integer, String> treemapincl = new TreeMap<Integer, String>();

        // populating tree map
        treemap.put(2, "two");
        treemap.put(1, "one");
        treemap.put(3, "three");
        treemap.put(6, "six");
        treemap.put(5, "five");

        System.out.println("Getting tail map");
        treemapincl = treemap.tailMap(4);
        System.out.println("Tail map values: "+treemapincl);
    }

    public Customer clone(){
        return new Customer(this.getId(), this.getName(), this.getScores());
    }



}
