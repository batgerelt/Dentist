package schedule;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ResourceBundle;


public class ScheduleContorller implements Initializable {
    private int week = 1;
    private int nullValue = 0;
    private int row = 1, column = 1;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    @FXML
    private TextField patientName;
    @FXML
    private TableView<doctorSchedule> scheduleTableViewId;

    @FXML
    private TableColumn<doctorSchedule, String> monday;

    @FXML
    private TableColumn<doctorSchedule, String> tuesday;

    @FXML
    private TableColumn<doctorSchedule, String> wednesday;

    @FXML
    private TableColumn<doctorSchedule, String> thursday;

    @FXML
    private TableColumn<doctorSchedule, String> friday;

    @FXML
    private TableColumn<doctorSchedule, String> saturday;

    @FXML
    private TableColumn<doctorSchedule, String> sunday;
    @FXML
    private ChoiceBox<?> Button_patient;

    @FXML
    private ChoiceBox<?> Button_doctor;

    @FXML
    private DatePicker Choice_date;

    @FXML
    private Button Button_cancel;

    @FXML
    private Button Button_done;

    @FXML
    private Label second;

    @FXML
    private Label third;

    @FXML
    private Label fourth;

    @FXML
    private Label fifth;

    @FXML
    private Label sixth;

    @FXML
    private Label seventh;

    @FXML
    private Label first;

    private LocalDateTime now = LocalDateTime.now();
    private int month = now.getMonthValue();
    private int day = now.getDayOfMonth();
    private String[] array = new String[22];
    private ObservableList<doctorSchedule> selectedItems = FXCollections.observableArrayList(); 
    private String[][] welt;
    private String[] savedIndex;
    private int beginIndex = 0;
    private int too = 0;
    ObservableList data;
    ObservableList data1;

    @FXML
    void Cancel(ActionEvent event) {

    }

    @FXML
    void Done(ActionEvent event) {

    }

    @FXML
    void emch_songoh(MouseEvent event) {
        System.err.println("Ready for choice doctor");
        try {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            data = FXCollections.observableArrayList();
            String sql = "SELECT lname FROM user;";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                data.add(rs.getString("lname"));
            }
            System.err.println(data.get(0).toString());
            Button_doctor.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void uvchtun_songoh(MouseEvent event) {
        System.err.println("Ready for choice patient");
        try {
        	ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            data = FXCollections.observableArrayList();
            String sql = "SELECT Fname FROM patient ;";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                data.add(rs.getString("Fname"));
            }
            Button_patient.setItems(data);
            System.err.println(data.get(0).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //cbxPatient.setItems(data);
    }
    @FXML
    void udur_songoh(ActionEvent event) {
        //System .err.println(choose_day.getValue());
        String date = Choice_date.getValue().toString();
        try {
        	ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            data = FXCollections.observableArrayList();
            String sql = "SELECT * from schedule where date ='"+date+"';";
            ResultSet rs = connection.createStatement().executeQuery(sql);
//            while (rs.next()) {
//                data.add(rs.getString("date"));
//                data1.add(rs.getString("date"));
//            }
//            System.err.println(data.get(0).toString());
//            //choice_doctor.setItems(data);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    @FXML
    void next(ActionEvent event) {
        if(week <= 2){
            week++;
            too+=7;
            for ( int i = 0; i<scheduleTableViewId.getItems().size(); i++) {
                scheduleTableViewId.getItems().clear();
            }
            calculateNextDate(too);
        }
    }

    @FXML
    void previous(ActionEvent event) {
        if(week > 1){
            week--;
            beginIndex-=14;
            too-=7;
            for ( int i = 0; i<scheduleTableViewId.getItems().size(); i++) {
                scheduleTableViewId.getItems().clear();
            }
            calculateNextDate(too);
        }
    }

    private void calculateNextDate(int index) {
        first.setText(array[beginIndex++]);
        second.setText(array[beginIndex++]);
        third.setText(array[beginIndex++]);
        fourth.setText(array[beginIndex++]);
        fifth.setText(array[beginIndex++]);
        sixth.setText(array[beginIndex++]);
        seventh.setText(array[beginIndex++]);

        int q;
        for (int rowCount = 0; rowCount < 12; rowCount++) {
            q = 0;
            for (int columnCount = index; columnCount < index + 7; columnCount++) {

                for(int i = index; i < index + 7; i++){
                    if (columnCount == i) {
                        savedIndex[q] = welt[rowCount][columnCount];
                        q++;
                    }
                }
            }

            selectedItems.add(new doctorSchedule(savedIndex[0],
                    savedIndex[1],
                    savedIndex[2],
                    savedIndex[3],
                    savedIndex[4],
                    savedIndex[5],
                    savedIndex[6]));

            for (int i = 0; i < 7; i++)
                savedIndex[i] = null;
        }

        monday.setCellValueFactory(new PropertyValueFactory<>("columnMonday"));
        tuesday.setCellValueFactory(new PropertyValueFactory<>("columnTuesday"));
        wednesday.setCellValueFactory(new PropertyValueFactory<>("columnWednesday"));
        thursday.setCellValueFactory(new PropertyValueFactory<>("columnThursday"));
        friday.setCellValueFactory(new PropertyValueFactory<>("columnFriday"));
        saturday.setCellValueFactory(new PropertyValueFactory<>("columnSaturday"));
        sunday.setCellValueFactory(new PropertyValueFactory<>("columnSunday"));
        scheduleTableViewId.setItems(selectedItems);

    }


    @FXML
    void getCell(MouseEvent event) {
        scheduleTableViewId.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scheduleTableViewId.getSelectionModel().setCellSelectionEnabled(true);
                ObservableList selectedCells = scheduleTableViewId.getSelectionModel().getSelectedCells();
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                Object obj = scheduleTableViewId.getSelectionModel().getSelectedCells().get(0).getTableColumn().getCellData(tablePosition.getRow());
                column = (scheduleTableViewId.getSelectionModel().getSelectedCells().get(0)).getColumn() + 1;
                row = (scheduleTableViewId.getSelectionModel().getSelectedCells().get(0)).getRow() + 1;
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/tab.fxml"));
                TabPane rooter = null;

            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void search(ActionEvent event) throws SQLException {
        String query = "SELECT d.tRow, d.tCol " +
                         "FROM patient p, schedule d " +
                            " WHERE d.p_id = p.id and p.FName LIKE '%" + patientName.getText()+"%'";
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement st = connection.prepareStatement(query);
        ResultSet result = st.executeQuery(query);
        int diffDay = 0, addDay = 0;
        String time = null;
        while (result.next()) {
            switch (result.getInt("tRow")){
                case 1:
                    time = "8:00-9:00";
                    break;
                case 2:
                    time = "9:00-10:00";
                    break;
                case 3:
                    time = "10:00-11:00";
                    break;
                case 4:
                    time = "11:00-12:00";
                    break;
                case 5:
                    time = "12:00-13:00";
                    break;
                case 6:
                    time = "13:00-14:00";
                    break;
                case 7:
                    time = "14:00-15:00";
                    break;
                case 8:
                    time = "15:00-16:00";
                    break;
                case 9:
                    time = "16:00-17:00";
                    break;
                case 10:
                    time = "17:00-18:00";
                    break;
                case 11:
                    time = "18:00-19:00";
                    break;
                case 12:
                    time = "19:00-20:00";
                    break;
            }
            addDay = now.getDayOfMonth() + result.getInt("tColumn");
            if((now.getDayOfMonth() + result.getInt("tColumn")) <= YearMonth.of(now.getYear(), month).lengthOfMonth()){
                alert.setContentText("Month: " + now.getMonthValue() + " day: " + addDay + "\n"
                        + "Time: " + time);
                alert.showAndWait();
            }else{
                diffDay = (addDay - YearMonth.of(now.getYear(), now.getMonthValue()).lengthOfMonth());
                alert.setContentText("Month: " + (now.getMonthValue()+1) + " day: " + diffDay + "\n"
                        + "Time: " + time);
                alert.showAndWait();
            }
            diffDay = 0;
        }
    }
    private String doctorRegisterID;
    void scheduleuserAuthentication(String username) {
        doctorRegisterID = username;
        week = 1;
        scheduleTableViewId.widthProperty().addListener((source, oldWidth, newWidth) -> {
            alert.setTitle("Information");
            alert.setHeaderText(null);
            Pane header = (Pane) scheduleTableViewId.lookup("TableHeaderRow");
            if (header.isVisible()) {
                header.setMaxHeight(0);
                header.setMinHeight(0);
                header.setPrefHeight(0);
                header.setVisible(false);
            }
        });

        for (int i = 0; i < array.length; i++) {
            if (YearMonth.of(now.getYear(), month).lengthOfMonth() < day) {
                if (month <= 12)
                    month++;
                else
                    month = 1;
                day = 1;
            }
            array[i] = month + " / " + day;
            day++;
        }
        ConnectionClass connectionClass = new ConnectionClass();
        scheduleTableViewId.requestFocus();
        scheduleTableViewId.setFixedCellSize(40);
        welt = new String[][]{{" ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "},
                            {" ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "},
                            {" ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "},
                            {" ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "},
                            {" ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "},
                            {" ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "},
                            {" ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "},
                            {" ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "},
                            {" ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "},
                            {" ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "},
                            {" ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "},
                            {" ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "}};

        savedIndex = new String[]{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "," ", " ", " ", " ", " ", " ", " "};

//        String query = "SELECT p.FName, d.tRow, d.tCol FROM patient p, schedule d, user u where p.pId = d.phId and p.pDoctorId = u.id and u.uRegister = '"+ username +"'";
//        try {
//            Statement st = connection.prepareStatement(query);
//            ResultSet result = st.executeQuery(query);
//
//            while (result.next()) {
//                welt[result.getInt("tRow") - 1][result.getInt("tColumn") - 1] = result.getString("pName");
//            }
//            calculateNextDate(too);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}

