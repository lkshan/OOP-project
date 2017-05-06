package logistika.runningExpo;
import java.sql.Timestamp;
/**
 * Created by lukashanincik on 06/05/2017.
 */
public class Expedition {
    private int id_expedition;
    private int distance;
    private Timestamp date;
    private double time;
    private double costs;
    private double profit;

    public int getId_expedition() {
        return id_expedition;
    }

    public void setId_expedition(int id_expedition) {
        this.id_expedition = id_expedition;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getCosts() {
        return costs;
    }

    public void setCosts(double costs) {
        this.costs = costs;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public Expedition(int id_expedition, int distance, Timestamp date, double time, double costs, double profit) {
        this.id_expedition = id_expedition;
        this.distance = distance;
        this.date = date;
        this.time = time;
        this.costs = costs;
        this.profit = profit;
    }
}
