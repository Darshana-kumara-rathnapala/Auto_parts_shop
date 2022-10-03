package controller;

import db.DbConnection;
import javafx.collections.ObservableList;
import modle.Order;
import modle.OrderDetail;
import modle.Payment;
import tm.OrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderController {
    public ResultSet getItem(String barcode) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm= con.prepareStatement("SELECT * FROM item WHERE Barcode=?");
        stm.setObject(1,barcode);
        return stm.executeQuery();
    }
    public ResultSet getEmployeeNic() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm= con.prepareStatement("SELECT Id FROM employee");
        return stm.executeQuery();
    }
    public  ResultSet getInvoice() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT Invoice_no FROM `order` ORDER BY Invoice_no DESC LIMIT 1");
        return stm.executeQuery();
    }
    public String getName(String id) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT Name FROM employee WHERE Id=?");
        stm.setObject(1,id);
        ResultSet set = stm.executeQuery();
       String name=new String();
        while (set.next()){
            name= set.getString(1);
        }
        return name;
    }
    public  ResultSet getPayCode() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT Pay_code FROM payment ORDER BY Pay_code DESC LIMIT 1");
        return stm.executeQuery();
    }

    public boolean addOrder(Order order, Payment payment) {
        Connection con=null;
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO `order` VALUES(?,?,?,?)");
            stm.setObject(1,order.getInvoiceNo());
            stm.setObject(2,order.getEmployeeId());
            stm.setObject(3,order.getOrderDate());
            stm.setObject(4,order.getOrderTime());

            if (stm.executeUpdate()>0){
                // OrderDetail orderDetail=order.getItems();
                if (addOrderDetail(order.getItems(),payment)){
                    con.commit();
                    return true;
                }else {
                    con.rollback();
                    return false;
                }
            }else {
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
    public boolean addOrderDetail(ArrayList<OrderDetail> list, Payment payment) throws SQLException, ClassNotFoundException {
        int chek=0;
        Connection con = DbConnection.getInstance().getConnection();
        for (OrderDetail temp:list ) {
            PreparedStatement stm = con.prepareStatement("INSERT INTO order_detail VALUES(?,?,?,?)");
            stm.setObject(1,temp.getInvoiceNo());
            stm.setObject(2,temp.getBarCode());
            stm.setObject(3,temp.getUnitPrice());
            stm.setObject(4,temp.getQtyForSell());
            chek=stm.executeUpdate();
        }
        if (chek>0){
            if (addPayment(payment)){
                return  true;
            }else{
                con.rollback();
                return false;
            }
        }else{
            con.rollback();
            return false;
        }
    }
    public boolean addPayment(Payment payment) throws SQLException, ClassNotFoundException {
        Connection con=DbConnection.getInstance().getConnection();
        ResultSet set = con.prepareStatement("SELECT*FROM payment").executeQuery();
        while (set.next()){
            if (payment.getPayCode().equals(set.getString(1))){
                PreparedStatement stm = con.prepareStatement("UPDATE payment SET Cash=?,Discount=?,Balance=? WHERE Pay_code=?");
                stm.setObject(1,payment.getCash());
                stm.setObject(2,payment.getDiscount());
                stm.setObject(3,payment.getBalance());
                stm.setObject(4,payment.getPayCode());
                return stm.executeUpdate()>0;
            }
        }

        con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("INSERT INTO payment VALUES(?,?,?,?,?,?)");
        stm.setObject(1,payment.getPayCode());
        stm.setObject(2,payment.getInvoiceNo());
        stm.setObject(3,payment.getTotalPrice());
        stm.setObject(4,payment.getCash());
        stm.setObject(5,0);
        stm.setObject(6,payment.getBalance());

        if (stm.executeUpdate()>0){
            return true;
        }else{
            con.rollback();
            return false;
        }
    }
    public boolean updateQty(ArrayList<OrderDetail> items) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        int chek=0;
        for (OrderDetail temp:items) {
            PreparedStatement stm = con.prepareStatement("UPDATE item SET Qty_on_hand=(Qty_on_hand-" + temp.getQtyForSell() + ")" +
                    " WHERE Barcode ='" + temp.getBarCode() + "'");
            chek=stm.executeUpdate();
        }
        return chek>0;
    }
    public ResultSet getDetail() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT o.Invoice_no,od.Barcode,o.Date,o.Time,i.Description,od.unit_price,od.Qty FROM `order` o INNER JOIN order_detail od ON o.Invoice_no=od.Invoice_no INNER JOIN item i ON od.Barcode=i.Barcode;");
        return  stm.executeQuery();
    }
    public boolean checkInvoice(String i) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT Invoice_no FROM `order` WHERE Invoice_no=?");
        stm.setObject(1,i);
        ResultSet set = stm.executeQuery();
        if (set.next()){
            return true;
        }
        return false;
    }
    public boolean updateDetail(String barcode,String invoice,int qty,int a) throws SQLException, ClassNotFoundException {
        int q=0;
        //double d=0.0;
        Connection con1 = DbConnection.getInstance().getConnection();
        ResultSet set = con1.prepareStatement("SELECT Qty FROM order_detail WHERE Barcode ='" + barcode + "'").executeQuery();
        if (set.next()){
            System.out.println("0");
            q=Integer.parseInt(set.getString(1));
        }
        /*ResultSet set1 = con1.prepareStatement("SELECT unit_price FROM item WHERE Barcode ='" + barcode + "'").executeQuery();
        if (set1.next()){
            d=Double.parseDouble(set1.getString(1));
        }*/

        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("UPDATE order_detail SET Qty=? WHERE Barcode =?");
        stm.setObject(1,qty);
        stm.setObject(2,barcode);

        if (stm.executeUpdate()>0){
            System.out.println("1");
            if (con.prepareStatement("UPDATE item SET Qty_on_hand=(Qty_on_hand-" + a + ") WHERE Barcode ='" + barcode +"'").executeUpdate()>0){
                System.out.println("2");
                //double p=a*d;
                //con.prepareStatement("UPDATE payment SET Totle_price=(Totle_price+"+p+") WHERE Invoice_no +'"+invoice+"'");
                return  true;
            }
            return true;
        }
        return false;
    }

    public boolean removeOrder(String invoice) throws SQLException, ClassNotFoundException {
        Connection con1 = DbConnection.getInstance().getConnection();
        ResultSet set = con1.prepareStatement("SELECT Barcode,Qty FROM order_detail WHERE Invoice_no='" + invoice + "'").executeQuery();
        if (con1.prepareStatement("DELETE FROM `order` WHERE Invoice_no ='" + invoice + "'").
                executeUpdate()>0){
            //if (set.next()){
            while (set.next()){
                    int i = Integer.parseInt(set.getString(2));
                    PreparedStatement stm = con1.prepareStatement("UPDATE item SET Qty_on_hand=(Qty_on_hand+"+i+") WHERE Barcode=?");
                    stm.setObject(1,set.getString(1));
                    int c=stm.executeUpdate();
                }

            return true;
        }else{
            return false;
        }
        //return false;
    }
    public ResultSet modifyTransaction(String invoice) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        return con.prepareStatement("SELECT*FROM payment WHERE Invoice_no='"+invoice+"'").executeQuery();
    }
}
