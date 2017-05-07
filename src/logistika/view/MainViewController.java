package logistika.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import logistika.Main;

import java.io.IOException;

/**
 * Created by lukashanincik on 17/03/2017.
 */
public class MainViewController {

    private Main main;
    @FXML
    private Label cashLabel;

    public void setCashLabelText() {
        initialize();
    }

    @FXML
    public void initialize(){
        String str = ""+Main.getCash()+"$";
        cashLabel.setText(str);
    }

    @FXML
    private void goHome() throws IOException {
        main.showMainItems();
    }

    @FXML
    private void addStorage() throws IOException {
        main.showFirstStorage();
    }
}
