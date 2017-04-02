package logistika.map;

import logistika.orderlist.Order;

import java.util.ArrayList;

/**
 * Created by lukashanincik on 02/04/2017.
 */
public class Storage {
    private String name = new String();
    private int type;
    private Cities location = new Cities();
    private ArrayList<Order> storageList = new ArrayList<>();

    public Storage(String nazov, int type, Cities poloha){
        this.name = nazov;
        this.type = type;
        this.location = poloha;
    }

    public Storage(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Cities getLocation() {
        return location;
    }

    public void setLocation(Cities location) {
        this.location = location;
    }

    public boolean addOrder(Order order){
        if (order.getTyp() == this.type){
            if (storageList.add(order))return true;
            else return false;
        }
        else return false;
    }
}
