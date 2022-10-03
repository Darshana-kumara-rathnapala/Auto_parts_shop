package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import tm.ReportBuyTm;
import tm.ReportSalaryTm;
import tm.ReportSellTm;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TranceActionFormController {
    public BarChart chartrance;
    public CategoryAxis y;
    public NumberAxis x;
    public AnchorPane root1;
    public AnchorPane root2;
    public DatePicker selectDateId;
    String dat;
    //ResultSet sell1;

    public void initialize(){
        /*XYChart.Series<String,Number> series1=new XYChart.Series<>();
        series1.setName("sell");
        series1.getData().add(new XYChart.Data<>("report",75));

        XYChart.Series<String,Number> series2=new XYChart.Series<>();
        series2.setName("buy");
        series2.getData().add(new XYChart.Data<>("report",120));

        XYChart.Series<String,Number> series3=new XYChart.Series<>();
        series3.setName("salary");
        series3.getData().add(new XYChart.Data<>("report",10));
        chartrance.getData().addAll(series1,series2,series3);*/

        //selectDateId.setDayCellFactory();
    }

    public void sellOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        root2.getChildren().clear();
        passSell();
        root2.getChildren().add(FXMLLoader.load(getClass().getResource("../view/SellDetailForm.fxml")));
    }

    public void buyOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        root2.getChildren().clear();
        passBuy();
        root2.getChildren().add(FXMLLoader.load(getClass().getResource("../view/BuyDetailForm.fxml")));
    }

    public void salaryOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        root2.getChildren().clear();
        passSalary();
        root2.getChildren().add(FXMLLoader.load(getClass().getResource("../view/SalaryDetailForm.fxml")));
    }

    private void passSell() throws SQLException {
        try {
            SellDetailFormController.obList=new ReportController().getSell(dat);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void passBuy() throws SQLException {
        try {
            BuyDetailFormController.obList=new ReportController().getBuy(dat);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void passSalary() throws SQLException {
        try {
            SalaryDetailFormController.obList=new ReportController().getSalary(dat);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void getCalenderDate(ActionEvent actionEvent) {
        double p=0;
        double b=0;
        double s=0;
        chartrance.getData().clear();
        LocalDate date1=selectDateId.getValue();
        String date = String.valueOf(date1);
        dat=date;

        try {
            ArrayList<ReportSellTm> sell = new ReportController().getSell(date);
            for (ReportSellTm temp:sell) {
                p=p+(temp.getTotleBill()-temp.getDiscount());
            }
            ArrayList<ReportBuyTm> buy = new ReportController().getBuy(date);
            for (ReportBuyTm temp:buy) {
                b=b+temp.getCost();
            }
            ArrayList<ReportSalaryTm> salary =new ReportController().getSalary(date);
            for (ReportSalaryTm temp:salary ) {
                s=s+temp.getSalary();
            }
            /*while (salary.next()){
                if (salary.getString(7).equals(date)){
                    s=s+Double.parseDouble(salary.getString(3));
                }else{

                }
            }*/
            //+++++++++++++++++++++++++++++ Start Chart++++++++++++++++++++++++++++++++++++++
            XYChart.Series<String,Number> series1=new XYChart.Series<>();
            series1.setName("sell");
            series1.getData().add(new XYChart.Data<>("report",p));

            XYChart.Series<String,Number> series2=new XYChart.Series<>();
            series2.setName("buy");
            series2.getData().add(new XYChart.Data<>("report",b));

            XYChart.Series<String,Number> series3=new XYChart.Series<>();
            series3.setName("salary");
            series3.getData().add(new XYChart.Data<>("report",s));
            chartrance.getData().addAll(series1,series2,series3);
    //+++++++++++++++++++++++++++++ End Chart++++++++++++++++++++++++++++++++++++++
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
