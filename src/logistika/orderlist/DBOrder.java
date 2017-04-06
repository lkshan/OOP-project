package logistika.orderlist;

/**
 * Created by lukashanincik on 05/04/2017.
 */
public class DBOrder extends Order {
    private int id_order;
    private String storageName;
    private String cityName;
    private String typ_str;

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storgeName) {
        this.storageName = storgeName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTyp_str() {
        return typ_str;
    }

    public void setTyp_str(String typ_str) {
        this.typ_str = typ_str;
    }
}
