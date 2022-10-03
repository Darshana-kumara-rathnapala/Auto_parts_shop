package controller;

import db.DbConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

public class ItemController {

    public boolean addItem(ObservableList<ItemTm> obList, Item item, Buy buy, Suplier suplier )  {
        Connection con=null;
        int check=0;

        try {
            con= DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            for (ItemTm temp:obList) {
                double unitPrice=temp.getCost()/temp.getQuantity();
                unitPrice=unitPrice+(10*(unitPrice/100));
                PreparedStatement stm = con.prepareStatement("INSERT INTO item VALUES(?,?,?,?)");
                stm.setObject(1,temp.getBarcode());
                stm.setObject(2,temp.getDescription());
                stm.setObject(3,unitPrice);
                stm.setObject(4,temp.getQuantity());

                check=stm.executeUpdate();
            }
            if (check>0){
                if (suplier(obList,suplier,buy)){
                    con.commit();
                    return true;

                }else{
                    con.rollback();
                    return false;
                }
            }else{
                con.rollback();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

       return false;

    }
    private boolean suplier(ObservableList<ItemTm> obList,Suplier suplier,Buy buy) throws SQLException, ClassNotFoundException {
        String check="n";
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm1 = con.prepareStatement("SELECT Suplier_id FROM suplier");
        ResultSet set = stm1.executeQuery();
        while (set.next()){
            if (suplier.getSupId().equals(set.getString(1))){
                check="c";
            }
        }
        if (check.equals("n")){
            PreparedStatement stm = con.prepareStatement("INSERT INTO suplier VALUES (?,?,?)");
            stm.setObject(1,suplier.getSupId());
            stm.setObject(2,suplier.getSupName());
            stm.setObject(3,suplier.getSupContact());

            if (stm.executeUpdate()>0){
                if (buy(buy,obList)){
                    return true;
                }else{
                    con.rollback();
                    return false;
                }
            }else{
                con.rollback();
                return false;
            }
        }else {
            if (buy(buy,obList)){
                return true;
            }else{
                con.rollback();
                return false;
            }
        }
        //return false;
    }
    private boolean buy(Buy buy,ObservableList<ItemTm> obList) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("INSERT INTO buy VALUES(?,?,?)");
        stm.setObject(1,buy.getbId());
        stm.setObject(2,buy.getSupId());
        stm.setObject(3,buy.getDate());

        if (stm.executeUpdate()>0){
            if (broughtDetails(obList)){

                return true;
            }else {
                con.rollback();
                return false;
            }
        }else {
            con.rollback();
            return false;
        }
        //return false;
    }
    private boolean broughtDetails(ObservableList<ItemTm> obList) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        int a=0;
        for (ItemTm temp:obList) {
            PreparedStatement stm = con.prepareStatement("INSERT INTO brought_detail VALUES(?,?,?,?)");
            stm.setObject(1,temp.getBarcode());
            stm.setObject(2,temp.getBroughtId());
            stm.setObject(3,temp.getQuantity());
            stm.setObject(4,temp.getCost());
            a=stm.executeUpdate();
        }

        if (a>0){
            //System.out.println("true5");
            return true;
        }
        con.rollback();
        return false;
    }
    public ResultSet getBuyId() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT Buy_id FROM buy ORDER BY Buy_id DESC LIMIT 1");
        ResultSet set = stm.executeQuery();
        return set;
    }
    public  ResultSet search() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        PreparedStatement stm = con.prepareStatement("SELECT od.barcode,i.Description,od.Qty,od.Cost FROM brought_detail od INNER JOIN item i ON od.Barcode=i.Barcode");
        //PreparedStatement stm = con.prepareStatement("SELECT*FROM item WHERE Barcode=?");
        //stm.setObject(1,barcode);
        return stm.executeQuery();
        //return set;
    }

    public int update(Item item) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm1 = con.prepareStatement("SELECT*FROM item WHERE Barcode=?");
        stm1.setObject(1,item.getBarCode());
        ResultSet rst = stm1.executeQuery();
        if (rst.next()){
            String d = rst.getString(2);
            double u = Double.parseDouble(rst.getString(3));
            int q=Integer.parseInt(rst.getString(4));
            if (item.getDescription().equals(d) && item.getUnitPrice()==u && item.getQtyOnHand()==q){
                return 0;
            }else {
               // System.out.println("upaddte");
                double unitPrice=item.getUnitPrice()/item.getQtyOnHand();
                unitPrice=unitPrice+(10*(unitPrice/100));
                PreparedStatement stm = con.prepareStatement("UPDATE item SET Description=?,unit_price=?,Qty_on_hand=? WHERE Barcode=?");
                stm.setObject(4,item.getBarCode());
                stm.setObject(1,item.getDescription());
                stm.setObject(2,unitPrice);
                stm.setObject(3,item.getQtyOnHand());

                PreparedStatement stm2 = con.prepareStatement("UPDATE brought_detail SET Qty=?,Cost=? WHERE Barcode=?");
                stm2.setObject(1,item.getQtyOnHand());
                stm2.setObject(2,item.getUnitPrice());
                stm2.setObject(3,item.getBarCode());
                stm2.executeUpdate();
                /*PreparedStatement stm = con.prepareStatement("UPDATE item SET Description=?,unit_price=?,Qty_on_hand=? WHERE Barcode=?");
                stm.setObject(1,item.getDescription());
                stm.setObject(2,item.getUnitPrice());
                stm.setObject(3,item.getQtyOnHand());
                stm.setObject(4,item.getBarCode());*/
                return stm.executeUpdate();
            }
        }else{
            return 0;
        }
    }
    public boolean delete(String barcode) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm1= con.prepareStatement("DELETE FROM  brought_detail WHERE Barcode=?");
        stm1.setObject(1,barcode);
        if (stm1.executeUpdate()>0){
            PreparedStatement stm = con.prepareStatement("DELETE FROM item WHERE Barcode=?");
            stm.setObject(1,barcode);
            return stm.executeUpdate()>0;
        }else{
            return false;
        }
    }
}
