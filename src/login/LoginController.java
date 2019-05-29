package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.MainWindowController;
import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {

    @FXML
    private AnchorPane topPanel;

    @FXML
    private AnchorPane middlePanel;

    @FXML
    private JFXPasswordField password;

    @FXML
    private Text logintext;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton btnclose;

    @FXML
    private JFXButton btnlogin;

    @FXML
    private JFXButton btnMinimize;

    @FXML
    private JFXTextField username;

    @FXML
    private Stage primaryStage;

    @FXML
    void setStageAndSetupListeners(Stage stage){
        primaryStage = stage;
    }

    User loggedInUser = new User();

    @FXML
    void login(ActionEvent event) throws SQLException {
        if (password.getText().isEmpty() || username.getText().isEmpty()) {
            logintext.setText("Хэрэглэгчийн нэвтрэх нэр болон нууц үгээ оруулна уу!");
        } else {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            String sql = "SELECT password from user where email='"+username.getText()+"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(!resultSet.next()){
                logintext.setText("Хэрэглэгчийн нэвтрэх нэр болон нууц үг буруу байна.");
                return;
            }
            resultSet.beforeFirst();
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(password.getText())) {
                    try {
                        loggedInUser.getUserByEmail(username.getText());

                        FXMLLoader view = new FXMLLoader(getClass().getResource("../application/MainWindowFxml.fxml"));

                        Parent patient = view.load();

                        MainWindowController mainController = view.getController();
                        mainController.setLoggedInUser(loggedInUser);

                        Scene scene = new Scene(patient);
                        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                        appStage.initStyle(StageStyle.DECORATED);
                        appStage.setScene(scene);
                        appStage.centerOnScreen();
                        appStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    logintext.setText("Хэрэглэгчийн нэвтрэх нэр болон нууц үг буруу байна.");
                }
            }

        }

    }

}
