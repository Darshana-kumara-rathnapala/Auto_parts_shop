package controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DashBordFormController {
    public AnchorPane root;
    public AnchorPane pane1;
    public AnchorPane pane2;
    public AnchorPane pane3;
    public  Label lblTime;
    public Label lblDate;
    public static Label lblTime1;
    public static Label lblDate1;
    public VBox vbox;
    public FontAwesomeIconView menuId;
    public AnchorPane changeRoot;
    public LineChart incomeChart;
    public CategoryAxis x;
    public NumberAxis y;
    public BarChart profitChart;
    public CategoryAxis b;
    public NumberAxis a;

    public void initialize() {
        lblDate1=lblDate;
        lblTime1=lblTime;
//------------Set data to chart --------------------------------------------
        XYChart.Series set1=new XYChart.Series<>();
        set1.setName("Year");
        set1.getData().add(new XYChart.Data<>("2018",105));
        set1.getData().add(new XYChart.Data<>("2019",50));
        set1.getData().add(new XYChart.Data<>("2020",110));
        set1.getData().add(new XYChart.Data<>("2021",80));
        incomeChart.getData().add(set1);

//------------Set data to chart --------------------------------------------
        XYChart.Series set2=new XYChart.Series<>();
        set2.setName("Day");
        set2.getData().add(new XYChart.Data<>("2018",100.50));
        set2.getData().add(new XYChart.Data<>("2019",50));
        set2.getData().add(new XYChart.Data<>("2020",110));
        set2.getData().add(new XYChart.Data<>("2021",80));
        profitChart.getData().add(set2);
//------------Set menu animation --------------------------------------------

        vbox.setVisible(false);
        FadeTransition fade1=new FadeTransition(new Duration(350),vbox);
        fade1.setFromValue(0.15);
        fade1.setToValue(5);

        TranslateTransition translate1= new TranslateTransition(new Duration(350),vbox);
        translate1.setToX(0);

        FadeTransition fade=new FadeTransition(new Duration(350),vbox);
        fade.setFromValue(0.15);
        fade.setToValue(5);

        TranslateTransition translate= new TranslateTransition(new Duration(350),vbox);
        //translate.setByX(-200);

        menuId.setOnMouseClicked(event -> {
            if (vbox.getTranslateX()!=0){
                fade1.play();
                translate1.play();
                fade1.setOnFinished(event1 -> {
                    vbox.setVisible(true);
                });
            }else{
                translate.setToX(-(vbox.getWidth()));
                fade.play();
                translate.play();
                fade.setOnFinished(event2 -> {
                    vbox.setVisible(true);
                });
            }
        });

        loadDateAndTime();
    }
    private void loadDateAndTime() {
        //load Data
        Date date=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        //Load Time
        Timeline time=new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime= LocalTime.now();
            lblTime.setText(currentTime.getHour()+" : "+currentTime.getMinute()+" : "+currentTime.getSecond());

        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }


    public void btnDashbord(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("../view/DashBordForm.fxml"));
        Scene scene=new Scene(parent);
        Stage primaryStage=(Stage ) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void btnEmployee(ActionEvent actionEvent) throws IOException {
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("../view/EmployeeAdd.fxml"))) ;
    }

    public void btnSalary(ActionEvent actionEvent) throws IOException {
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("../view/EmployeeSalary.fxml"))) ;
    }

    public void btnItem(ActionEvent actionEvent) throws IOException {
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("../view/ItemForm.fxml"))) ;
    }

    public void btnTransaction(ActionEvent actionEvent) throws IOException {
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("../view/TranceActionForm.fxml"))) ;
    }

    public void btnOrderReport(ActionEvent actionEvent) throws IOException {
        changeRoot.getChildren().clear();
        changeRoot.getChildren().add(FXMLLoader.load(getClass().getResource("../view/OrderReportForm.fxml"))) ;
    }

    public void btnBackOoAction(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("../view/LoggingForm.fxml"));
        Scene scene=new Scene(parent);
        Stage primaryStage=(Stage ) this.root.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
