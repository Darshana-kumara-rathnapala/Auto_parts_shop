package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tm.ModifyTm;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModifyOrderFormController {
    public AnchorPane root1;
    public TextField txtEnterInvoiceNo;
    public TableView<ModifyTm> tblInvoice;
    public TableColumn clmBarcode;
    public TableColumn clmDate;
    public TableColumn clmTime;
    public TableColumn clmDescription;
    public TableColumn clmUnitPrice;
    public TableColumn clmQtyForCustomer;
    public TextField txtUnitPrice;
    public TextField txtQtyForCustomer;
    public Label txtBarcode;
    public Label txtDescription;
    int i=-1;

    public void initialize(){
        tblInvoice.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            i= ( int ) newValue;
            loadDetail(i);
        });
    }

    ArrayList<ModifyTm>list2=new ArrayList();
    public void searchInvoice(MouseEvent mouseEvent) {
        ArrayList<ModifyTm>list=new ArrayList();
        list2=list;
        try {
            ResultSet set = new OrderController().getDetail();
           if (new OrderController().checkInvoice(txtEnterInvoiceNo.getText())){
               while (set.next()){
                   if (set.getString(1).equals(txtEnterInvoiceNo.getText())){
                       list.add(new ModifyTm(set.getString(2),set.getString(3),set.getString(4),
                               set.getString(5),Double.parseDouble(set.getString(6)),
                               Integer.parseInt(set.getString(7))));

                   }else{

                   }
               }
           }else {
               new Alert(Alert.AlertType.WARNING,"Invalid Invoice no").show();
           }
           txtQtyForCustomer.clear();
           txtBarcode.setText("barcode");
           txtDescription.setText("description");
           txtUnitPrice.clear();
            tblInvoice.getItems().clear();
            tblInvoice.getItems().addAll(list);
           clmBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
            clmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            clmTime.setCellValueFactory(new PropertyValueFactory<>("time"));
            clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            clmUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
            clmQtyForCustomer.setCellValueFactory(new PropertyValueFactory<>("qtyForCustomer"));

            //tblInvoice.refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void bntUpadte(ActionEvent actionEvent) {
        int a=Integer.parseInt(txtQtyForCustomer.getText())-list2.get(i).getQtyForCustomer();
        try {
            if (list2.get(i).getQtyForCustomer()!=Integer.parseInt(txtQtyForCustomer.getText())){
                if (list2.get(i).getQtyForCustomer()<Integer.parseInt(txtQtyForCustomer.getText())){
                    if (new OrderController().updateDetail(txtBarcode.getText(),txtEnterInvoiceNo.getText(),Integer.parseInt(txtQtyForCustomer.getText()),a)){
                        new Alert(Alert.AlertType.CONFIRMATION,"Update success").show();
          //+++++++++++++++++++++++++Refresh tables Start+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                        ArrayList<ModifyTm>list=new ArrayList();
                        list2=list;
                        try {
                            ResultSet set = new OrderController().getDetail();
                            if (new OrderController().checkInvoice(txtEnterInvoiceNo.getText())){
                                while (set.next()){
                                    if (set.getString(1).equals(txtEnterInvoiceNo.getText())){
                                        list.add(new ModifyTm(set.getString(2),set.getString(3),set.getString(4),
                                                set.getString(5),Double.parseDouble(set.getString(6)),
                                                Integer.parseInt(set.getString(7))));

                                    }else{

                                    }
                                }
                            }else {
                                new Alert(Alert.AlertType.WARNING,"Invalid Invoice no").show();
                            }
                            txtQtyForCustomer.clear();
                            txtBarcode.setText("barcode");
                            txtDescription.setText("description");
                            txtUnitPrice.clear();
                            tblInvoice.getItems().clear();
                            tblInvoice.getItems().addAll(list);
                            clmBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
                            clmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                            clmTime.setCellValueFactory(new PropertyValueFactory<>("time"));
                            clmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
                            clmUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
                            clmQtyForCustomer.setCellValueFactory(new PropertyValueFactory<>("qtyForCustomer"));

                            //tblInvoice.refresh();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
        //+++++++++++++++Refresh tables end+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                    }else {
                        new Alert(Alert.AlertType.ERROR,"Update not success").show();
                    }

                }else{
                    System.out.println("please ");
                }

            }else{
                System.out.println("please update");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnCancel(ActionEvent actionEvent) throws IOException {
        root1.getChildren().clear();
        root1.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ModifyOrderForm.fxml"))) ;
    }
    private void loadDetail(int i){
        if (i>-1){
            txtBarcode.setText(list2.get(i).getBarcode());
            txtUnitPrice.setText(String.valueOf(list2.get(i).getUnitPrice()));
            txtDescription.setText(list2.get(i).getDescription());
            txtQtyForCustomer.setText(String.valueOf(list2.get(i).getQtyForCustomer()));
        }else{
            System.out.println("no selected");
        }
    }
}
