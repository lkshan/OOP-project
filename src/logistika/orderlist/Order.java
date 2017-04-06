package logistika.orderlist;

import logistika.map.Cities;
import logistika.map.Storage;

/**
 * Created by lukashanincik on 21/03/2017.
 */
public class Order {
    private int typ;
    private int vzdialenost;
    private Storage zdroj = new Storage();
    private Cities destinacia = new Cities();

    public Order(int typ, int vzdialenost, Storage zdroj, Cities destinacia) {
        this.typ = typ;
        this.vzdialenost = vzdialenost;
        this.zdroj = zdroj;
        this.destinacia = destinacia;
    }

    public Order(){

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

    public Storage getZdroj() {
        return zdroj;
    }

    public void setZdroj(Storage zdroj) {
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
