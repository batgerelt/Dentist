package inspection;

import ModelClass.InspectionTable;
import ModelClass.TreatmentTable;
import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class inspectionController implements Initializable {

    @FXML
    public ComboBox cbxPatientInfo;

    ObservableList data;

    String insId;

    //check controller 2
    @FXML
    private CheckBox sick1;

    @FXML
    private CheckBox sick2;

    @FXML
    private CheckBox sick3;

    @FXML
    private CheckBox sick5;

    @FXML
    private CheckBox sick6;

    @FXML
    private CheckBox sick7;

    @FXML
    private CheckBox sick4;

    @FXML
    private CheckBox sick8;

    @FXML
    private CheckBox sick9;

    @FXML
    private CheckBox sick11;

    @FXML
    private CheckBox sick10;

    @FXML
    private TextArea zoviur;

    @FXML
    private TextField zoolonEd;

    @FXML
    private CheckBox dSick12;

    @FXML
    private CheckBox dSick13;

    @FXML
    private CheckBox dSick14;

    @FXML
    private CheckBox dSick15;

    @FXML
    private CheckBox dSick16;

    @FXML
    private TextField tungalag;

    @FXML
    private TextField hel;

    @FXML
    private TextField uruul;

    @FXML
    private DatePicker iDate;

    @FXML
    private TableView<InspectionTable> ins_table;

    @FXML
    private TableColumn<InspectionTable, String> ins_id;

    @FXML
    private TableColumn<InspectionTable, String> ins_date;


    @FXML
    private Button ins_create;

    @FXML
    private Button idConfirm;

    ObservableList pID;

    String[] patientId;

    //controller 2 functions
    ObservableList<String> checkBoxList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // cbxTreatmentChoice();
        sick1.setOnAction(e -> {
            checkBoxList.add(sick1.getText());
        });
        sick2.setOnAction(e -> {
            checkBoxList.add(sick2.getText());
        });
        sick3.setOnAction(e -> {
            checkBoxList.add(sick3.getText());
        });
        sick4.setOnAction(e -> {
            checkBoxList.add(sick4.getText());
        });
        sick5.setOnAction(e -> {
            checkBoxList.add(sick5.getText());
        });
        sick6.setOnAction(e -> {
            checkBoxList.add(sick6.getText());
        });
        sick7.setOnAction(e -> {
            checkBoxList.add(sick7.getText());
        });
        sick8.setOnAction(e -> {
            checkBoxList.add(sick8.getText());
        });
        sick9.setOnAction(e -> {
            checkBoxList.add(sick9.getText());
        });
        sick10.setOnAction(e -> {
            checkBoxList.add(sick10.getText());
        });
        sick11.setOnAction(e -> {
            checkBoxList.add(sick11.getText());
        });
        dSick12.setOnAction(e->{
            checkBoxList.add(dSick12.getText());
        });
        dSick13.setOnAction(e->{
            checkBoxList.add(dSick13.getText());
        });
        dSick14.setOnAction(e->{
            checkBoxList.add(dSick14.getText());
        });
        dSick15.setOnAction(e->{
            checkBoxList.add(dSick15.getText());
        });
        dSick16.setOnAction(e->{
            checkBoxList.add(dSick16.getText());
        });

        iDate.setOnAction(e->{
            cbxPatientInfo.setDisable(false);
        });
        ins_create.setDisable(true);
        idConfirm.setDisable(true);

    }

    @FXML
    void getCell(MouseEvent event) {
        ins_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ins_table.getSelectionModel().setCellSelectionEnabled(true);
                ObservableList selectedCell = ins_table.getSelectionModel().getSelectedCells();
                TablePosition tablePosition = (TablePosition) selectedCell.get(0);
                Object obj = ins_table.getSelectionModel().getSelectedCells().get(0).getTableColumn().getCellData(tablePosition.getRow());
                System.out.println("test " + obj.toString());
                String id = obj.toString();
                if(id.length() < 3)
                ins_show(id);
                ins_create.setDisable(false);
            }
        });
    }

    @FXML
    void btnConfirm(ActionEvent event) {
        String p_id;
        String sql1;
        String id;
        try {
            if(validateFields()) {
            	ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                String sql = "Insert into inspection(p_id,t_id,date,doctor_id,pain_now,lymph_gland_type,lips,tongue) " +
                        "values(" + patientId[0] + ",1,'" + iDate.getValue().toString() + "',1,'" + zoviur.getText() + "','" + zoolonEd.getText() + "','" + uruul.getText() + "','" + hel.getText() + "')";
                Statement statement = connection.createStatement();
                statement.execute(sql);
                id = inspectionId();
                for (int i = 0; i < checkBoxList.size(); i++) {
                    System.out.println(checkBoxList.get(i));
                    p_id = painId(checkBoxList.get(i));
                    System.out.print(checkBoxList.get(i));
                    sql1 = "Insert into pain_inspection(inspection_id,pain_id,type) values(" + id + "," + p_id + ",'1')";
                    statement.execute(sql1);

                }
                showInspectionTable();
                clearSelect();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Анхааруулга");
                alert.setHeaderText(null);
                alert.setContentText("Бөглөөгүй талбар байна!!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //uzlegin id butsaah function
    private String inspectionId(){
        ResultSet rs = null;

        try {
        	ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            String sql = "Select id from inspection where date = '"+ iDate.getValue().toString()+"' and p_id = "+patientId[0]+"";
            rs = connection.createStatement().executeQuery(sql);

            if (rs.next()) {
                insId = rs.getString("id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
        return insId;
    }

    // inspection medeelel
    void ins_show(String ins_id) {
        ResultSet rs = null;
        clearSelect();
        try {
        	ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            String sql = "Select pain_id from pain_inspection where inspection_id= "+ins_id+" ";
            rs = connection.createStatement().executeQuery(sql);
            while(rs.next()) {
                String pain = rs.getString("pain_id");
                System.out.println("pain "+pain);
                System.out.println("hha");
                switch (pain){
                    case "1" : sick1.setSelected(true);
                        break;
                    case "2" : sick2.setSelected(true);
                        break;
                    case "3" : sick3.setSelected(true);
                        break;
                    case "4" : sick4.setSelected(true);
                        break;
                    case "5" : sick5.setSelected(true);
                        break;
                    case "6" : sick6.setSelected(true);
                        break;
                    case "7" : sick7.setSelected(true);
                        break;
                    case "8" : sick8.setSelected(true);
                        break;
                    case "9" : sick9.setSelected(true);
                        break;
                    case "10" : sick10.setSelected(true);
                        break;
                    case "11" : sick11.setSelected(true);
                        break;
                    case "12" : dSick12.setSelected(true);
                        break;
                    case "13" : dSick13.setSelected(true);
                        break;
                    case "14" : dSick14.setSelected(true);
                        break;
                    case "15" : dSick15.setSelected(true);
                        break;
                    case "16" : dSick16.setSelected(true);
                        break;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }

        ins_show1(ins_id);
        idConfirm.setDisable(true);

    }

    //from buren esehig shalgah
    private boolean validateFields(){
        System.out.println((sick1.isSelected() || sick2.isSelected() || sick3.isSelected() || sick4.isSelected()
                || sick5.isSelected() || sick6.isSelected() || sick7.isSelected()));
        System.out.println((sick8.isSelected() || sick9.isSelected() || sick10.isSelected() || sick11.isSelected()));
        System.out.println();
        if((sick1.isSelected() || sick2.isSelected() || sick3.isSelected() || sick4.isSelected()
            || sick5.isSelected() || sick6.isSelected() || sick7.isSelected()) && (sick8.isSelected() || sick9.isSelected()
            || sick10.isSelected() || sick11.isSelected()) && !zoviur.getText().isEmpty() && !zoolonEd.getText().isEmpty()
            && !tungalag.getText().isEmpty() && !hel.getText().isEmpty() && !uruul.getText().isEmpty() ){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Мэдээдэл");
                alert.setHeaderText(null);
                alert.setContentText("Амжилттай хадгалагдлаа");
                alert.showAndWait();
                return true;
            }
            return false;
    }



    private void ins_show1(String i_id){
        ResultSet rs = null;
        try {
        	ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            String sql = "Select pain_now,lymph_gland_type,lips,tongue from inspection where id = "+i_id+"";
            rs = connection.createStatement().executeQuery(sql);
            if (rs.next()) {
                String pain_now = rs.getString("pain_now");
                zoviur.setText(pain_now);
                String lymph = rs.getString("lymph_gland_type");
                tungalag.setText(lymph);
                String lips = rs.getString("lips");
                uruul.setText(lips);
                String tongue = rs.getString("tongue");
                hel.setText(tongue);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }
    // omnoh medeelluddug tseverlene
    @FXML
    void btnInsCreate(MouseEvent event) {
      clearSelect();
    }

    //buh formoo tseverleh function
    void clearSelect(){
        idConfirm.setDisable(false) ;
        sick1.setSelected(false);
        sick2.setSelected(false);
        sick3.setSelected(false);
        sick4.setSelected(false);
        sick5.setSelected(false);
        sick6.setSelected(false);
        sick7.setSelected(false);
        sick8.setSelected(false);
        sick9.setSelected(false);
        sick10.setSelected(false);
        sick11.setSelected(false);
        dSick12.setSelected(false);
        dSick13.setSelected(false);
        dSick14.setSelected(false);
        dSick15.setSelected(false);
        dSick16.setSelected(false);
        zoviur.clear();
        zoolonEd.clear();
        tungalag.clear();
        hel.clear();
        uruul.clear();
        ins_create.setDisable(true);
        checkBoxList.clear();
    }

    private void showInspectionTable(){

        ObservableList<InspectionTable> oblist = FXCollections.observableArrayList();
        try {
        	ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            String sql = "Select id,date from inspection where p_id="+patientId[0]+"";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()){
                InspectionTable table = new InspectionTable(rs.getString("id"),rs.getString("date"));
                oblist.add(table);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        ins_id.setCellValueFactory(new PropertyValueFactory<>("ins_id"));
        ins_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        ins_table.setItems(oblist);

    }

    private String painId(String pain){
        try {
        	ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            pID = FXCollections.observableArrayList();
            //System.out.println("tName "+tName);
            String sql = "Select id from pain where p_name = '"+pain+"'";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            System.out.println(rs.toString());
            while (rs.next()) {
                pID.add(rs.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (String) pID.get(0);
    }




    //controller 1 function
    @FXML
    private ImageView logout;

    // patient choice function

    //patient info function
    @FXML
    void cbxPatientInfoChoice(MouseEvent event) {
        try {
        	ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            data = FXCollections.observableArrayList();
            String sql = "SELECT concat(schedule.p_id,' ,',patient.Fname,' ,',patient.Lname) as info FROM schedule \n" +
                    "left join patient on  patient.id = schedule.p_id where schedule.date = '" + iDate.getValue().toString() + "'";
            System.out.println(sql);
            ResultSet rs = connection.createStatement().executeQuery(sql);
            //  System.out.println("test2"+rs.toString());
            while (rs.next()) {
                data.add(rs.getString("info"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error");
        }
        cbxPatientInfo.setItems(data);
        System.out.print(data.get(0).toString());
        String info = data.get(0).toString();
        patientId = info.split(" ");
        if(cbxPatientInfo.getValue() != null) {


            showInspectionTable();
            idConfirm.setDisable(false);
        }

    }

    @FXML
    void handleClickCloseBtn(ActionEvent event) {
        System.out.println("logout");
    }

    @FXML
    void handleLogout(MouseEvent event) {
        ButtonType yes = new ButtonType("Тийм", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("Буцах", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.WARNING, "",  yes, no);
        alert.setTitle("");
        alert.setHeaderText("Та програмаас гарахдаа итгэлтэй байна уу?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no) == yes) {
            //do stuf
//            Stage stage = (Stage) logout.getScene().getWindow();
//           stage.close();
        }
    }
}
