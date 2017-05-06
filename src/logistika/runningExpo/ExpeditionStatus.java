package logistika.runningExpo;

import java.sql.Timestamp;

/**
 * Created by lukashanincik on 06/05/2017.
 */
public class ExpeditionStatus extends Expedition {
    private double status;
    public ExpeditionStatus(int id_expedition, int distance, Timestamp date, double time, double costs, double profit) {
        super(id_expedition, distance, date, time, costs, profit);
    }

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }
}
