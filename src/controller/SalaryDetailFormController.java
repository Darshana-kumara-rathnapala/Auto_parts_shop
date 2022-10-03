package controller;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tm.ReportBuyTm;
import tm.ReportSalaryTm;

import java.util.ArrayList;

public class SalaryDetailFormController {
    public TableView tblSalary;
    public TableColumn clmPaymnet_no;
    public TableColumn clmSalary;
    public TableColumn clmEpf;
    public TableColumn clmNetSalary;
    public Label lblTotSalary;
    public Label lblTotEpf;
    public Label clmTotNetSalary;
    public static ArrayList<ReportSalaryTm> obList = new ArrayList();
    public void initialize(){
        clmPaymnet_no.setCellValueFactory(new PropertyValueFactory<>("paymnet_no"));
        clmSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        clmEpf.setCellValueFactory(new PropertyValueFactory<>("epf"));
        clmNetSalary.setCellValueFactory(new PropertyValueFactory<>("netSalary"));
        table();
    }
    public void table(){
        double salary=0;
        double epf=0;
        double netSalary=0;
        if (obList!=null){
            tblSalary.getItems().addAll(obList);
            for (ReportSalaryTm temp:obList ) {
                salary=salary+temp.getSalary();
                epf=epf+temp.getEpf();
                netSalary=netSalary+temp.getNetSalary();
                //pay=pay+te
            }
            lblTotSalary.setText(String.valueOf(salary));
            lblTotEpf.setText(String.valueOf(epf));
            clmTotNetSalary.setText(String.valueOf(netSalary));
        }else{

        }
    }
}
