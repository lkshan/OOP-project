package logistika.map;

/**
 * Created by lukashanincik on 02/04/2017.
 */
public class FullStorage extends Storage{
    int id_storage;
    int city_id;

    public int getId_storage() {
        return id_storage;
    }

    public void setId_storage(int id_storage) {
        this.id_storage = id_storage;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }
}
