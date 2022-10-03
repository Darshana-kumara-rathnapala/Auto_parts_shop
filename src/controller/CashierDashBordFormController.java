package controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class CashierDashBordFormController {
    public AnchorPane root;
    public AnchorPane pane2;
    public VBox vBox;
    public FontAwesomeIconView menuID;
    public AnchorPane changeRoot;
    public static String name=new String();
    public Label lblDashBordUserName;
    public Label lblrating;

    public void initialize() throws IOException {
        //setName("name");
  // -------------start menu animation-------------------------------------//
        vBox.setVisible(false);
        FadeTransition fade1=new FadeTransition(new Duration(350),vBox);
        fade1.setFromValue(0.15);
        fade1.setToValue(5);

        TranslateTransition translate1= new TranslateTransition(new Duration(350),vBox);
        translate1.setToX(0);

        FadeTransition fade=new FadeTransition(new Duration(350),vBox);
        fade.setFromValue(0.15);
        fade.setToValue(5);

        TranslateTransition translate= new TranslateTransition(new Duration(350),vBox);
        //translate.setByX(-200);

        menuID.setOnMouseClicked(event -> {
            if (vBox.getTranslateX()!=0){
                fade1.play();
                translate1.play();
                fade1.setOnFinished(event1 -> {
                    vBox.setVisible(true);
                });
            }else{
                translate.setToX(-(vBox.getWidth()));
                fade.play();
                translate.play();
                fade.setOnFinished(event2 -> {
                    vBox.setVisible(true);
                });
            }
        });
        // -------------stop menu animation-------------------------------------//
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"))) ;
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("../view/PlaceOrderForm.fxml"))) ;
    }

    public void bntModifyOrderOnAction(ActionEvent actionEvent) throws IOException {
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ModifyOrderForm.fxml"))) ;
    }

    public void bntRemoveOrderOnAction(ActionEvent actionEvent) throws IOException {
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("../view/RemoveOrderForm.fxml"))) ;
    }

    public void bntModifyTrancactionOnAction(ActionEvent actionEvent) throws IOException {
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ModifyTransactionForm.fxml"))) ;
    }

    public void bntBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("../view/LoggingForm.fxml"));
        Scene scene=new Scene(parent);
        Stage primaryStage=(Stage ) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
