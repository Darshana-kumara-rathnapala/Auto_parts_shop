package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tm.ModifyTm;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RemoveOrderFormController {
    public AnchorPane root1;
    public TextField txtEnterInvoiceNumber;
    public Label lblOrderDate;
    public Label lblItemeAmount;
    public Label lblOrderTime;
    public Label lblPrice;

    public void searchOnAction(MouseEvent mouseEvent) {
        int count=0;
        double price=0.0;
        try {
            ResultSet set = new OrderController().getDetail();
            if (new OrderController().checkInvoice(txtEnterInvoiceNumber.getText())){
                while (set.next()){
                    if (set.getString(1).equals(txtEnterInvoiceNumber.getText())){
                        //while (set.next()){
                            count=count+Integer.parseInt(set.getString(7));
                            price=price+Double.parseDouble(set.getString(6))*Integer.parseInt(set.getString(7));
                            lblOrderDate.setText(set.getString(3));
                            lblOrderTime.setText(set.getString(4));

                        //}
                    }else{
                        System.out.println(" ..... ");
                    }
                }
                lblItemeAmount.setText(String.valueOf(count));
                lblPrice.setText(String.valueOf(price));
            }else {
                new Alert(Alert.AlertType.WARNING,"Invalid Invoice no").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnRemove(ActionEvent actionEvent) {
        try {
            if (new OrderController().removeOrder(txtEnterInvoiceNumber.getText())){
                txtEnterInvoiceNumber.clear();
                lblOrderDate.setText("Order date");
                lblOrderTime.setText("Order time");
                lblItemeAmount.setText("Amount item");
                lblPrice.setText("Totle price");
                new Alert(Alert.AlertType.CONFIRMATION,"Order Deleted!").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Invalid Invoice no").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void bntCancel(ActionEvent actionEvent) {
        txtEnterInvoiceNumber.clear();
        lblOrderDate.setText("Order date");
        lblOrderTime.setText("Order time");
        lblItemeAmount.setText("Amount item");
        lblPrice.setText("Totle price");
    }
}
