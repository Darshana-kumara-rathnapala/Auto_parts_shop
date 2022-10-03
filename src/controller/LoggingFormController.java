package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.util.Logging;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import modle.Loging;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoggingFormController {
    public AnchorPane root;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public CheckBox adminAsID;
    String identifier="U";
    String name=null;
    String password=null;

    // public static HashMap<JFXPasswordField,String> h;
    //public static LinkedHashMap<JFXPasswordField,String> hs;
   public void initialize() throws IOException {
    /*txtPassword.setOnKeyReleased(event -> {
        Pattern p = Pattern.compile("^(q)[0-1][0-2][0-3]");//. represents single character
        Matcher m = p.matcher(txtPassword.getText());
        boolean b = m.matches();
        if (m.matches()){
            txtPassword.setStyle("-fx-border-color: Green");
        }else{
            txtPassword.setStyle("-fx-border-color: Red");
        }
    });*/
   }
    public void btnSignIn() throws IOException {
        try {
            Connection con = DbConnection.getInstance().getConnection();
            Statement stm = con.createStatement();
            //String quary="SElECT*FROM loging";
            ResultSet set = stm.executeQuery("SElECT*FROM loging WHERE Identifier ='"+identifier+"'");
            while (set.next()){
                name=set.getString(2);
                password=set.getString(3);
            }

            Loging log=new Loging(identifier,txtUserName.getText(),txtPassword.getText());
            if (log.getIdentifier().equals("A") && log.getName().equals(name) && log.getPassword().equals(password)) {
                Parent parent= FXMLLoader.load(getClass().getResource("../view/DashBordForm.fxml"));
                Stage stage=(Stage ) this.root.getScene().getWindow();
                Scene scene=new Scene(parent);
                stage.setScene(scene);
                stage.show();
            }else if (log.getIdentifier().equals("U") && log.getName().equals(name) && log.getPassword().equals(password)){
                Stage stage=(Stage) this.root.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CashierDashBordForm.fxml"))));
                stage.show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Your User name or Password is incorrect ").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       /* Parent parent= FXMLLoader.load(getClass().getResource("../view/DashBordForm.fxml"));
        Stage stage=(Stage ) this.root.getScene().getWindow();
        Scene scene=new Scene(parent);
        stage.setScene(scene);
        stage.show();*/
    }

    public void adminAsMouseClick(MouseEvent mouseEvent) {
      if (identifier.equals("U")){
          identifier="A";
      }else{
          identifier="U";
      }
   }
}
