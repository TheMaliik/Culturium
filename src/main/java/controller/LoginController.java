package controller;

import Entity.User;
import Services.ServiceUser;
import Utils.DBConnection;
import Utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {

    @FXML
    private Button login_btn;
    @FXML
    private TextField emailF;
    @FXML
    private PasswordField mdp;

    public static User UserConnected;
    @FXML
    private TextField visiblePassword;

    @FXML
    private ImageView eyeIcon;
    private boolean passwordVisible = false;
    ServiceUser us= new ServiceUser();

    Connection connection;

/*
    @FXML
    public void Login(ActionEvent actionEvent) throws IOException {
        if (email.getText().equals("melek.guesmi@esprit.tn") && mdp.getText().equals("TheMaliik")) {
            // Admin login
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Travel Me :: Success Message");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenue Admin");
            alert.showAndWait();

            // Close the current stage (login stage)
            Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            loginStage.close();

            // Load AdminDashboard.fxml on a new stage
            Parent root = FXMLLoader.load(getClass().getResource("/AdminDashboard.fxml"));
            Scene scene = new Scene(root);
            Stage adminStage = new Stage();
            adminStage.setScene(scene);
            adminStage.show();
        } else {
            String loginResult = ServiceUser.login(new User(email.getText(), mdp.getText()));
            connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement smt = connection.prepareStatement(loginResult);
                smt.setString(1, email.getText());
                smt.setString(2, mdp.getText());
                ResultSet rs = smt.executeQuery();
                if (rs.next()) {
                    // User found, set current user and open UserCard.fxml
                    User currentUser = new User(
                            rs.getInt("id"),
                            rs.getString("fullName"),
                            rs.getString("email"),
                            rs.getString("tel"),
                            rs.getString("image")
                    );
                    User.setCurrent_User(currentUser);
                    SessionManager.getInstace(
                            rs.getInt("id"),
                            rs.getString("fullName"),
                            rs.getString("email"),
                            rs.getString("tel"),
                            rs.getString("image")
                    );
                    System.out.println(User.Current_User.getEmail());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Travel Me :: Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Vous êtes connecté");
                    alert.showAndWait();

                    // Close the current stage (login stage)
                    Stage loginStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    loginStage.close();

                    // Load UserCard.fxml on a new stage
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserCard.fxml"));
                    Parent root = loader.load();
                    UserCardController controller = loader.getController();
                    controller.initData(currentUser.getId()); // Pass the currentUser object to the controller
                    Scene scene = new Scene(root);
                    Stage userStage = new Stage();
                    userStage.setScene(scene);
                    userStage.show();

                } else {
                    // No user found with provided credentials
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Travel Me :: Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Email/Password!");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                // Handle any SQL exceptions here
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                // Handle any IO exceptions related to FXML loading
            }
        }
    }

*/


    @FXML
    private void Login(ActionEvent event) {
        String email = emailF.getText();
        String password = mdp.getText();

        // Vérifiez d'abord si l'utilisateur est un administrateur
        if (email.equals("melek.guesmi@esprit.tn") && password.equals("TheMaliik")) {
            // Si l'utilisateur est un administrateur, affichez directement l'interface AdminDashboard
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage adminStage = new Stage();
                adminStage.setScene(scene);
                adminStage.show();

                // Fermez l'interface de connexion
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

                // Afficher une alerte de connexion réussie
                showAlert(Alert.AlertType.INFORMATION, "Connexion réussie", "Vous êtes connecté en tant qu'administrateur.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Si l'utilisateur n'est pas un administrateur, continuez avec le processus de connexion normal
            // Créez un objet User avec les informations de connexion fournies par l'utilisateur
            User user = new User(email, password);

            // Utilisez le ServiceUser pour vérifier les informations de connexion
            String loginMessage = ServiceUser.login(user);

            // Vérifiez le message de retour de la fonction login
            if (loginMessage.isEmpty()) {
                // Si le message est vide, cela signifie que l'utilisateur est connecté avec succès
                // Maintenant, chargez l'interface utilisateur appropriée
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientDashboard.fxml"));
                    Parent root = loader.load();


                    Scene scene = new Scene(root);
                    Stage userStage = new Stage();
                    userStage.setScene(scene);
                    userStage.show();

                    // Fermez l'interface de connexion
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();

                    // Afficher une alerte de connexion réussie
                    showAlert(Alert.AlertType.INFORMATION, "Connexion réussie", "Vous êtes connecté.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Afficher le message d'erreur de connexion
                showAlert(Alert.AlertType.ERROR, "Erreur de connexion", loginMessage);
            }
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void SignUp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUpController.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("SignUp");
        stage.setScene(scene);
        stage.show();
    }

@FXML
private void ForgotPassword(ActionEvent event) {

    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ResetPassword.fxml"));
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
