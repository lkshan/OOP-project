package logistika.runningExpo;

import java.sql.Timestamp;

/**
 * Created by lukashanincik on 07/05/2017.
 */
public class ExpeditionStatusEndTime extends ExpeditionStatus {
    private Timestamp endTime;
    public ExpeditionStatusEndTime(int id_expedition, int distance, Timestamp date, double time, double costs, double profit) {
        super(id_expedition, distance, date, time, costs, profit);
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
