package controller;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tm.ReportBuyTm;
import tm.ReportSellTm;

import java.util.ArrayList;

public class BuyDetailFormController {
    public TableView tblBuy;
    public TableColumn clmBuy_id;
    public TableColumn clmCost;
    public TableColumn clmPayment;
    public TableColumn clmBalance;
    public Label lblCost;
    public Label lblPayment;
    public Label lblBalance;
    public static ArrayList<ReportBuyTm> obList = new ArrayList();

    public void initialize(){
        clmBuy_id.setCellValueFactory(new PropertyValueFactory<>("buy_id"));
        clmCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        clmPayment.setCellValueFactory(new PropertyValueFactory<>("payment_cash"));
        clmBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        table();
    }
    public void table(){
        double cost=0;
        double pay=0;
        if (obList!=null){
            tblBuy.getItems().addAll(obList);
            for (ReportBuyTm temp:obList ) {
                cost=cost+temp.getCost();
                //pay=pay+te
            }
            lblCost.setText(String.valueOf(cost));
            lblPayment.setText(String.valueOf(cost));
            lblBalance.setText("0");
        }else{

        }
    }
}
