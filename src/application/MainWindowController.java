package application;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindowController implements Initializable {

    @FXML
    private BorderPane rootPane;

    @FXML
    private ChoiceBox<?> choice_udurharah;

    @FXML
    private Button button_umnuhdoloohonog;

    @FXML
    private Button button_enedoloohonog;

    @FXML
    private Button button_daraadoloohonog;

    @FXML
    private Label time_lbl1;

    @FXML
    private Label time_lbl2;

    @FXML
    private Label time_lbl3;

    @FXML
    private Label time_lbl4;

    @FXML
    private Label time_lbl5;

    @FXML
    private Label time_lbl6;

    @FXML
    private Label time_lbl7;

    @FXML
    private Label time_lbl8;

    @FXML
    private Label time_lbl9;

    @FXML
    private Label day_lbl1;

    @FXML
    private Label day_lbl2;

    @FXML
    private Label day_lbl3;

    @FXML
    private Label day_lbl4;

    @FXML
    private Label day_lbl5;

    @FXML
    private DatePicker datepicker_udursongoh;

    @FXML
    private ChoiceBox<?> choice_uvchtunsongoh;

    @FXML
    private TextArea textarea1_uvchtunharah;

    @FXML
    private TextArea textarea_tsaghuviarlalt;

    @FXML
    private Button button_done;

    @FXML
    private Button button_cancel;

    @FXML
    private ToggleGroup face;

    @FXML
    private ToggleGroup mouth;

    @FXML
    private ToggleGroup length;

    @FXML
    private ToggleGroup growth;

    @FXML
    private ToggleGroup teeth;

    @FXML
    private Button closeBtn;
    
    @FXML
    private ImageView logout;

    @FXML
	void handleClickCloseBtn(ActionEvent event) {
    	System.out.println("logout");
	}
    
    @FXML
    void handleLogout(MouseEvent  event) {
    	ButtonType yes = new ButtonType("Тийм", ButtonData.YES);
    	ButtonType no = new ButtonType("Буцах", ButtonData.CANCEL_CLOSE);
    	
    	Alert alert = new Alert(AlertType.WARNING, "",  yes, no); 
    	alert.setTitle("");
    	alert.setHeaderText("Та програмаас гарахдаа итгэлтэй байна уу?");
    	
    	Optional<ButtonType> result = alert.showAndWait();

    	if (result.orElse(no) == yes) {
    	    //do stuff
    		Stage stage = (Stage) logout.getScene().getWindow();
    		stage.close();
    	}    	
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
	}
}
