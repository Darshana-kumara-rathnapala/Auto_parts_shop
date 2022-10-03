package controller;

import db.DbConnection;
import tm.ReportBuyTm;
import tm.ReportSalaryTm;
import tm.ReportSellTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportController {
    public ArrayList<ReportSellTm> getSell(String date) throws SQLException, ClassNotFoundException {
        ArrayList<ReportSellTm> list =new ArrayList();
        ResultSet set=DbConnection.getInstance().getConnection().prepareStatement("SELECT o.Invoice_no,o.Date,p.Totle_price,p.Cash,p.Discount,p.Balance FROM `order` o INNER JOIN payment p ON o.Invoice_no=p.Invoice_no WHERE Date='"+date+"'").executeQuery();
        while (set.next()){
            list.add(new ReportSellTm(set.getString(1),set.getString(2),Double.parseDouble(set.getString(3)),Double.parseDouble(set.getString(4)),Double.parseDouble(set.getString(5)),Double.parseDouble(set.getString(6))));
        }
        return list;
    }
    public ArrayList<ReportBuyTm> getBuy(String date) throws SQLException, ClassNotFoundException {
        ArrayList<ReportBuyTm> list =new ArrayList();
        ResultSet set =DbConnection.getInstance().getConnection().prepareStatement("SELECT bd.Buy_id,bd.Cost,b.Brought_date FROM brought_detail bd INNER JOIN buy b ON  bd.Buy_id=b.Buy_id").executeQuery();

        while (set.next()){
            if (set.getString(3).equals(date)){
                list.add(new ReportBuyTm(set.getString(1),Double.parseDouble(set.getString(2)),
                        Double.parseDouble(set.getString(2)),0));
            }
        }
        return list;
    }
    public ArrayList<ReportSalaryTm> getSalary(String date) throws SQLException, ClassNotFoundException {
        ArrayList<ReportSalaryTm> list = new ArrayList();
        ResultSet set =DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM pay_roll").executeQuery();
        while (set.next()){
            if (set.getString(7).equals(date)){
                double epf=2*(Double.parseDouble(set.getString(3))/100);
                list.add(new ReportSalaryTm(set.getString(1),Double.parseDouble(set.getString(3)),epf,Double.parseDouble(set.getString(5))));
            }else{

            }
        }
        return list;
    }
}
