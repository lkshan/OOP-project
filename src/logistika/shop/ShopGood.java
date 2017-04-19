package logistika.shop;

/**
 * Created by lukashanincik on 19/04/2017.
 */
public class ShopGood {
    private int id;
    private String name;
    private String type;
    private int cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public ShopGood(int id, String name, String type, int cost) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.cost = cost;
    }
}
