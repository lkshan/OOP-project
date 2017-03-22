package logistika.map;

/**
 * Created by lukashanincik on 21/03/2017.
 */
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class WorldMap {
    private char[][] map = new char[52][25];
    private ArrayList<Cities> cities = new ArrayList<Cities>();

    public void setMap(int x, int y, char first){
        this.map[x][y] = first;
    }

    public void readMap() throws IOException{
        File f = new File("/Users/lukashanincik/Documents/OOP-project/src/logistika/map/map.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        //String line;
        int i, m=0,n=0;
        while((i = br.read()) != -1){
            char ch = (char) i;
            //System.out.print(ch);
            this.map[m][n] = ch;
            m++;
            if (m == 52){
                n++;
                m=0;
            }
        }
        br.close();
        fr.close();
        m=0;
        n=0;
    }

    public void showMap() throws IOException{
        addCities();
        int i, j, n=0;

        try{
            readMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addCities();
        for (i = 0; i < 25; i++){
            for (j = 0; j < 52; j++){
                System.out.print(this.map[j][i]);
            }
            //System.out.print("\n");
        }
    }

    public void loadCities() throws IOException {
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
            if (i == 2)fillCities(name, x, y);
            i++;
            if (i == 3)i=0;
        }
        br.close();
        fr.close();
        m=0;
        n=0;
    }

    public ArrayList<Cities> getCities() throws IOException {
        loadCities();
        return cities;
    }

    public void fillCities(String name, String x, String y){
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
        Cities mesto = new Cities();
        mesto.setName(name);
        mesto.setX(X);
        mesto.setY(Y);
        cities.add(mesto);
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

    public void showCities() throws IOException{
        loadCities();
        for (Cities mesto : cities){
            System.out.println(mesto.getName());
            System.out.println(mesto.getX());
            System.out.println(mesto.getY());
        }
    }

    public void addCities() throws IOException{
        loadCities();
        String s = new String();
        char c;
        for (Cities mesto : cities){
            s = mesto.getName();
            c = s.charAt(0);
            setMap(mesto.getX()-1, mesto.getY()-1, c);
        }
    }


/*              ---------------------------------------------------
                -----------------xxx---------------x---------------
                -----------xxx-xxxxxx---------------xx-------------
                -----------x--xxxxxx-------------xxxxxxx-----------
                -------xxx--xx--xxx-------xx----xxxxxxxxxxxxx------
                --xxxxxxxxxx-xx--xx------xxxx--xxxxxxxxxxxxxxxx----
                --xxxxxxxxxx-----x-----x-xx-xxxxxxxxxxxxxxxxxxxx---
                -x---xxxxxx--xx------x-x---xxxxxxxxxxxxxxxxx-x-----
                -----xxxxxxx-xxx--------xxxxxxxxxxxxxxxxxxx--x-----
                ------xxxxxxxxx-------xxxxxxxxxxxxxxxxxxxx--x------
                ------xxxxxxxx--------xx-x-x--xxxxxxxxxxx----------
                -------xxxxxx-------------x-xxxxxxxxxxxx--x--------
                --------xx--x---------xxx---xx-xxxxxxxxx-x---------
                ---------xx----------xxxxxxx-xx--xxxxxx------------
                ----------xx---------xxxxxxx-----xx--xx-x----------
                ------------xxx------xxxxxxxxx----x--x-------------
                -----------xxxxx------xxxxxxx---------xx-----------
                -----------xxxxxxx------xxxxx----------------------
                ------------xxxxx-------xxxx------------x-x--------
                -------------xxxx-------xxx------------xxxx--------
                -------------xxx--------xxx-----------xxxxxx-------
                -------------xx----------x------------xxxxxx-------
                ------------xx---------------------------xx--------
                ------------x--------------------------------------
                ---------------------------------------------------
*/


}

