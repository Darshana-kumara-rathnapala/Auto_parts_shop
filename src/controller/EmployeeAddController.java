package controller;

import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import modle.Employee;
import modle.Epf_Etf;
import modle.Payroll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeAddController {
    public AnchorPane rootEmployee;
    public JFXTextField txtEmployeeNic;
    public JFXTextField txtEmployeeName;
    public JFXTextField txtEmployeeAddress;
    public JFXTextField txtEmployeeSalaryAmount;
    public JFXTextField txtEmployeeContact;
    public TextField txtSearchNic;
    public TextField txtSearchName;
    public TextField txtSearchAddress;
    public TextField txtSearchContact;
    public TextField txtSearchSalaryAmount;

    public void initialize(){
        txtEmployeeNic.setOnKeyReleased(event -> {
            Pattern p = Pattern.compile("[0-9]{9}(V)$");//. represents single character
            Matcher m = p.matcher(txtEmployeeNic.getText());
            boolean b = m.matches();
            if (m.matches()){
                txtEmployeeNic.setStyle("-fx-border-color: white");
            }else{
                txtEmployeeNic.setStyle("-fx-border-color: Red");
            }
        });

    }
    public void btnRegisterEmployee(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        double epf=2*(Double.parseDouble(txtEmployeeSalaryAmount.getText())/100);
        double netSalary=Double.parseDouble(txtEmployeeSalaryAmount.getText())-epf;
        //System.out.println(netSalary+" "+epf);
//-------------------set data to model (Start) ------------------------------------------------
        Employee employee=new Employee(txtEmployeeNic.getText(),txtEmployeeName.getText(),
                txtEmployeeAddress.getText(),Integer.parseInt(txtEmployeeContact.getText()),Double.parseDouble(txtEmployeeSalaryAmount.getText()));
       /* Payroll payroll=new Payroll(txtPaySheetNO.getText(),"E-0"+(epfNo++),Double.parseDouble(txtEmployeeSalaryAmount.getText()),
                '2',netSalary);
        Epf_Etf epf_etf=new Epf_Etf("E-0"+(epfNo++),epf,50.00);*/
//-------------------set data to model (End)------------------------------------------------
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("INSERT INTO employee VALUES(?,?,?,?,?)");
        stm.setObject(1,employee.getId());
        stm.setObject(2,employee.getName());
        stm.setObject(3,employee.getContact());
        stm.setObject(4,employee.getAddress());
        stm.setObject(5,employee.getSalary());
        if (stm.executeUpdate()>0){
            txtEmployeeNic.clear();
            txtEmployeeName.clear();
            txtEmployeeAddress.clear();
            txtEmployeeContact.clear();
            txtEmployeeSalaryAmount.clear();
            new Alert(Alert.AlertType.CONFIRMATION,"Employee Registation was Successfully",ButtonType.OK).show();
        }
    }

    public void btnSearchEmployee(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM employee WHERE Id=?");
        stm.setObject(1,txtSearchNic.getText());
        ResultSet set = stm.executeQuery();

        if (set.next()){
            txtSearchName.setText(set.getString(2));
            txtSearchAddress.setText(set.getString(4));
            txtSearchContact.setText(String.valueOf(set.getString(3)));
            txtSearchSalaryAmount.setText(String.valueOf(set.getString(5)));
        }else{
            txtSearchName.clear();
            txtSearchAddress.clear();
            txtSearchContact.clear();
            txtSearchSalaryAmount.clear();
            new Alert(Alert.AlertType.WARNING,"No Data", ButtonType.CLOSE).show();
        }
    }

    public void btnUpdateEmployee(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        /*Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm1 = con.prepareStatement("SELECT * FROM employee WHERE Id=?");
        stm1.setObject(1,txtSearchNic.getText());
        ResultSet set1 = stm1.executeQuery();
        if (set1.next()){
            System.out.println(set1.getString(1));
            System.out.println(set1.getString(2));
            System.out.println(set1.getString(3));
            System.out.println(set1.getString(4));
            System.out.println(set1.getString(5));
            if (txtSearchName.equals(set1.getString(2))){
                System.out.println("ss");

                PreparedStatement stm = con.prepareStatement("UPDATE employee SET Name=?,Contact=?,Address=?,Salary=? WHERE Id= ?");
                stm.setObject(1,txtSearchName.getText());
                stm.setObject(2,Integer.parseInt(txtSearchContact.getText()));
                stm.setObject(3,txtSearchAddress.getText());
                stm.setObject(4,Double.parseDouble(txtSearchSalaryAmount.getText()));
                stm.setObject(5,txtSearchNic.getText());
                if (stm.executeUpdate()>0){
                    txtSearchName.clear();
                    txtSearchAddress.clear();
                    txtSearchContact.clear();
                    txtSearchSalaryAmount.clear();
                    new Alert(Alert.AlertType.CONFIRMATION,"Update Successfully",ButtonType.OK).show();
                }
            }
        }else{
            new Alert(Alert.AlertType.WARNING,"No New Values", ButtonType.CLOSE).show();
        }*/

        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("UPDATE employee SET Name=?,Contact=?,Address=?,Salary=? WHERE Id= ?");
        stm.setObject(1,txtSearchName.getText());
        stm.setObject(2,Integer.parseInt(txtSearchContact.getText()));
        stm.setObject(3,txtSearchAddress.getText());
        stm.setObject(4,Double.parseDouble(txtSearchSalaryAmount.getText()));
        stm.setObject(5,txtSearchNic.getText());
        if (stm.executeUpdate()>0){
            txtSearchName.clear();
            txtSearchAddress.clear();
            txtSearchContact.clear();
            txtSearchSalaryAmount.clear();
            new Alert(Alert.AlertType.CONFIRMATION,"Update Successfully",ButtonType.OK).show();
        }
    }

    public void bntDeleteEmployee(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement stm = con.prepareStatement("DELETE FROM employee WHERE Id= ?");
        stm.setObject(1,txtSearchNic.getText());
        if (stm.executeUpdate()>0){
            txtSearchName.clear();
            txtSearchAddress.clear();
            txtSearchContact.clear();
            txtSearchSalaryAmount.clear();
            new Alert(Alert.AlertType.CONFIRMATION,"Delete Successfully",ButtonType.OK).show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"This Employee alredy exist ",ButtonType.OK).show();
        }

    }
}
