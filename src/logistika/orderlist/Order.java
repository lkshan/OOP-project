package logistika.orderlist;

import logistika.map.Cities;

/**
 * Created by lukashanincik on 21/03/2017.
 */
public class Order {
    private int typ;
    private int vzdialenost;
    private Cities zdroj = new Cities();
    private Cities destinacia = new Cities();

    public Order(int typ, int vzdialenost, Cities zdroj, Cities destinacia) {
        this.typ = typ;
        this.vzdialenost = vzdialenost;
        this.zdroj = zdroj;
        this.destinacia = destinacia;
    }

    public int getTyp() {
        return typ;
    }

    public void setTyp(int hmotnost) {
        this.typ = hmotnost;
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
        System.out.println("Typ : " + this.getTyp());
        System.out.println("Vzdialenost : " + this.getVzdialenost());
    }
}
