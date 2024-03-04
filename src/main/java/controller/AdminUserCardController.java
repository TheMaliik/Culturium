package controller;

import Entity.User;
import Services.ServiceUser;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminUserCardController implements Initializable {

    private int idCu;

    private User user;

    @FXML
    private TextField emailTextField;


    @FXML
    private TextField ApprF;
    @FXML
    private TextField BlocF;

    @FXML
    private TextField fullNameTextField;

    @FXML
    private TextField phoneNumberTextField;


    @FXML
    private ImageView img;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.user = new User();

        Circle clip = new Circle();
        clip.setCenterX(img.getFitWidth() / 2);
        clip.setCenterY(img.getFitHeight() / 2);
        clip.setRadius(Math.min(img.getFitWidth(), img.getFitHeight()) / 2);


        img.setClip(clip);

        LoadData();
    }

    public void LoadData() {

        ServiceUser us = new ServiceUser();
        try {
            User u = us.findById(this.idCu);
            if (u != null) {
                if (u.getFullName() != null) {
                    fullNameTextField.setText(u.getFullName());
                }
                if (u.getEmail() != null) {
                    emailTextField.setText(u.getEmail());
                }
                if (u.getTel() != null) {
                    phoneNumberTextField.setText(u.getTel());
                }


                ApprF.setText(String.valueOf(u.isIs_approved()));
                BlocF.setText(String.valueOf(u.isIs_blocked()));

                if (u.getImage() != null) {
                    // Create an Image object from the file path
                    Image image = new Image(new File(u.getImage()).toURI().toString());
                    // Set the image in the ImageView
                    img.setImage(image);
                }



            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int initData(int id) {
        this.idCu = id;
        this.LoadData();
        System.err.println("ena aaaaaa" + this.idCu);
        return this.idCu;

    }


    @FXML
    private void unban(ActionEvent event) throws SQLException {
        ServiceUser us = new ServiceUser();
        User userToBan = us.findById(this.idCu);
        us.unban(userToBan);
    }



    @FXML
    private void goBackHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListUsers.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }








}