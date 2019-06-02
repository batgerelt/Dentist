    package patient;

    import connectivity.ConnectionClass;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;
    import javafx.scene.control.cell.PropertyValueFactory;
    import login.User;

    import java.sql.Connection;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.time.LocalDate;
    import java.util.Date;

    public class PatientController {
        User loggedInUser;
        Patient selectedPatient;
        ObservableList<Patient> PatientList = FXCollections.observableArrayList();
        public boolean isInteger( String input ) {
            try {
                Integer.parseInt( input );
                return true;
            }
            catch( Exception e ) {
                return false;
            }
        }

        @FXML
        private Button patientBackBtn;
        @FXML
        private TableView<Patient> PatientTable;
        @FXML
        private TableColumn<Patient, Date> patientListDOB;
        @FXML
        private TableColumn<Patient, String> patientListFname;
        @FXML
        private TableColumn<Patient, String> patientListLname;
        @FXML
        private TableColumn<Patient, String> patientListRegister;
        @FXML
        private TextField patientName;
        @FXML
        private Button patientSaveBtn;
        @FXML
        private Button patientEditBtn;
        @FXML
        private ChoiceBox<String> patientGender;
        @FXML
        private Button patientSearchBtn;
        @FXML
        private TextField patientPhone;
        @FXML
        private DatePicker patientDob;
        @FXML
        private Button patientCreateBtn;
        @FXML
        private TextField patientRegisterNo;
        @FXML
        private TextArea patientComment;
        @FXML
        private TextField patientEmail;
        @FXML
        private Button patientViewBtn;
        @FXML
        private TextField patientAddress;
        @FXML
        private TextField patientSearchField;
        @FXML
        private TextField patientSurname;

        @FXML
        public void initialize() {
            makeRightBtnsInvi();
            makeFieldsRO();
        }

        @FXML
        void viewPatient(ActionEvent event) {
            if(PatientTable.getSelectionModel().getSelectedItem() == null)
                showAlert("Өвчтөн сонгоогүй байна!");
            else{
                makeFieldsRO();
                makeRightBtnsInvi();
                selectedPatient = PatientTable.getSelectionModel().getSelectedItem();
                patientRegisterNo.setText(selectedPatient.getRegisterNo());
                patientSurname.setText(selectedPatient.getLname());
                patientName.setText(selectedPatient.getFname());
                if(selectedPatient.getGender() == 'm')
                    patientGender.setValue(patientGender.getItems().get(0));
                else
                    patientGender.setValue(patientGender.getItems().get(1));
                patientDob.setValue(selectedPatient.getBirthDate());
                patientAddress.setText(selectedPatient.getAddress());
                patientPhone.setText(selectedPatient.getPnumber());
                patientEmail.setText(selectedPatient.getEmail());
                patientComment.setText(selectedPatient.getComment());
            }
        }

        @FXML
        void backPatient(ActionEvent event) {
            if(selectedPatient != null)
                viewPatient(event);
            makeFieldsRO();
            makeRightBtnsInvi();
            enableLefttBtns();
        }

        @FXML
        void createPatient(ActionEvent event) {
            makeRightBtnsVi();
            selectedPatient = null;
            clearFields();
            makeFieldsEditable();
            this.patientRegisterNo.requestFocus();
            disableLeftBtns();

        }

        @FXML
        void editPatient(ActionEvent event) {
            if(selectedPatient == null){
                showAlert("Өвчтөн сонгоогүй байна!\n(Та дэлгэрэнгүй товч дарсны дараа засах боломжтойг анхаарна уу)");
            }
            else{
                makeFieldsEditable();
                makeRightBtnsVi();
                disableLeftBtns();

            }
        }

        @FXML
        void savePatient(ActionEvent event) throws SQLException {
            if(patientSurname.getText() == null || patientSurname.getText().isEmpty() ||
                    patientName.getText() == null || patientName.getText().isEmpty() ||
                    patientDob.getValue() == null || patientGender.getValue() == null){
                showAlert("Овог, Нэр, хүйс ,Төрсөн огноог заавал бөглөх ёстой!");
            }else {
                if(checkfields()){
                    Patient pt = new Patient(patientName.getText(),
                            patientSurname.getText(),
                            patientRegisterNo.getText(),
                            patientDob.getValue(),
                            (patientGender.getValue().equals("Эр"))? 'm': 'f',
                            patientEmail.getText(),
                            patientPhone.getText(),
                            patientAddress.getText(),
                            patientComment.getText());
                    if(selectedPatient == null){
                        selectedPatient = pt;
                        selectedPatient.createPatient();
                        makeRightBtnsInvi();
                        enableLefttBtns();
                        makeFieldsRO();
                        PatientList.clear();
                        PatientList.add(selectedPatient);
                        PatientTable.setItems(PatientList);
                    }
                    else{
                        pt.setId(selectedPatient.getId());
                        selectedPatient = pt;
                        selectedPatient.updatePatient();
                        makeRightBtnsInvi();
                        enableLefttBtns();
                        makeFieldsRO();
                        PatientList.clear();
                        PatientList.add(selectedPatient);
                        PatientTable.setItems(PatientList);
                    }
                }
            }
        }

        @FXML
        void searchPatient(ActionEvent event) throws SQLException {
            if(patientSearchField.getText().isEmpty()){
                showAlert("Хайх хэсэг хоосон байна!");
            }
            else {
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                String qry = "SELECT * FROM patient WHERE RegisterNo like '%" + patientSearchField.getText() + "%' or Lname like '%" +
                        patientSearchField.getText() + "%' or Fname like '%" + patientSearchField.getText() +
                        "%' or Pnumber like '%" + patientSearchField.getText() +
                        "%' or email like '%" + patientSearchField.getText() + "%'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(qry);
                if (!resultSet.isBeforeFirst() ) {
                    showAlert("Өвчтөн олдсонгүй");
                }
                else {
                    selectedPatient = null;
                    PatientList.clear();
                    clearFields();
                    while (resultSet.next()) {
                        Patient pt = new Patient(resultSet.getString("fname"),resultSet.getString("lname"),
                                resultSet.getString("registerno"),resultSet.getDate("BirthDate").toLocalDate(),
                                resultSet.getString("gender").toCharArray()[0], resultSet.getString("Email"),
                                resultSet.getString("pnumber"), resultSet.getString("address"),
                                resultSet.getString("Comment"), resultSet.getInt("id"));
                        PatientList.add(pt);
                    }
                    patientListLname.setCellValueFactory(new PropertyValueFactory<>("lname"));
                    patientListFname.setCellValueFactory(new PropertyValueFactory<>("fname"));
                    patientListRegister.setCellValueFactory(new PropertyValueFactory<>("RegisterNo"));
                    patientListDOB.setCellValueFactory(new PropertyValueFactory<>("BirthDate"));

                    PatientTable.setItems(PatientList)  ;
                }
            }
        }

        public void makeRightBtnsInvi(){
            patientSaveBtn.setVisible(false);
            patientBackBtn.setVisible(false);
        }

        public boolean checkfields() throws SQLException {
            try{
                if(patientRegisterNo.getText()!= null){
                    String where = "";
                    if(selectedPatient != null){
                        where = " AND id != " + selectedPatient.getId()+"";
                    }
                    ConnectionClass connectionClass = new ConnectionClass();
                    Connection connection = connectionClass.getConnection();
                    String qry = "SELECT * FROM patient WHERE RegisterNo='" + patientRegisterNo.getText()+"'" + where;
                     Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(qry);
                    if(resultSet.next()){
                        showAlert("Регистерийн дугаар давхцаж байна өөр дугаар оруулна уу");
                        return false;
                    }
                }
                if(!isInteger(patientPhone.getText())){
                    showAlert("Утасны дугаар буруу байна.");
                    return false;
                }
                return true;
            }catch (SQLException e){
                showAlert(e.getMessage());
                return false;
            }
        }

        public void makeRightBtnsVi(){
            patientSaveBtn.setVisible(true);
            patientBackBtn.setVisible(true);
        }

        public void disableLeftBtns(){
            patientSearchBtn.setDisable(true);
            patientCreateBtn.setDisable(true);
            patientViewBtn.setDisable(true);
            patientEditBtn.setDisable(true);
        }

        public void enableLefttBtns(){
            patientSearchBtn.setDisable(false);
            patientCreateBtn.setDisable(false);
            patientViewBtn.setDisable(false);
            patientEditBtn.setDisable(false);

        }

        public void clearFields(){
            this.patientAddress.setText(null);
            this.patientComment.setText(null);
            this.patientEmail.setText(null);
            this.patientName.setText(null);
            this.patientPhone.setText(null);
            this.patientRegisterNo.setText(null);
            this.patientSurname.setText(null);
            this.patientDob.setValue(null);
            this.patientGender.setValue(null);
        }

        public void makeFieldsRO(){
            this.patientAddress.setDisable(true);
            this.patientComment.setDisable(true);
            this.patientEmail.setDisable(true);
            this.patientName.setDisable(true);
            this.patientPhone.setDisable(true);
            this.patientRegisterNo.setDisable(true);
            this.patientSurname.setDisable(true);
            this.patientDob.setDisable(true); 
            this.patientGender.setDisable(true);
        }

        public void makeFieldsEditable(){
            this.patientAddress.setDisable(false);
            this.patientComment.setDisable(false);
            this.patientEmail.setDisable(false);
            this.patientName.setDisable(false);
            this.patientPhone.setDisable(false);
            this.patientRegisterNo.setDisable(false);
            this.patientSurname.setDisable(false);
            this.patientDob.setDisable(false);
            this.patientGender.setDisable(false);
        }

        public void showAlert(String text) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Анхааруулга");
            alert.setHeaderText(null);
            alert.setContentText(text);
            alert.showAndWait();
        }

        public void setLoggedInUser(User loggedInUser) {
            this.loggedInUser = loggedInUser;
        }
    }
