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
import javafx.scene.control.TableColumn;
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


public class UserCardController implements Initializable {

    private int idCu;

    private User user;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField ApprF;
    @FXML
    private TextField BlocF;

    @FXML
    private TextField phoneNumberTextField;
    /*
    @FXML
    private TextField mdpField;
*/
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

               /* if (u.getMdp() != null) {
                    mdpField.setText(u.getMdp());
                }
*/
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
    private void updateData(ActionEvent event) {
        // Check if any field is empty
        if (emailTextField.getText().isEmpty() ||
                fullNameTextField.getText().isEmpty() ||
                phoneNumberTextField.getText().isEmpty()
               /* mdpField.getText().isEmpty()*/) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields!");
            alert.show();
            return;
        }

        try {
            // Fetch user data from the database
            ServiceUser us = new ServiceUser();
            User userToUpdate = us.findById(idCu);

            // Update user data
            userToUpdate.setEmail(emailTextField.getText());
            userToUpdate.setFullName(fullNameTextField.getText());
            userToUpdate.setTel(phoneNumberTextField.getText());
           /*userToUpdate.setMdp(mdpField.getText());*/
            userToUpdate.setIs_approved(Boolean.parseBoolean(ApprF.getText()));
            userToUpdate.setIs_blocked(Boolean.parseBoolean(BlocF.getText()));

            // Call the modifier method to update the user
            us.modifier(userToUpdate);

            // Show success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText(null);
            successAlert.setContentText("Modification réussie !!");
            successAlert.show();


        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle exception appropriately
            // Show error message
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("An error occurred while updating user data!");
            errorAlert.show();
        }
    }
@FXML
private void ban(ActionEvent event) throws SQLException, IOException {
    ServiceUser us = new ServiceUser();
    User userToBan = us.findById(this.idCu);
    us.ban(userToBan);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage adminStage = new Stage();
    adminStage.setScene(scene);
    adminStage.show();

    // Fermez l'interface de connexion
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.close();
    }
    
    
@FXML
private void unban(ActionEvent event) throws SQLException {
    ServiceUser us = new ServiceUser();
    User userToBan = us.findById(this.idCu);
    us.unban(userToBan);
}




    public void goBackHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientDashboard.fxml"));
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