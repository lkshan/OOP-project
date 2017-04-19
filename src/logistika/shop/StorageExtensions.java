package logistika.shop;

import logistika.map.Storage;

/**
 * Created by lukashanincik on 19/04/2017.
 */
public class StorageExtensions extends ShopGood{
    private String storage;
    private String extension;

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public StorageExtensions(int id, String name, String type, int cost, String myStorage, String myExtension) {
        super(id, name, type, cost);
        this.storage = myStorage;
        this.extension = myExtension;
    }
}
