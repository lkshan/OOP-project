package logistika.vehicles;

import logistika.shop.ShopGood;

/**
 * Created by lukashanincik on 25/04/2017.
 */
public class Vehicle {
    private String name;
    private String specifying;
    private double speed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecifying() {
        return specifying;
    }

    public void setSpecifying(String specifying) {
        this.specifying = specifying;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Vehicle(String name, String specifying, double speed) {
        this.name = name;
        this.specifying = specifying;
        this.speed = speed;
    }
}
