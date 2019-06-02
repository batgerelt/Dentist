package treatment;

import ModelClass.TreatmentTable;
import ModelClass.Treatments;
import application.MainWindowController;
import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import login.User;

public class treatmentController implements Initializable {
    // patient info lundeg
	static User loggedInUser;
    @FXML
    private TextField ln;

    @FXML
    private TextField fn;

    @FXML
    private TextField age;

    @FXML
    private TextField gender;

    @FXML
    private TextField register;

    @FXML
    private TextField bDate;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneT;

    @FXML
    private TextField address;

    @FXML
    private ComboBox cbxTreatment;

    @FXML
    private TextField teeth_index;

    @FXML
    private DatePicker completedDate;

    @FXML
    private TableView<TreatmentTable> treatTable;

    @FXML
    private TableColumn<TreatmentTable, String> treatDate;

    @FXML
    private TableColumn<TreatmentTable, String> treatTeeth;

    @FXML
    private TableColumn<TreatmentTable, String> treat;

    @FXML
    private TableColumn<TreatmentTable, String> treatState;

    //Эмчилгээнүүдийг харагдуулах хүснэгт
    @FXML
    private TableView<Treatments> treatmentsTable;

    @FXML
    private TableColumn<String, Treatments> treatmentColumn;

    @FXML
    private ComboBox<String> cbxState;

    @FXML
    private DatePicker tDate;

    @FXML
    private Button tAdd;

    @FXML
    private ComboBox cbxPatientInfo1;

    ObservableList data;
    ObservableList<Treatments> treatmentList = FXCollections.observableArrayList();
    String[] patientId;
    String pID;
    
    // doctor id avah
    MainWindowController con = new MainWindowController();


    // tolov songoh combobox
    private void selectCbx() {
        ObservableList<String> list = FXCollections.observableArrayList("Дууссан", "Дуусаагүй");
        cbxState.setItems(list);

    }
    
    @FXML
    void cbxPatientInfoChoice1(MouseEvent event) {
        try {
        	ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            
            data = FXCollections.observableArrayList();
            String sql = "SELECT concat(schedule.p_id,' ,',patient.Fname,' ,',patient.Lname) as info FROM schedule \n" +
                    "left join patient on  patient.id = schedule.p_id where schedule.date = '" + tDate.getValue().toString() + "'";
            System.out.println(sql);
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                data.add(rs.getString("info"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error");
        }
        cbxPatientInfo1.setItems(data);
        String info = data.get(0).toString();
        patientId = info.split(" ");
        if (cbxPatientInfo1.getValue() != null) {
            tAdd.setDisable(false);
            patient_Info(patientId[0]);
            treatmentTable(patientId[0]);
        }
    }


    //treatment id butsaah function
    String treatment_id(String tName) {
        try {
        	ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            data = FXCollections.observableArrayList();
            String sql = "Select id from treatment where t_name = '" + tName + "'";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            System.out.println(rs.toString());
            while (rs.next()) {
                data.add(rs.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (String) data.get(0);
    }

    //ovchtoni hiigdsen emchilgeegiin buh medeellig haruulah husnegt
    void treatmentTable(String patient_id) {
        ObservableList<TreatmentTable> oblist = FXCollections.observableArrayList();
        try {
        	ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            String sql = "SELECT patient_treatment.expirydate,\n" +
                    "patient_treatment.teeth,treatment.t_name,patient_treatment.type\n" +
                    "FROM patient_treatment\n" +
                    "LEFT JOIN treatment\n" +
                    "ON patient_treatment.treatment_id = treatment.id\n" +
                    "where patient_treatment.patient_id = '" + patient_id + "'";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                TreatmentTable table1 = new TreatmentTable(rs.getString("patient_treatment.expirydate"), rs.getString("patient_treatment.teeth"), rs.getString("treatment.t_name"), rs.getString("patient_treatment.type"));
                oblist.add(table1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        treatDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        treatTeeth.setCellValueFactory(new PropertyValueFactory<>("teeth"));
        treat.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        treatState.setCellValueFactory(new PropertyValueFactory<>("state"));
        treatTable.setItems(oblist);


    }

    @FXML
    void treatment_Add() throws SQLException {
        if (validateFields()) {
            int state;
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            for (Treatments treatments : treatmentList
            ) {
                System.out.println("test " + treatments.toString());
                String t_id = treatment_id(treatments.getNer());
                if (cbxState.getSelectionModel().getSelectedItem().equals("Дууссан"))
                    state = 1;
                else state = 0;

                String sql = "INSERT INTO patient_treatment(patient_id,treatment_id,doctor_id,type,expiryDate,teeth) VALUES(" + patientId[0] + "," + Integer.valueOf(t_id) + ","+Integer.valueOf(con.getLoggedUser())+",'" + state + "','" + completedDate.getValue().toString() + "','" + teeth_index.getText() + "')";
                Statement statement = connection.createStatement();
                statement.execute(sql);
                treatmentTable(patientId[0]);
            }
            dataClear();
        }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Анхааруулга");
                alert.setHeaderText(null);
                alert.setContentText("Бөглөөгүй талбар байна!");
                alert.showAndWait();
            }
        }


    // patient information function
    void patient_Info(String id) {
    	
        ResultSet rs = null;
        try {
        	ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            String sql = "SELECT Fname,Lname,Address,BirthDate,Email,pNumber,RegisterNo,Gender from patient where id = " + id + "";
            rs = connection.createStatement().executeQuery(sql);
            if (rs.next()) {
                String lastName = rs.getString("Lname");
                ln.setText(lastName);
                String firstName = rs.getString("Fname");
                fn.setText(firstName);
                String phone2 = rs.getString("PNumber");
                phoneT.setText(phone2);
                String gen = rs.getString("Gender");
                if (gen.contentEquals("m")) {
                    gender.setText("Эрэгтэй");
                } else if (gen.contentEquals("f")) {
                    gender.setText("Эмэгтэй");
                }

                String date = rs.getString("BirthDate");
                bDate.setText(date);
                String address1 = rs.getString("Address");
                address.setText(address1);
                String email1 = rs.getString("Email");
                email.setText(email1);
                String reg = rs.getString("RegisterNo");
                register.setText(reg);

                DateFormat dateFormat = new SimpleDateFormat("yyyy");
                Date current_date = new Date();
                int p_age = Integer.valueOf(dateFormat.format(current_date)) - Integer.valueOf(date.substring(0, 4));
                age.setText(String.valueOf(p_age));

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    // hiigdsen emchilgee songoh function lundeg
    void cbxTreatmentChoice() {

        try {
        	ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            data = FXCollections.observableArrayList();
            String sql = "SELECT t_name FROM treatment";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            //   System.out.println(rs.toString());
            while (rs.next()) {
                data.add(rs.getString("t_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cbxTreatment.setItems(data);
    }

    // textfieldin form boglogdson esehig check function
    boolean validateFields() {
        if (!completedDate.getEditor().getText().isEmpty() && !teeth_index.getText().isEmpty() && !cbxTreatment.getSelectionModel().isEmpty() && !cbxPatientInfo1.getSelectionModel().isEmpty() && !cbxState.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Мэдээдэл");
            alert.setHeaderText(null);
            alert.setContentText("Амжилттай хадгалагдлаа");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    //ovchton dahin duudahad emjilgeeeni talbaruudin utgig clear hiih function
    void dataClear() {
        cbxTreatment.getEditor().clear();
        teeth_index.clear();
        completedDate.getEditor().clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tDate.setOnAction(event -> {
            cbxPatientInfo1.setDisable(false);
        });
        cbxTreatmentChoice();
        selectCbx();
        cbxTreatment.setOnAction(event -> {
            boolean isEquals = false;
            for (int i = 0; i < treatmentList.size(); i++) {
                if (treatmentList.get(i).equals(cbxTreatment.getValue().toString())) {
                    isEquals = true;
                }
            }
            if (!isEquals) {
                treatmentList.add(new Treatments(cbxTreatment.getValue().toString()));
                treatmentColumn.setCellValueFactory(new PropertyValueFactory<>("ner"));
                treatmentsTable.setItems(treatmentList);
            }
        });

        tAdd.setDisable(true);


    }
    
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
