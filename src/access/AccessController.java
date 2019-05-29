package access;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import login.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;


public class AccessController {

    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }

    User loggedInUser;

    User selectedUser;
    ObservableList<User> UserList = FXCollections.observableArrayList();

    Access selectedAccess;
    ObservableList<Access> AccessList = FXCollections.observableArrayList();

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void displayAccesses() throws SQLException {
        selectedAccess = null;
        AccessList.clear();
        computePos();
        PosTable.setItems(AccessList);
    }

    public void otherBtnsDisable(){
        userSearchBtn.setDisable(true);
        userViewBtn.setDisable(true);
        userChangePwdBtn.setDisable(true);
        userEditBtn.setDisable(true);
        posViewBtn.setDisable(true);
        posCreateBtn.setDisable(true);
        posEditBtn.setDisable(true);
        userSearchField.setDisable(true);
        userCreateBtn.setDisable(true);
        posDeleteBtn.setDisable(true);
        userDeleteBtn.setDisable(true);
    }

    public void otherBtnsEnable(){
        userSearchBtn.setDisable(false);
        userViewBtn.setDisable(false);
        userChangePwdBtn.setDisable(false);
        userEditBtn.setDisable(false);
        posViewBtn.setDisable(false);
        posCreateBtn.setDisable(false);
        posEditBtn.setDisable(false);
        userSearchField.setDisable(false);
        userCreateBtn.setDisable(false);
        posDeleteBtn.setDisable(false);
        userDeleteBtn.setDisable(false);
    }

    public boolean checkUserFields() throws SQLException {
        if(userSurname.getText().isEmpty() || userName.getText().isEmpty() || userGender.getValue() == null ||
        userDob.getValue() == null || userAddress.getText().isEmpty() || userPhone.getText().isEmpty() ||
        userEmail.getText().isEmpty() || userPosition.getValue() == null || userRegisterNo.getText().isEmpty()){
            showAlert("Бүх талбарыг бөглөнө үү!");
            return false;
        }
        if(!isInteger(userPhone.getText())){
            showAlert("Утасны дугаарыг зөв бөглөнө үү!");
            return false;
        }
        String where = "";
        if(selectedUser != null){
            where = " AND id!="+selectedUser.getId();
        }
        String qry = "SELECT * FROM user where (email='"+ userEmail.getText()+
                "' OR register_no='"+ userRegisterNo.getText()+"')" + where;
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(qry);
        if(resultSet.next()){
            showAlert("Регистерийн дугаар эсвэл имэйл давхцаж байна. Шалгана уу");
            return false;
        }
        return true;
    }

    public void employeeBtnInvi(){
        userBackBtn.setVisible(false);
        userSaveBtn.setVisible(false);
        userRegisterNo.setDisable(true);
        userSurname.setDisable(true);
        userName.setDisable(true);
        userGender.setDisable(true);
        userDob.setDisable(true);
        userAddress.setDisable(true);
        userPhone.setDisable(true);
        userEmail.setDisable(true);
        userPosition.setDisable(true);
    }

    public void accessBtnInvi(){
        posBackBtn.setVisible(false);
        posSaveBtn.setVisible(false);
        PosName.setDisable(true);
        isPatient.setDisable(true);
        isSchedule.setDisable(true);
        isInspection.setDisable(true);
        isTreatment.setDisable(true);
        isPayment.setDisable(true);
        isAccess.setDisable(true);
        isReport.setDisable(true);
    }

    public void employeeBtnVi(){
        userBackBtn.setVisible(true);
        userSaveBtn.setVisible(true);
        userRegisterNo.setDisable(false);
        userSurname.setDisable(false);
        userName.setDisable(false);
        userGender.setDisable(false);
        userDob.setDisable(false);
        userAddress.setDisable(false);
        userPhone.setDisable(false);
        userEmail.setDisable(false);
        userPosition.setDisable(false);
    }

    public void accessBtnVi(){
        posBackBtn.setVisible(true);
        posSaveBtn.setVisible(true);
        PosName.setDisable(false);
        isPatient.setDisable(false);
        isSchedule.setDisable(false);
        isInspection.setDisable(false);
        isTreatment.setDisable(false);
        isPayment.setDisable(false);
        isAccess.setDisable(false);
        isReport.setDisable(false);
        
    }

    public void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Анхааруулга");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void clearAccessValues(){
        PosName.clear();
        isPatient.setSelected(false);
        isSchedule.setSelected(false);
        isInspection.setSelected(false);
        isTreatment.setSelected(false);
        isPayment.setSelected(false);
        isAccess.setSelected(false);
        isReport.setSelected(false);
    }

    public void clearUserValues(){
        userRegisterNo.clear();
        userSurname.clear();
        userName.clear();
        userDob.setValue(null);
        userGender.setValue(null);
        userAddress.clear();
        userPhone.clear();
        userEmail.clear();
        userPosition.setValue(null);
    }

    @FXML
    public void initialize() throws SQLException {
        employeeBtnInvi();
        accessBtnInvi();
        displayAccesses();
        ActionEvent event = new ActionEvent();
        searchUser(event);
    }
    @FXML
    private Button userSearchBtn;
    @FXML
    private CheckBox isPatient;
    @FXML
    private TableView<Access> PosTable;
    @FXML
    private TableColumn<?, ?> PosListName;
    @FXML
    private TextField userPhone;
    @FXML
    private TextField PosName;
    @FXML
    private Button userCreateBtn;
    @FXML
    private DatePicker userDob;
    @FXML
    private ChoiceBox<Access> userPosition;
    @FXML
    private CheckBox isTreatment;
    @FXML
    private TableColumn<?, ?> userListRegister;
    @FXML
    private TextField userEmail;
    @FXML
    private Button posEditBtn;
    @FXML
    private Button posDeleteBtn;
    @FXML
    private Button userDeleteBtn;
    @FXML
    private TableColumn<?, ?> userListPosition;
    @FXML
    private Button userEditBtn;
    @FXML
    private CheckBox isSchedule;
    @FXML
    private TableColumn<?, ?> userListFname;
    @FXML
    private TextField userSurname;
    @FXML
    private TableColumn<?, ?> userListLname;
    @FXML
    private Button userBackBtn;
    @FXML
    private TableView<User> UserTable;
    @FXML
    private CheckBox isInspection;
    @FXML
    private TextField userRegisterNo;
    @FXML
    private TextField userName;
    @FXML
    private TextField userAddress;
    @FXML
    private TextField userSearchField;
    @FXML
    private CheckBox isPayment;
    @FXML
    private Button posSaveBtn;
    @FXML
    private Button userSaveBtn;
    @FXML
    private Button posCreateBtn;
    @FXML
    private Button userViewBtn;
    @FXML
    private ChoiceBox<String> userGender;
    @FXML
    private CheckBox isAccess;
    @FXML
    private CheckBox isReport;
    @FXML
    private Button posBackBtn;
    @FXML
    private Button posViewBtn;
    @FXML
    private Button userChangePwdBtn;

    @FXML
    void computePos() throws SQLException {
        AccessList.clear();
        String qry = "SELECT * FROM access";
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(qry);
        while(resultSet.next()){
            Access acs = new Access(resultSet.getString("name"),resultSet.getBoolean("isPatient"),
                    resultSet.getBoolean("isSchedule"), resultSet.getBoolean("isInspection"),
                    resultSet.getBoolean("isTreatment"),resultSet.getBoolean("isPayment"),
                    resultSet.getBoolean("isAccess"), resultSet.getBoolean("isReport"), resultSet.getInt("id"));
            AccessList.add(acs);
            PosListName.setCellValueFactory(new PropertyValueFactory<>("name"));
        }
        userPosition.setItems(AccessList);
    }


    @FXML
    void searchUser(ActionEvent event) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        String qry = "";
        if(userSearchField.getText().isEmpty()){
            qry = "SELECT * FROM user";
        }
        else{
             qry = "SELECT * FROM user WHERE register_no like '%" + userSearchField.getText() + "%' or lname like '%" +
                    userSearchField.getText() + "%' or fname like '%" + userSearchField.getText() +
                    "%' or phonenumber like '%" + userSearchField.getText() +
                    "%' or email like '%" + userSearchField.getText() + "%'";
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(qry);
        if (!resultSet.isBeforeFirst() ) {
            showAlert("Ажилтан олдсонгүй");
        }
        else {
            selectedUser = null;
            UserList.clear();
            clearUserValues();
            while (resultSet.next()) {
                User pt = new User(resultSet.getString("password"),resultSet.getInt("position"),
                        resultSet.getString("fname"),resultSet.getString("lname"),
                        resultSet.getString("register_no"), resultSet.getDate("dob").toLocalDate(),
                        resultSet.getString("gender").toCharArray()[0], resultSet.getString("email"),
                        resultSet.getInt("phonenumber"), resultSet.getString("address")
                        ,resultSet.getInt("id"));
                UserList.add(pt);
            }
            userListLname.setCellValueFactory(new PropertyValueFactory<>("lname"));
            userListFname.setCellValueFactory(new PropertyValueFactory<>("fname"));
            userListRegister.setCellValueFactory(new PropertyValueFactory<>("register_no"));
            userListPosition.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));

            UserTable.setItems(UserList) ;
        }
    }
    @FXML
    void viewUser(ActionEvent event) {
        if(UserTable.getSelectionModel().getSelectedItem() == null ){
            if(!((Button)event.getSource()).getText().equals("Буцах"))
                showAlert("Ажилтан сонгоогүй байна!");
        }
        else {
            selectedUser = UserTable.getSelectionModel().getSelectedItem();
            employeeBtnInvi();
            userRegisterNo.setText(selectedUser.getRegister_no());
            userSurname.setText(selectedUser.getLname());
            userName.setText(selectedUser.getFname());
            if(selectedUser.getGender() == 'm')
                userGender.setValue(userGender.getItems().get(0));
            else
                userGender.setValue(userGender.getItems().get(1));
            userDob.setValue(selectedUser.getDateOfBirth());
            userAddress.setText(selectedUser.getAddress());
            userPhone.setText(String.valueOf(selectedUser.getPhonenumber()));
            userEmail.setText(selectedUser.getEmail());
            for(int i=0; i<userPosition.getItems().size();i++){
                if(selectedUser.getPosition() == userPosition.getItems().get(i).getId()){
                    userPosition.setValue(userPosition.getItems().get(i));
                }
            }
        }
    }

    @FXML
    void createUser(ActionEvent event) {
        selectedUser = null;
        otherBtnsDisable();
        employeeBtnVi();
        clearUserValues();
        userRegisterNo.requestFocus();

    }

    @FXML
    void viewPos(ActionEvent event) {
        if(PosTable.getSelectionModel().getSelectedItem() == null)
            showAlert("Албан тушаал сонгоогүй байна!");
        else{
            accessBtnInvi();
            selectedAccess = PosTable.getSelectionModel().getSelectedItem();
            PosName.setText(selectedAccess.getName());
            isPatient.setSelected(selectedAccess.getIspatient());
            isSchedule.setSelected(selectedAccess.getCalendar());
            isInspection.setSelected(selectedAccess.getInspection());
            isTreatment.setSelected(selectedAccess.getTreatment());
            isPayment.setSelected(selectedAccess.getPayment());
            isAccess.setSelected(selectedAccess.getAccess());
            isReport.setSelected(selectedAccess.getAccess());
        }
    }

    @FXML
    void createPos(ActionEvent event) {
        selectedAccess = null;
        accessBtnVi();
        otherBtnsDisable();
        clearAccessValues();
    }

    @FXML
    void editUser(ActionEvent event) {
        if(selectedUser == null){
            showAlert("Дэлгэрэнгүй товч дарна уу!");
            return;
        }
        otherBtnsDisable();
        employeeBtnVi();
    }

    @FXML
    void backUser(ActionEvent event) {
        employeeBtnInvi();
        otherBtnsEnable();
        viewUser(event);
    }

    @FXML
    void userSave(ActionEvent event) throws SQLException {
        if(!checkUserFields()){
            return;
        }
        employeeBtnInvi();
        otherBtnsEnable();
        User newUser = new User(userRegisterNo.getText(), userPosition.getValue().getId(),
                userName.getText(), userSurname.getText(), userRegisterNo.getText(),
                userDob.getValue(), (userGender.getValue().equals("Эр")) ? 'm' : 'f',
                userEmail.getText(), Integer.parseInt(userPhone.getText()),
                userAddress.getText());
        if(selectedUser == null){
            try {
                newUser.insertUser();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(e.getMessage());
                return;
            }
            selectedUser = newUser;
        }
        else{
            newUser.setId(selectedUser.getId());
            selectedUser = newUser;
            selectedUser.updateUser();
        }
        UserList.clear();
        UserList.add(selectedUser);
        UserTable.setItems(UserList) ;
    }

    @FXML
    void changeUserPwd(ActionEvent event) throws SQLException {
        if(selectedUser == null){
            showAlert("Дэлгэрэнгүй товч дарна уу!");
            return;
        }
        PasswordDialog pd = new PasswordDialog(selectedUser.getFname());
        Optional<String> result = pd.showAndWait();
        if(result.isPresent()){
            selectedUser.setPassword(result.get());
            selectedUser.updatePassword();
            showAlert("Амжилттай");
        }
    }

    @FXML
    void editPosition(ActionEvent event) {
        if(selectedAccess==null){
            showAlert("Албан тушаал сонгоогүй байна!");
            return;
        }
        accessBtnVi();
        otherBtnsDisable();
    }
    @FXML
    void deleteUser(ActionEvent event) throws SQLException {
        if(selectedUser == null){
            showAlert("Дэлгэрэнгүй товч дарна уу!");
            return;
        }
        else {
            ButtonType yes = new ButtonType("Тийм", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("Үгүй", ButtonBar.ButtonData.NO);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",yes,no);
            alert.setTitle("");
            alert.setHeaderText(selectedUser.getFname() + " Ажилтанг устгахдаа итгэлтэй байна уу?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.orElse(no) == yes){
                selectedUser.deleteUser();
                UserList.remove(selectedUser);
                selectedUser = null;
                UserTable.setItems(UserList);
                clearUserValues();
            }
        }
    }

    @FXML
    void deletePosition(ActionEvent event) throws SQLException {
        if(selectedAccess == null)
            showAlert("Албан тушаалын дэлгэрэнгүй товч дарна уу!");
        else{
            ButtonType yes = new ButtonType("Тийм", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("Үгүй", ButtonBar.ButtonData.NO);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",yes,no);
            alert.setTitle("");
            alert.setHeaderText(selectedAccess.getName() + " албан тушаалыг устгахдаа итгэлтэй байна уу?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.orElse(no) == yes){
                selectedAccess.deleteAccess();
                displayAccesses();
                clearAccessValues();
            }
        }
    }

    @FXML
    void backPosition(ActionEvent event) {
        accessBtnInvi();
        otherBtnsEnable();
        viewPos(event);

    }

    public boolean checkAccessName(String name) throws SQLException {
        String where = "";
        if(selectedAccess != null){
            where = " AND id != " + selectedAccess.getId()+"";
        }
        String qry = "SELECT * FROM access WHERE name='" + name +"' " + where;
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(qry);
        if(resultSet.next()){
            showAlert("Албан тушаалын нэр давхцаж байна\n Өөр нэр оруулна уу");
            return false;
        }
        return true;
    }

    @FXML
    void PositionSave(ActionEvent event) throws SQLException {
        if(PosName.getText().isEmpty()){
            showAlert("Албан тушаалын нэрийг оруулна уу!");
            return;
        }
        else if(!checkAccessName(PosName.getText())){
            return;
        }
        else if(selectedAccess==null){
            Access access = new Access(PosName.getText(), isPatient.isSelected(), isSchedule.isSelected(),
                    isInspection.isSelected(), isTreatment.isSelected(), isPayment.isSelected(), isAccess.isSelected(), isReport.isSelected());
            access.createAccess();
            selectedAccess = access;
        }
        else{
            selectedAccess.setName(PosName.getText());
            selectedAccess.setIspatient(isPatient.isSelected());
            selectedAccess.setCalendar(isSchedule.isSelected());
            selectedAccess.setInspection(isInspection.isSelected());
            selectedAccess.setTreatment(isTreatment.isSelected());
            selectedAccess.setPayment(isPayment.isSelected());
            selectedAccess.setAccess(isAccess.isSelected());
            selectedAccess.updateAccess();
        }
        clearAccessValues();
        employeeBtnInvi();
        accessBtnInvi();
        displayAccesses();
        otherBtnsEnable();
        if(selectedUser != null){
            viewUser(event);
        }
    }

}
