

import java.util.Map;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Bankomat bankomat = new Bankomat();
        bankomat.addMoney(10, 500);
        bankomat.addMoney(5, 100);
        bankomat.addMoney(5, 50);
        bankomat.addMoney(8, 10);


        TreeSet<Integer> setStart = new TreeSet(bankomat.getMapCell().keySet());
        for (Integer t : setStart) {
            System.out.println(t + "    " + bankomat.getMapCell().get(t));
        }
        System.out.println("  ");
        System.out.println("выдать");
        Map<Integer, CellMoney> test =  bankomat.getMoney(1600);
        if (test != null) {
            TreeSet<Integer> setMed = new TreeSet(test.keySet());
            for (Integer t : setMed) {
                System.out.println(t + "    " + test.get(t));
            }
            System.out.println("  ");
            System.out.println("статок");
            TreeSet<Integer> setEnd = new TreeSet(bankomat.getMapCell().keySet());
            for (Integer t : setStart) {
                System.out.println(t + "    " + bankomat.getMapCell().get(t));
            }
        } else {
            System.out.println("Нет такой суммы");
        }


    }
}
