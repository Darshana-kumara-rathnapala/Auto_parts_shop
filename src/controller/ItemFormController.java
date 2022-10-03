package controller;

import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modle.BoughtDetail;
import modle.Buy;
import modle.Item;
import modle.Suplier;
import tm.ItemTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemFormController {
    public TextField txtBarcode;
    public TextField txtDescription;
    public TextField txtQuantity;
    public TextField txtCost;
    public JFXTextField txtSuplierNic;
    public JFXTextField txtSuplierName;
    public JFXTextField txtSuplierContact;
    public TableView <ItemTm>itemTable;
    public TableColumn clmBarcode;
    public TableColumn clmDescription;
    public TableColumn clmQuantitiy;
    public TableColumn clmCost;
    public TableColumn clmBroughtId;
    public TableColumn clmBroughtDate;
    public TextField txtSearchBarcode;
    public TextField txtSearchDescription;
    public TextField txtSearchQuantity;
    public TextField txtSearchCost;
    int tableSelectedRowRemove=-1;
    public void initialize(){
        itemTable.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            tableSelectedRowRemove=(int)newValue;

        });

        txtBarcode.setOnKeyReleased(event -> {
            Pattern p = Pattern.compile("^B-[0-1][0-2][0-9]");//. represents single character
            Matcher m = p.matcher(txtBarcode.getText());
            boolean b = m.matches();
            if (m.matches()){
                txtBarcode.setStyle("-fx-border-color: white");
            }else{
                txtBarcode.setStyle("-fx-border-color: Red");
            }
        });
        txtSuplierNic.setOnKeyReleased(event -> {
            Pattern p = Pattern.compile("[0-9]{9}(V)$");//. represents single character
            Matcher m = p.matcher(txtSuplierNic.getText());
            boolean b = m.matches();
            if (m.matches()){
                txtSuplierNic.setStyle("-fx-border-color: white");
            }else{
                txtSuplierNic.setStyle("-fx-border-color: Red");
            }
        });

    }
    public void btnSearch(ActionEvent actionEvent) {
        int a=0;
        try {
            ResultSet set = new ItemController().search();
            while (set.next()){
                if (set.getString(1).equals(txtSearchBarcode.getText())){
                    txtSearchDescription.setText(set.getString(2));
                    txtSearchQuantity.setText(set.getString(3));
                    txtSearchCost.setText(set.getString(4));
                   a=1;
                }else{

                }
            }
            if (a==0){
                txtSearchCost.clear();
                txtSearchQuantity.clear();
                txtSearchDescription.clear();
                txtSearchBarcode.clear();
                new Alert(Alert.AlertType.ERROR,"NO data found").show();
            }else{

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnUpdate(ActionEvent actionEvent) {
        try {
            int check = new ItemController().update(new Item(txtSearchBarcode.getText(), txtSearchDescription.getText(),
                    Integer.parseInt(txtSearchQuantity.getText()), Double.parseDouble(txtSearchCost.getText())));
            if (check>0){
                txtSearchCost.clear();
                txtSearchQuantity.clear();
                txtSearchDescription.clear();
                txtSearchBarcode.clear();
                new Alert(Alert.AlertType.INFORMATION,"Update Successfully").show();
            }else {
                txtSearchCost.clear();
                txtSearchQuantity.clear();
                txtSearchDescription.clear();
                txtSearchBarcode.clear();
                new Alert(Alert.AlertType.ERROR,"Sorry cannot update").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void bntDelete(ActionEvent actionEvent) {
        try {
            if (new ItemController().delete(txtSearchBarcode.getText())){
                txtSearchCost.clear();
                txtSearchQuantity.clear();
                txtSearchDescription.clear();
                txtSearchBarcode.clear();
                new Alert(Alert.AlertType.INFORMATION,"Delete Successfully").show();
            }else{
                txtSearchCost.clear();
                txtSearchQuantity.clear();
                txtSearchDescription.clear();
                txtSearchBarcode.clear();
                new Alert(Alert.AlertType.INFORMATION,"Something went wrong").show();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        if (tableSelectedRowRemove==-1){
            new Alert(Alert.AlertType.ERROR,"Please Select The row").show();
        }else{
            obList.remove(tableSelectedRowRemove);
            itemTable.refresh();
        }
    }

    ObservableList<ItemTm> obList= FXCollections.observableArrayList();
    public void btnAddTableOnnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String brougthId = getbuyId();
        obList.add(new ItemTm(txtBarcode.getText(),txtDescription.getText(),Integer.parseInt(txtQuantity.getText()),
                Double.parseDouble(txtCost.getText()),brougthId,DashBordFormController.lblDate1.getText()));
        itemTable.setItems(obList);
        clmBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clmQuantitiy.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        clmBroughtId.setCellValueFactory(new PropertyValueFactory<>("broughtId"));
        clmBroughtDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
//+++++++++++++Start Add item to Store ++++++++++++++++++++++++++++++++++++++++
    public void btnAddStoreOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String buyId = getbuyId();
        Connection con=null;

        Item item=new Item(txtBarcode.getText(),txtDescription.getText(),Integer.parseInt(txtQuantity.getText()),Double.parseDouble(txtCost.getText()));
        //System.out.println(Double.parseDouble(txtCost.getText()));
        //System.out.println(Integer.parseInt(txtCost.getText()));

        Suplier suplier=new Suplier(txtSuplierNic.getText(),txtSuplierName.getText(),Integer.parseInt(txtSuplierContact.getText()));

        ArrayList<BoughtDetail> list =new ArrayList<>();
        list.add(new BoughtDetail(txtBarcode.getText(),buyId,Integer.parseInt(txtQuantity.getText()),Double.parseDouble(txtCost.getText())));

        Buy buy=new Buy(buyId,txtSuplierNic.getText(),DashBordFormController.lblDate1.getText(),list);

        if (new ItemController().addItem(obList,item,buy,suplier)){
            obList.clear();
            itemTable.refresh();
            txtBarcode.clear();
            txtDescription.clear();
            txtQuantity.clear();
            txtCost.clear();
            txtSuplierNic.clear();
            txtSuplierName.clear();
            txtSuplierContact.clear();
            new Alert(Alert.AlertType.INFORMATION,"Added Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"no added").show();
        }
        for (ItemTm temp:obList) {
        }

    }

//+++++++++++++End Add item to Store ++++++++++++++++++++++++++++++++++++++++
    private String getbuyId() throws SQLException, ClassNotFoundException {
        /*Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT Buy_id FROM buy ORDER BY Buy_id DESC LIMIT 1");
        ResultSet set = stm.executeQuery();*/
        ResultSet set = new ItemController().getBuyId();
        if (set.next()){
            int tempId = Integer.
                    parseInt(set.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<9){
                return "BY-00"+tempId;
            }else if(tempId<99 && tempId>9){
                return "BY-0"+tempId;
            }else{
                return "BY-"+tempId;
            }
        }else{
            return "BY-001";
        }

    }
}
