package login;

import connectivity.ConnectionClass;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

public class User {

    private int id;
    private String lname; //required
    private String fname; //required
    private LocalDate dateOfBirth; //required
    private int position; //required
    private String email; //required
    private String password; //required
    private char gender; //required
    private String address;//required
    private String register_no; //required
    private int phonenumber; //required

    User(String Password, int position, String fname,String lname,String registerno,
         LocalDate birthdate,char gender,String Email,int pnumber,String address){

        this.fname = fname;
        this.lname= lname;
        this.register_no= registerno;
        this.dateOfBirth = birthdate;
        this.gender = gender;
        this.email= Email;
        this.phonenumber= pnumber;
        this.address = address;
        this.password = Password;
        this.position = position;

    }

    User(String Password, int position, String fname,String lname,String registerno,
         LocalDate birthdate,char gender,String Email,int pnumber,String address, int id){

        this.fname = fname;
        this.lname = lname;
        this.register_no = registerno;
        this.dateOfBirth = birthdate;
        this.gender = gender;
        this.email = Email;
        this.phonenumber = pnumber;
        this.address = address;
        this.password = Password;
        this.position = position;
        this.id = id;

    }

    User(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegister_no() {
        return register_no;
    }

    public void setRegister_no(String register_no) {
        this.register_no = register_no;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    private void insertUser() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String sql = "INSERT INTO users(lname,fname,dob,position,email,password,address,phonenumber,gender) " +
                    "VALUES ('"+this.lname+"','"+this.fname+"','"+this.dateOfBirth+"','"+this.position+
                    "','"+this.email+"','"+this.password+"','"+this.address+"','"+this.phonenumber+"','"+this.gender+"')";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.executeUpdate();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            this.setId(generatedKeys.getInt(1));
        }
    }

    private void updateUser() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String sql = "Update user set surname='"+this.lname+"',name='"+this.fname+"',dob='"+this.dateOfBirth+"',position = '"+this.position+
                "',email='"+this.email+"',password='"+this.password+"',address='"+this.address+"',phonenumber'"+this.phonenumber+
                "',gender'"+this.gender+"')";
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.executeUpdate();
    }

    public void getUserByEmail(String email) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        String qry = "Select * from user where email='"+email+"' limit 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(qry);
        while(resultSet.next()){
            this.setId(resultSet.getInt("id"));
            this.setFname(resultSet.getString("fname"));
            this.setLname(resultSet.getString("lname"));
            this.setDateOfBirth(resultSet.getDate("dob").toLocalDate());
            this.setPosition(resultSet.getInt("position"));
            this.setEmail(resultSet.getString("email"));
            this.setPassword(resultSet.getString("password"));
            this.setAddress(resultSet.getString("address"));
            this.setPhonenumber(resultSet.getInt("phonenumber"));
            this.setGender(resultSet.getString("gender").charAt(0));
            this.setRegister_no(resultSet.getString("register_no"));
        }
    }
}

