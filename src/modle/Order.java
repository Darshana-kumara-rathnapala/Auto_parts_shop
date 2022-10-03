package modle;

import java.sql.Time;
import java.util.ArrayList;

public class Order {
    private String invoiceNo;
    private String employeeId;
    private String orderDate;
    private String orderTime;
    private ArrayList<OrderDetail> items;

    public Order() {
    }

    public Order(String invoiceNo, String employeeId, String orderDate, String orderTime, ArrayList<OrderDetail> items) {
        this.invoiceNo = invoiceNo;
        this.employeeId = employeeId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.items = items;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public ArrayList<OrderDetail> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderDetail> items) {
        this.items = items;
    }
}
