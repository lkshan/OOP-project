package logistika.map;

/**
 * Created by lukashanincik on 21/03/2017.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by lukashanincik on 07/03/2017.
 */
public class Cities {
    private String name;
    private int x;
    private int y;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    //private ArrayList<Mesta> Cities = new ArrayList<Mesta>();

    /*public Mesta(String name, String x, String y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void loadCities(ArrayList<Mesta> Cities) throws IOException {
        File f = new File("/Users/lukashanincik/Documents/Projekt_Logistika/src/logistikaCore/fiit/stuba/sk/Lukas/Hanincik/mesta.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int i=0, m=0,n=0;
        String name = new String();
        String x = new String();
        String y = new String();
        while((line = br.readLine()) != null){
            switch (i){
                case 0: name = line;
                        //System.out.println(name);
                        break;
                case 1: x = line;
                        //System.out.println(x);
                        break;
                case 2: y = line;
                        //System.out.println(y);
                        break;
            }
            if (i == 2)fillCities(name, x, y, Cities);
            i++;
            if (i == 3)i=0;
        }
        br.close();
        fr.close();
        m=0;
        n=0;
    }

    public void fillCities(String name, String x, String y, ArrayList<Mesta> Cities){
        int X=0, i, Y=0;
        char c;
        for (i=0; i<x.length(); i++){
            c = x.charAt(i);
            String hex = String.format("%04x", (int) c);
            X += (h2d(hex)-64);
            //System.out.println(hex);
        }
        X += (x.length()-1)*25;
        Y = Integer.parseInt(y);
        Mesta mesto = new Mesta();
        mesto.setName(name);
        mesto.setX(X);
        mesto.setY(Y);
        Cities.add(mesto);
    }

    public static int h2d(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }

    public void printCities(ArrayList<Mesta>Cities) throws IOException{
        loadCities(Cities);
        //setCities();
        for (Mesta mesto : Cities){
            System.out.println(mesto.getName());
            System.out.println(mesto.getX());
            System.out.println(mesto.getY());
        }
    }*/

}

