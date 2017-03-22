package logistika.view;

import javafx.fxml.FXML;
import logistika.Main;
import logistika.orderlist.OrderList;

import java.io.IOException;

/**
 * Created by lukashanincik on 17/03/2017.
 */
public class MainItemsController {

    private Main main;

    @FXML
    private void goStorages() throws IOException {
        main.showStoragesScene();
    }

    @FXML
    private void goOrderlist() throws IOException {
        main.showOrderlistScene();
        main.showOrderTableView();
    }

}
