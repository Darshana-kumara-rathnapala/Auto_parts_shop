package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modle.Epf_Etf;
import modle.Payroll;
import tm.SalaryTm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmployeeSalaryController {
    public AnchorPane rootSalary;
    public JFXComboBox<String> cmbSelectEmployeeNic;
    public TextField txtSalaryAmount;
    public TextField txtEpfNo;
    public JFXCheckBox markPaySalary;
    public TableView<SalaryTm> tblSalary;
    public TableColumn clmNic;
    public TableColumn clmName;
    public TableColumn clmAmount;
    public TableColumn clmMonth;
    public TableColumn clmEpNo;
    public TableColumn clmPresatageOfEpf;
    public TableColumn clmNetSalary;
    public Label lblName;
    public JFXComboBox<String> cmbSelectMonth;
    public String pay="notPay";
    public JFXButton btnSubmitId;
    public String epfNo;
    public String nic;
    public String month;
    public DatePicker datePickID;
    int tableSelectedRowRemove=-1;
    String date;

    public void initialize() throws SQLException, ClassNotFoundException {
        btnSubmitId.setDisable(true);
        loadMonth();
        loadNic();

        cmbSelectEmployeeNic.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Connection con = DbConnection.getInstance().getConnection();
                PreparedStatement stm = con.prepareStatement("SELECT *FROM employee WHERE Id=?");
                nic=newValue;
                stm.setObject(1,newValue);
                ResultSet set = stm.executeQuery();
                if (set.next()){
                    lblName.setText(set.getString(2));
                    txtSalaryAmount.setText(set.getString(5));
                }
                PreparedStatement stm1 = con.prepareStatement("SELECT Epf_Etf_number FROM epf_etf ORDER BY Epf_Etf_number DESC LIMIT 1");
                ResultSet rset = stm1.executeQuery();
                if (rset.next()){
                    int tempId = Integer.
                            parseInt(rset.getString(1).split("-")[1]);
                    tempId=tempId+1;
                    if (tempId<9){
                        txtEpfNo.setText("E-00"+tempId);
                    }else if(tempId<99){
                        txtEpfNo.setText("E-0"+tempId);
                    }else{
                        txtEpfNo.setText("E-"+tempId);
                    }
               }else {
                   txtEpfNo.setText("E-001");
               }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        cmbSelectMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            month=newValue;
        });
        tblSalary.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            tableSelectedRowRemove=(int)newValue;

        });
        loadtable();
    }
 //-------------------------------End Initialize Method--------------------------------------
    public void btnSubmit(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        Connection con=null;
        double salary=Double.parseDouble(txtSalaryAmount.getText());
        double epf=2*(salary/100);
        double netSalary=salary-epf;

        Payroll payroll=new Payroll("p-"+txtEpfNo.getText().split("-")[1],nic,salary,
                "2%",netSalary,month);
        try {
            con = DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO pay_roll VALUES(?,?,?,?,?,?,?)");
            stm.setObject(1,payroll.getPaymentNo());
            stm.setObject(2,payroll.getEmployeeNic());
            stm.setObject(3,payroll.getSalary());
            stm.setObject(4,payroll.getPresantage());
            stm.setObject(5,payroll.getNetSalary());
            stm.setObject(6,payroll.getMonth());
            stm.setObject(7,date);
            if (stm.executeUpdate()>0){
                if (saveEpfEtf(new Epf_Etf(txtEpfNo.getText(),epf,100,month))){
                    con.commit();

                    new Alert(Alert.AlertType.CONFIRMATION,"Salary Payment was successfully").show();
                }else {
                    con.rollback();
                }
                loadtable();
            }else {
                con.rollback();
                new Alert(Alert.AlertType.WARNING,"something went wrong", ButtonType.CANCEL).show();
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
        rootSalary.getChildren().clear();
        rootSalary.getChildren().add(FXMLLoader.load(getClass().getResource("../view/EmployeeSalary.fxml"))) ;

    }
 //-------------------------------Start Save-Epf_Etf Method--------------------------------------

    private boolean saveEpfEtf(Epf_Etf epf_etf) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("INSERT INTO epf_etf VALUES(?,?,?,?,?)");
        stm.setObject(1,epf_etf.getEpfNo());
        stm.setObject(2,"p-"+txtEpfNo.getText().split("-")[1]);
        stm.setObject(3,epf_etf.getEpf());
        stm.setObject(4,epf_etf.getEtf());
        stm.setObject(5,epf_etf.getMonth());

        if (stm.executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
    //-------------------------------Start Tables load Method--------------------------------------
    ObservableList<SalaryTm> obList=null;
    private  void loadtable() throws SQLException, ClassNotFoundException {
        obList=FXCollections.observableArrayList();
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("select e.Id,e.Name,p.Salary,p.Month,ep.Epf_Etf_number,p.Presentage_Epf,p.NetSalary\n" +
                "from employee e INNER JOIN pay_roll p  \n" +
                "on e.Id=p.Employee_nic\n" +
                "INNER JOIN epf_etf ep on p.Payment_no=ep.Payment_no");
        ResultSet set = stm.executeQuery();

        while (set.next()){
            obList.add(new SalaryTm(set.getString(1),set.getString(2),Double.parseDouble(set.getString(3)),
                    set.getString(4),set.getString(5),set.getString(6),Double.parseDouble(set.getString(7))));
        }
        tblSalary.setItems(obList);
    }
 //-------------------------------Start clear Method--------------------------------------

    public void bntClear(ActionEvent actionEvent) {
        if (tableSelectedRowRemove==-1){
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        }else{
            obList.remove(tableSelectedRowRemove);
            //calculateCost();
            tblSalary.refresh();
        }
    }

 //-------------------------------Start NIC load Method--------------------------------------

    private void loadNic() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT Id FROM employee");
        ResultSet set = stm.executeQuery();
        ObservableList<String> list=FXCollections.observableArrayList();
        while (set.next()){
            list.add(set.getString(1));
        }
        cmbSelectEmployeeNic.setItems(list);
        clmNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        clmMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        clmEpNo.setCellValueFactory(new PropertyValueFactory<>("epfNo"));
        clmPresatageOfEpf.setCellValueFactory(new PropertyValueFactory<>("prasantageOfEpf"));
        clmNetSalary.setCellValueFactory(new PropertyValueFactory<>("netSalary"));
    }

//-------------------------------End Load NIC number Method--------------------------------------
    private void loadMonth() {
        ObservableList<String> list=FXCollections.observableArrayList("January","February","March","April","May","June","July","Augest","September","Octomber","November","December");
        cmbSelectMonth.setItems(list);
    }
 //-------------------------------Start Pay Ticked Method--------------------------------------
    public void selectedAsPay(MouseEvent mouseEvent) {
        if (pay.equals("notPay")){
            pay="pay";
            btnSubmitId.setDisable(false);
        }else{
            pay="notPay";
            btnSubmitId.setDisable(true);
        }
    }

    public void pickDateAction(ActionEvent actionEvent) {
        //datePickID.getData().clear();
        LocalDate date1=datePickID.getValue();
        date=String.valueOf(date1);
    }
}
