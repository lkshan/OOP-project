package logistika.orderlist;

/**
 * Created by lukashanincik on 21/03/2017.
 */
import logistika.map.Cities;
import logistika.map.WorldMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

/**
 * Created by lukashanincik on 09/03/2017.
 */
public class OrderList {
    private ArrayList<Order> Objednavky = new ArrayList<Order>();

    public ArrayList<Order> getObjednavky() {
        return Objednavky;
    }

    public void createOrderList(Cities city) throws IOException{
        WorldMap map = new WorldMap();
        int i, rand;
        Cities zdroj = new Cities();
        Cities destinacia = new Cities();
        int hmotnost, vzdialenost;
        for (i = 0; i < 10; i++) {
            rand = randCity();
            Cities mesto = new Cities();
            mesto = map.getCities().get(rand-1);
            destinacia = mesto;
            zdroj = city;
            hmotnost = randInt(2000, 200);
            //
            int a, b;
            double ab;
            a = Math.abs( mesto.getX() - city.getX() +1 );
            b = Math.abs( mesto.getY() - city.getY() +1 );
            ab = Math.pow(a, 2) + Math.pow(b, 2);
            vzdialenost = (int) Math.round( Math.sqrt(ab) );
            //
            Order order = new Order(hmotnost, vzdialenost, zdroj, destinacia);
            Objednavky.add(order);
        }
    }

    public static int randCity() throws IOException{
        Random rand  = new Random();
        int max=0, min = 1;
        File f = new File("/Users/lukashanincik/Documents/OOP-project/src/logistika/map/mesta.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line = new String();
        while ((line = br.readLine()) != null){
            max++;
        }
        max /= 3;
        int randomNum = rand.nextInt((max - min) + 1) + min;
        br.close();
        fr.close();
        return randomNum;
    }

    public static int randInt(int max, int min){
        int weight;
        Random rand = new Random();
        weight = rand.nextInt((max-min) + 1 ) + min;
        return weight;
    }

    public void printOrders(){
        int i = 0;
        for (Order objednavka : Objednavky){
            i++;
            System.out.println(i + ".");
            System.out.println("Z  : " + objednavka.getZdroj().getName());
            System.out.println("Do : " + objednavka.getDestinacia().getName());
            System.out.println("Hmotnost : " + objednavka.getHmotnost());
            System.out.println("Vzdialenost : " + objednavka.getVzdialenost());
            System.out.println();
        }
    }

    public Order getObjednavka(int i){
        return Objednavky.get(i-1);
    }

    public void removeOrder(Order objednavka){
        this.Objednavky.remove(objednavka);
    }

}

