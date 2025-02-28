package dz.hacencode.ecommerce;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dz.hacencode.ecommerce.Model.Category;
import dz.hacencode.ecommerce.Model.connectDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloController {
    @FXML
    private StackPane stackPane;
    @FXML
    private Label info;
    @FXML
    private JFXButton login;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    protected void onHelloButtonClick() {
        String user = userName.getText();
        String pwd = password.getText();

        if(user != null && pwd != null){
            if(user.equals("admin") && pwd.equals("admin")){
                close();
                load();
                Connection cn = connectDB.getconnection();
                Category category = new Category();
                Set<Category> categories =  category.getAllCategory();

                categories.forEach(category1 -> System.out.println(category1.getCategoryName()));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("username or password not true");
                // Setting the Button's action
                alert.showAndWait();
            }
        }


    }
    @FXML
    protected void onFocus(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER && userName.isFocused()){
            password.requestFocus();
        }else
        if(event.getCode() == KeyCode.ENTER && password.isFocused()){
            login.requestFocus();
        }
    }
    @FXML
    protected void onCloseClick() {
        System.exit(0);
    }
    private void close(){
        ((Stage)stackPane.getScene().getWindow()).close();
    }
    void load(){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
//        Image image = new Image("/rappel_courrier/calendar.png");
//        stage.getIcons().add(image);
            stage.setResizable(false);
            stage.setTitle("dashboard");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}