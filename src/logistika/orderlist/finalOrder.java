package logistika.orderlist;

/**
 * Created by lukashanincik on 07/05/2017.
 */
public class finalOrder extends DBOrder {
    private Double costs;
    private Double profit;
    private int id_expedition;

    public Double getCosts() {
        return costs;
    }

    public void setCosts(Double costs) {
        this.costs = costs;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public int getId_expedition() {
        return id_expedition;
    }

    public void setId_expedition(int id_expedition) {
        this.id_expedition = id_expedition;
    }
}
