package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tm.ReportSellTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SellDetailFormController {
    public AnchorPane root3;
    public TableView<ReportSellTm> tblSell;
    public TableColumn clmInvoiceNo;
    public TableColumn clmTotleBill;
    public TableColumn clmCash;
    public TableColumn clmBalance;
    public Label lblTot;
    public Label lblCash;
    public Label lblBalance;
    public static ArrayList<ReportSellTm> obList = new ArrayList();
    public void initialize(){
        clmInvoiceNo.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
        clmTotleBill.setCellValueFactory(new PropertyValueFactory<>("totleBill"));
        clmCash.setCellValueFactory(new PropertyValueFactory<>("cash"));
        clmBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        table();
    }
    public void table(){
        double bill=0;
        double cash=0;
        double balance=0;
        if (obList!=null){
            tblSell.getItems().addAll(obList);
            for (ReportSellTm temp:obList ) {
                bill=bill+temp.getTotleBill();
                cash=cash+temp.getCash();
                balance=balance+temp.getBalance();
            }
            lblTot.setText(String.valueOf(bill));
            lblCash.setText(String.valueOf(cash));
            lblBalance.setText(String.valueOf(balance));
        }else{
            //System.out.println("no ");
        }
    }
}
