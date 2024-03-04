package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import Entity.User;
import Services.ServiceUser;

import Utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ListUsersController implements Initializable {


    @FXML
    private TableView<User> TableUsers;

    ObservableList<User> obslistus = FXCollections.observableArrayList();

    @FXML
    private TableColumn<User, String> fullNameF;
    @FXML
    private TableColumn<User, String> emailF;
    @FXML
    private TableColumn<User, String> telF;
    @FXML
    private TableColumn<User, String> ApprF;
    @FXML
    private TableColumn<User, String> BlocF;
    @FXML
    private TableColumn<User, Void> act;

    @FXML
    private TextField search;


    Connection connection = DBConnection.getInstance().getConnection();
    @FXML
    private TableView<User> tableView;

    private ObservableList<User> UserList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            listUsers();

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception
        }
    }



   public ObservableList<User> listUsers() throws SQLException {
        ServiceUser UserS = new ServiceUser();
        /*User u = new User();*/
        UserS.afficher().stream().forEach((p) -> {
            obslistus.add(p);
        });
        fullNameF.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailF.setCellValueFactory(new PropertyValueFactory<>("email"));
        telF.setCellValueFactory(new PropertyValueFactory<>("tel"));
        ApprF.setCellValueFactory(new PropertyValueFactory<>("is_approved"));
        BlocF.setCellValueFactory(new PropertyValueFactory<>("is_blocked"));

        act.setCellFactory(column -> {


            return new TableCell<User, Void>() {
                private final Button deleteButton = new Button("Delete");
                private final Button getByIdButton = new Button("Get by ID");

                {
                    deleteButton.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        obslistus.remove(user);
                        try {
                            UserS.supprimer(user);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    });


                    getByIdButton.setOnAction(event -> {
                        try {
                            User user = getTableView().getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUserCard.fxml"));
                            Parent root = loader.load();
                            AdminUserCardController controller = loader.getController();
                            controller.initData(user.getId());
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(ListUsersController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                }
                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox buttonBox = new HBox();
                        //  buttonBox.setPadding(new Insets(4));
                        buttonBox.setSpacing(2);
                        buttonBox.getChildren().addAll(deleteButton, getByIdButton);
                        setGraphic(buttonBox);
                    }
                }
            };

                });
        TableUsers.setItems(obslistus);
        search.textProperty().addListener((obs, oldText, newText) -> {
            List<User> ae = null;
            try {
                ae = UserS.Search(newText);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            TableUsers.getItems().setAll(ae);
        });
        return null;
    }












    @FXML
    private void loadStat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Charts.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }



    @FXML
    private void pdf_user(ActionEvent event) {
        System.out.println("hello");
        try{

            JasperDesign jDesign = JRXmlLoader.load("C:\\Users\\melek\\Desktop\\Culturium\\src\\main\\resources\\report.jrxml");

            JasperReport jReport = JasperCompileManager.compileReport(jDesign);

            JasperPrint jPrint = JasperFillManager.fillReport(jReport, null, connection);

            JasperViewer viewer = new JasperViewer(jPrint, false);

            viewer.setTitle("Liste des Utilistaeurs");
            viewer.show();
            System.out.println("hello");


        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }



@FXML
    private void TrieEmail() throws SQLException {
        ServiceUser utiSer = new ServiceUser();
        User user = new User();
        List<User> a = utiSer.triEmail();
        fullNameF.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailF.setCellValueFactory(new PropertyValueFactory<>("email"));
        telF.setCellValueFactory(new PropertyValueFactory<>("tel"));

        TableUsers.getItems().setAll(a);

    }


    @FXML
    private void triFullName() throws SQLException {
        ServiceUser utiSer = new ServiceUser();
        User user = new User();
        List<User> a = utiSer.triFullName();
        fullNameF.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailF.setCellValueFactory(new PropertyValueFactory<>("email"));
        telF.setCellValueFactory(new PropertyValueFactory<>("tel"));

        TableUsers.getItems().setAll(a);

    }








    @FXML
    private void goBackHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminDashboard.fxml"));
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


