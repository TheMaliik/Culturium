package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Entity.SmsSender;
import Entity.User;
import Securite.BCrypt;
import Services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn;

    @FXML
    private TextField FullNameField;
    @FXML
    private TextField EmailField;

    @FXML
    private TextField TelField;
    @FXML
    private TextField MdpField;

    @FXML
    private TextField ConfMdp;

    private String imageData;



    @FXML
    void addUser(ActionEvent event) {
    }


    @FXML
    void initialize() {
    }

    ServiceUser ServUser= new ServiceUser();





    @FXML
    private void add() throws SQLException, IOException {
        String fullName = FullNameField.getText();
        String email = EmailField.getText();
        String tel = TelField.getText();
        String password = MdpField.getText();
        String confirmPassword = ConfMdp.getText();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Check if any field is empty
        if (fullName.isEmpty() || email.isEmpty() || tel.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Please fill in all the fields.");
            return;
        }

        // Check password complexity
        int complexityScore = evaluatePasswordComplexity(password);
        updateComplexityMessage(complexityScore); // Update password complexity message

        if (complexityScore <= 1) {
            showAlert("The password is too weak. Please choose a stronger password.");
            return;
        }

        // Check email format
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            showAlert("Invalid email format!");
            return;
        }

        // Check if the email is already registered
        if (ServUser.existemail(email)) {
            showAlert("This email is already registered!");
            return;
        }

        // Check phone number format
        if (!tel.matches("\\d{8}")) {
            showAlert("Invalid phone number format!");
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match!");
            return;
        }

        // Add the user to the database
        try {
            User client = new User();
            client.setFullName(fullName);
            client.setEmail(email);
            client.setMdp(hashedPassword);
            client.setTel(tel);
            client.setImage(imageData);
            ServUser.ajouter(client);

            // Récupérer le numéro de téléphone de TelField
            String tel1 = TelField.getText();

            // Appeler la méthode SMSSender de SmsSender avec le numéro de téléphone récupéré
            SmsSender.sendSMS(tel1, "Bienvenue  "+fullName+ "! Votre inscription est réussie .\n  Email :" +email+" \n Password :"+password);

            
            showAlert("Welcome! Registration successful.");
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            showAlert("An error occurred during registration.");
        }
    }

    private void updateComplexityMessage(int complexityScore) {
        String message;
        if (complexityScore <= 1) {
            message = "Weak";
        } else if (complexityScore <= 3) {
            message = "Moderate";
        } else if (complexityScore <= 5) {
            message = "Strong";
        } else {
            message = "Very Strong";
        }
        /*complexityLabel.setText("Password Complexity: " + message);*/

    }

    private int evaluatePasswordComplexity(String password) {
        int score = 0;

        // Check password length
        if (password.length() >= 8) {
            score++;
        }

        // Check for both uppercase and lowercase letters
        if (password.matches(".*[a-z].*") && password.matches(".*[A-Z].*")) {
            score++;
        }

        // Check for digits
        if (password.matches(".*\\d.*")) {
            score++;
        }

        // Check for special characters
        if (password.matches(".*[^a-zA-Z0-9].*")) {
            score++;
        }

        return score;
    }




    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    private void onUploadButtonClick(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            //imageData = Files.readAllBytes(selectedFile.toPath());
            imageData=selectedFile.getPath();
        }


    }




    public void cancel(ActionEvent event) throws IOException {
        FullNameField.setText("");
        EmailField.setText("");
        TelField.setText("");
        MdpField.setText("");
        ConfMdp.setText("");

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




    public void setEmailField(TextField emailField) {
        EmailField = emailField;
    }
}
