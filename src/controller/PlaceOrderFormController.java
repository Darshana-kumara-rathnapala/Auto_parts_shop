package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import modle.Order;
import modle.OrderDetail;
import modle.Payment;
import tm.BillTm;
import tm.ItemTm;
import tm.OrderTm;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.scene.paint.Color.RED;

public class PlaceOrderFormController {
    public AnchorPane root1;
    public TableView tblOrder;
    public TableColumn clmInvoiceNo;
    public TableColumn clmBarcode;
    public TableColumn cmDescription;
    public TableColumn clmQty;
    public TableColumn clmPrice;
    public JFXComboBox cmbEnployeeId;
    public JFXTextField txtEnterBarcode;
    public Label lblDescription;
    public Label lblUnitPrice;
    public JFXTextField txtQtyForCustomer;
    public Label lblInvoiceNo;
    public Label lblTotlePrice;
    public Label lblDate;
    public Label lblTime;
    public String nic;
    public int qty;
    public double p=0;
    public TextField txtcashAmount;
    public Label billPayCode;
    public Label billInvoiceNo;
    public Label billLblDate;
    public Label billLblTime;
    public TableView<BillTm> billTable;
    public TableColumn billclmBarcode;
    public TableColumn billclmDescription;
    public TableColumn billclmUnitPrice;
    public TableColumn billclmQty;
    public TableColumn billclmAmount;
    public Label billLblTot;
    public Label billLblBalance;
    public Label billLblCash;
    public Label billLblNetTot;
    int tableSelectedRowRemove=-1;
    String payc=" ";

    public void initialize(){
        loadDateAndTime();
        loadNic();
        loadInvoiceNo();

        cmbEnployeeId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            nic=(String ) newValue;
            //setUserName();
        });
        txtEnterBarcode.setOnKeyReleased(event -> {
            Pattern p = Pattern.compile("^B-[0-1][0-2][0-9]");//. represents single character
            Matcher m = p.matcher(txtEnterBarcode.getText());
            boolean b = m.matches();
            if (m.matches()){
                txtEnterBarcode.setStyle("-fx-border-color: white");
            }else{
                txtEnterBarcode.setStyle("-fx-border-color: Red");
            }
        });
        tblOrder.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            tableSelectedRowRemove=(int)newValue;

        });
    }
    ObservableList<OrderTm> obList= FXCollections.observableArrayList();
    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        if (checkQuantity()) {
            for (OrderTm temp : obList) {
                if (temp.getBarcode().equals(txtEnterBarcode.getText())) {
                    int qty = temp.getQty()+Integer.parseInt(txtQtyForCustomer.getText());
                    double price=qty*Double.parseDouble(lblUnitPrice.getText());
                    p=p+(Double.parseDouble(lblUnitPrice.getText())*Integer.parseInt(txtQtyForCustomer.getText()));
                    lblTotlePrice.setText(String.valueOf(p));
                    temp.setQty(qty);
                    temp.setPrice(price);
                    tblOrder.refresh();
                    txtEnterBarcode.setStyle("-fx-border-color: white");
                    txtEnterBarcode.clear();
                    txtQtyForCustomer.clear();
                    //lblDescription.setTextFill(RED);
                    lblDescription.setText("Description");
                    lblUnitPrice.setText("Unit price");
                    return;
                }
            }
            double price=Integer.parseInt(txtQtyForCustomer.getText())*Double.parseDouble(lblUnitPrice.getText());
            obList.add(new OrderTm(lblInvoiceNo.getText(), txtEnterBarcode.getText(), lblDescription.getText(),
                    Integer.parseInt(txtQtyForCustomer.getText()),price));
            p=p+price;
            lblTotlePrice.setText(String.valueOf(p));
            tblOrder.setItems(obList);
            clmInvoiceNo.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
            clmBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
            cmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            clmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
            clmPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

            txtEnterBarcode.setStyle("-fx-border-color: white");
            txtEnterBarcode.clear();
            txtQtyForCustomer.clear();
            lblDescription.setText("Description");
            lblUnitPrice.setText("Unit price");
        } else {
            new Alert(Alert.AlertType.WARNING, "Your Quantity is Over").show();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        if (tableSelectedRowRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            OrderTm temp = obList.get(tableSelectedRowRemove);
            p=p-temp.getPrice();
            lblTotlePrice.setText(String.valueOf(p));
            obList.remove(tableSelectedRowRemove);
            tblOrder.refresh();
        }
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Double balance=Double.parseDouble(txtcashAmount.getText())-Double.parseDouble(lblTotlePrice.getText());
        ArrayList<OrderDetail> items=new ArrayList<>();
        for (OrderTm temp:obList ) {
            items.add(new OrderDetail(temp.getInvoiceNo(),temp.getBarcode(),temp.getPrice()/temp.getQty(),temp.getQty()));
        }
        Order order=new Order(lblInvoiceNo.getText(),nic,lblDate.getText(), lblTime.getText(),items);
        payc=payCode();
        Payment payment=new Payment(payCode(),lblInvoiceNo.getText(),Double.parseDouble(lblTotlePrice.getText()),
                Double.parseDouble(txtcashAmount.getText()),balance,0);
        if (new OrderController().addOrder(order,payment)){
            if (new OrderController().updateQty(items)){
                //loadBill();
                clear();
                new Alert(Alert.AlertType.CONFIRMATION,"Your Order Was Success!").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Sorry Can not do this 1!").show();
            }
        }else {
            new Alert(Alert.AlertType.WARNING,"Sorry Can not do this!").show();
        }
    }

    public void pressEnterAction(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER){
            try {
                ResultSet set = new OrderController().getItem(txtEnterBarcode.getText());
                if (set.next()){
                    lblDescription.setText(set.getString(2));
                    lblUnitPrice.setText(set.getString(3));
                    qty=Integer.parseInt(set.getString(4));
                    //System.out.println("item qty "+qty);
                }else{
                    new Alert(Alert.AlertType.WARNING,"Please Enter Right Barcode").show();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    private void loadDateAndTime() {
        //load Data
        Date date=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        //Load Time
        Timeline time=new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime= LocalTime.now();
            lblTime.setText(currentTime.getHour()+" : "+currentTime.getMinute());

        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
    private void loadNic(){
        try {
            ResultSet set = new OrderController().getEmployeeNic();
            ObservableList<String> list= FXCollections.observableArrayList();
            while (set.next()){
                list.add(set.getString(1));
            }
            cmbEnployeeId.setItems(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void loadInvoiceNo(){
        try {
            ResultSet set = new OrderController().getInvoice();
            if (set.next()){
                int tempId = Integer.
                        parseInt(set.getString(1).split("-")[1]);
                tempId=tempId+1;
                if (tempId<=9){
                    lblInvoiceNo.setText("IN-00"+tempId);
                }else if(tempId<=99){
                    lblInvoiceNo.setText("IN-0"+tempId);
                }else{
                    lblInvoiceNo.setText("IN-"+tempId);
                }
            }else{
                lblInvoiceNo.setText("IN-001");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private boolean checkQuantity(){
        for (OrderTm temp:obList) {
            if (temp.getBarcode().equals(txtEnterBarcode.getText())){
                int i=Integer.parseInt(txtQtyForCustomer.getText())+temp.getQty();
                if (qty>=i){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return qty >= Integer.parseInt(txtQtyForCustomer.getText());

    }

    private String payCode() throws SQLException, ClassNotFoundException {
            ResultSet set = new OrderController().getInvoice();
            if (set.next()){
                int tempId = Integer.
                        parseInt(set.getString(1).split("-")[1]);
                tempId=tempId+1;
                if (tempId<=9){
                    return "PAY-00"+tempId;
                }else if(tempId<=99){
                    return "PAY-0"+tempId;
                }else{
                    return "PAY-"+tempId;
                }
            }else{
                return "PAY-001";
            }
    }
    private void clear() throws SQLException, ClassNotFoundException {
        loadBill();
        loadInvoiceNo();
        txtcashAmount.clear();
        lblDescription.setText("Description");
        lblUnitPrice.setText("Unit price");
        lblTotlePrice.setText("0.00");
        obList=FXCollections.observableArrayList();
        tblOrder.setItems(obList);
        tblOrder.refresh();
        p=0;
    }

    private void loadBill() throws SQLException, ClassNotFoundException {
        ObservableList<BillTm>billList= FXCollections.observableArrayList();
        for (OrderTm temp:obList ) {
            billList.add(new BillTm(temp.getBarcode(),temp.getDescription(),temp.getPrice()/temp.getQty(),temp.getQty(),temp.getPrice()));
        }
        billLblTime.setText(lblTime.getText());
        billLblDate.setText(lblDate.getText());
        billPayCode.setText(payc);
        billInvoiceNo.setText(lblInvoiceNo.getText());
        billTable.setItems(billList);
        billclmBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        billclmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        billclmUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        billclmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        billclmAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        billLblTot.setText(lblTotlePrice.getText());
        billLblNetTot.setText(lblTotlePrice.getText());
        billLblCash.setText(txtcashAmount.getText());
        billLblBalance.setText(String.valueOf(Double.parseDouble(billLblCash.getText())-Double.parseDouble(billLblNetTot.getText())));
    }

}
