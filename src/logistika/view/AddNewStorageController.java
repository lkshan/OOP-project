package logistika.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import logistika.DBConnection;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Observable;

/**
 * Created by lukashanincik on 17/03/2017.
 */
public class AddNewStorageController {

    @FXML
    private Button closeButton;
    @FXML
    private Button addData;
    ObservableList<String> maritalStatusList = FXCollections.observableArrayList("Single", "Merried", "Divorced");
    ObservableList<String> mainDepartmentList = FXCollections.observableArrayList("Electrical", "Mechanical");
    ObservableList<String> electricalList = FXCollections.observableArrayList("Design", "R&D");
    ObservableList<String> mechanicalList = FXCollections.observableArrayList("Sales", "Management");

    //Contact Informations

    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;

    //Personal Informations

    @FXML
    private DatePicker birth;
    @FXML
    private TextField ageField;
    @FXML
    private ChoiceBox maritalStatusBox;
    @FXML
    private RadioButton maleBtn;
    @FXML
    private RadioButton femaleBtn;

    // Employee Information OVERALL

    @FXML
    private TextField idField;
    @FXML
    private ComboBox mainDepartmentBox;
    @FXML
    private ComboBox departmentBox;
    @FXML
    private CheckBox yesBox;
    @FXML
    private CheckBox noBox;

    public AddNewStorageController() throws SQLException {
    }

    @FXML
    private void showAge(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int birthYear = (birth.getValue().getYear());
        int age = year - birthYear;
        ageField.setText(Integer.toString(age) + "Years");
    }

    @FXML
    private void initialize(){
        //maritalStatusBox.setValue("Single");
        maritalStatusBox.setItems(maritalStatusList);

        mainDepartmentBox.setValue("Electrical");
        mainDepartmentBox.setItems(mainDepartmentList);

        //departmentBox.setValue("Design");
        //departmentBox.setItems(electricalList);
    }

    @FXML
    private void mainDepartmentChoice(){
        if (mainDepartmentBox.getValue().equals("Electrical")){
            departmentBox.setValue("Design");
            departmentBox.setItems(electricalList);
        }else{
            departmentBox.setValue("Sales");
            departmentBox.setItems(mechanicalList);
        }
    }

    @FXML
    private void handleYesBox(){
        if (yesBox.isSelected()){
            noBox.setSelected(false);
        }
    }

    @FXML
    private void handleNoBox(){
        if (noBox.isSelected()){
            yesBox.setSelected(false);
        }
    }

    @FXML
    private void closeStage(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addDataToDB() throws SQLException {
        DBConnection connection = new DBConnection();
        //connection.insertData(nameField.getText(), addressField.getText(), cityField.getText());
        closeStage();
    }
}
