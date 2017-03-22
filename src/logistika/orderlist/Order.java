package logistika.orderlist;

import logistika.map.Cities;

/**
 * Created by lukashanincik on 21/03/2017.
 */
public class Order {
    private int hmotnost;
    private int vzdialenost;
    private Cities zdroj = new Cities();
    private Cities destinacia = new Cities();

    public Order(int hmotnost, int vzdialenost, Cities zdroj, Cities destinacia) {
        this.hmotnost = hmotnost;
        this.vzdialenost = vzdialenost;
        this.zdroj = zdroj;
        this.destinacia = destinacia;
    }

    public int getHmotnost() {
        return hmotnost;
    }

    public void setHmotnost(int hmotnost) {
        this.hmotnost = hmotnost;
    }

    public int getVzdialenost() {
        return vzdialenost;
    }

    public void setVzdialenost(int vzdialenost) {
        this.vzdialenost = vzdialenost;
    }

    public Cities getZdroj() {
        return zdroj;
    }

    public void setZdroj(Cities zdroj) {
        this.zdroj = zdroj;
    }

    public Cities getDestinacia() {
        return destinacia;
    }

    public void setDestinacia(Cities destinacia) {
        this.destinacia = destinacia;
    }

    public void printOrder(){
        System.out.println("Z  : " + this.getZdroj().getName());
        System.out.println("Do : " + this.getDestinacia().getName());
        System.out.println("Hmotnost : " + this.getHmotnost());
        System.out.println("Vzdialenost : " + this.getVzdialenost());
    }
}
