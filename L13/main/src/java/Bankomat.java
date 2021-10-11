import lombok.Data;

import java.util.*;
@Data
public class Bankomat {

    Map<Integer, CellMoney> mapCell = new HashMap<Integer, CellMoney>();

    public void addMoney(Integer count, Integer nominal) {
        CellMoney cell = null;
        if (mapCell.containsKey(nominal)) {
            cell = mapCell.get(count);
            cell.setCount(cell.getCount() + count);
        } else {
            cell = new CellMoney(nominal, count);
        }
        mapCell.put(nominal, cell);
    }

    public Map<Integer, CellMoney> getMoney(Integer money) {
        Map<Integer, CellMoney> map = new HashMap<Integer, CellMoney>();
        Integer moneyOst = money;
        Set<Integer> setStart =  mapCell.keySet();
        TreeSet<Integer> set2 = new TreeSet<Integer>(setStart);
        TreeSet<Integer> set = (TreeSet<Integer>) set2.descendingSet();
        if (noMoney(money)) {
            for (Integer key : set) {
                int del = moneyOst / key;
                if (del > 0){
                    if (del <= mapCell.get(key).getCount()){
                        map.put(key, new CellMoney(key, del));
                        moneyOst = moneyOst - key * del;
                    } else
                    {
                        map.put(key, new CellMoney(key, mapCell.get(key).getCount()));
                        moneyOst = moneyOst  - key * mapCell.get(key).getCount();
                    }
                    if (moneyOst == 0){
                        deleteMoney(map);
                        return map;
                    }
                }
            }
        } else {
            return null;
        }
        return null;

    }

    public void deleteMoney(Map<Integer, CellMoney> mapMoney) {
        for (Integer key : mapMoney.keySet()) {
            mapCell.put(key, mapCell.get(key).diferentCell(mapMoney.get(key)));
        }
    }

    public boolean noMoney(Integer money) {
        Integer allMoney = 0;
        for (Integer key : mapCell.keySet()) {
            allMoney = allMoney + key * mapCell.get(key).getCount();
        }
        if (allMoney < money) {
            return false;
        } else {
            return true;
        }
    }

}
