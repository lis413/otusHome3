import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
public class CellMoney implements Cloneable {
    Integer nominal;
    Integer count;

    public CellMoney(Integer nominal) {
        this.nominal = nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public CellMoney diferentCell(CellMoney cellMoney){
       return  new CellMoney(nominal, count - cellMoney.getCount());
    }


}
