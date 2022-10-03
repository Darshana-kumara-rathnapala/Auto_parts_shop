package controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import modle.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModifyTransactionFormController {
    public TextField txtSearchInvoiceNo;
    public Label lblPayCode;
    public Label lblTotleAmount;
    public TextField txtDiscount;
    public TextField txtCash;
    public Label lblBalance;
    public FontAwesomeIconView searchId;

    public void btnUpdate(ActionEvent actionEvent) {
        double netPrice=Double.parseDouble(lblTotleAmount.getText())-Double.parseDouble(txtDiscount.getText());
        lblBalance.setText(String.valueOf(Double.parseDouble(txtCash.getText())-netPrice));
        try {
            if (new OrderController().addPayment(new Payment(lblPayCode.getText(),txtSearchInvoiceNo.getText(),
                    Double.parseDouble(lblTotleAmount.getText()),Double.parseDouble(txtCash.getText()),
                    Double.parseDouble(lblBalance.getText()),Double.parseDouble(txtDiscount.getText())))){
                new Alert(Alert.AlertType.CONFIRMATION,"Transaction Updated1").show();
            }else{

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnCancel(ActionEvent actionEvent) {
        lblPayCode.setText("Pay code");
        lblTotleAmount.setText("Totle amount");
        txtCash.clear();
        lblBalance.setText("Balance");
        txtDiscount.clear();
        txtSearchInvoiceNo.clear();
    }

    public void searchOnAction(MouseEvent mouseEvent) {
        try {
            ResultSet set = new OrderController().modifyTransaction(txtSearchInvoiceNo.getText());
            if (set.next()){
                lblPayCode.setText(set.getString(1));
                lblTotleAmount.setText(set.getString(3));
                txtCash.setText(set.getString(4));
                txtDiscount.setText(set.getString(5));
                lblBalance.setText(set.getString(6));
            }else{
                new Alert(Alert.AlertType.WARNING,"Invalid Invoice no!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void searchFromEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode()==KeyCode.ENTER){
            try {
                ResultSet set = new OrderController().modifyTransaction(txtSearchInvoiceNo.getText());
                if (set.next()){
                    lblPayCode.setText(set.getString(1));
                    lblTotleAmount.setText(set.getString(3));
                    txtCash.setText(set.getString(4));
                    txtDiscount.setText(set.getString(5));
                    lblBalance.setText(set.getString(6));
                }else{
                    new Alert(Alert.AlertType.WARNING,"Invalid Invoice no!").show();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void discountEnterOnAction(KeyEvent keyEvent) {
        if (keyEvent.getCode()==KeyCode.ENTER){
            double netPrice=Double.parseDouble(lblTotleAmount.getText())-Double.parseDouble(txtDiscount.getText());
            lblBalance.setText(String.valueOf(Double.parseDouble(txtCash.getText())-netPrice));
        }
    }

    public void cashEnterOnAction(KeyEvent keyEvent) {
        if (keyEvent.getCode()==KeyCode.ENTER){
            double netPrice=Double.parseDouble(lblTotleAmount.getText())-Double.parseDouble(txtDiscount.getText());
            lblBalance.setText(String.valueOf(Double.parseDouble(txtCash.getText())-netPrice));
        }
    }
}
