package patient;

import connectivity.ConnectionClass;
import login.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

public class Patient {
    String RegisterNo;
    String Lname;
    String Fname;
    char Gender;
    LocalDate BirthDate;
    String Address;
    String Pnumber;
    String Email;
    String comment;
    int id;
    Patient(String fname,String lname,String registerno,
            LocalDate birthdate,char gender,String Email,String pnumber,String address, String Comment,int id){
        this.Fname = fname;
        this.Lname= lname;
        this.RegisterNo= registerno;
        this.BirthDate= birthdate;
        this.Gender= gender;
        this.Email= Email;
        this.Pnumber= pnumber;
        this.Address= address;
        this.comment= Comment;
        this.id = id;
    }

    Patient(String fname,String lname,String registerno,
            LocalDate birthdate,char gender,String Email,String pnumber,String address, String Comment){
        this.Fname = fname;
        this.Lname= lname;
        this.RegisterNo= registerno;
        this.BirthDate= birthdate;
        this.Gender= gender;
        this.Email= Email;
        this.Pnumber= pnumber;
        this.Address= address;
        this.comment= Comment;
    }

    Patient(){

    }

    public String getFname() {
        if (Fname == null || Fname.isEmpty())
            return null;
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getComment() {
        if(comment == null || comment.isEmpty())
            return null;
        return comment;
    }

    public void setComment(String Comment) {
        comment = Comment;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getRegisterNo() {
        return RegisterNo;
    }

    public void setRegisterNo(String registerNo) {
        RegisterNo = registerNo;
    }

    public LocalDate getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        BirthDate = birthDate;
    }

    public char getGender() {
        return Gender;
    }

    public void setGender(char gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPnumber() {
        return Pnumber;
    }

    public void setPnumber(String pnumber) {
        Pnumber = pnumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        id = Id;
    }

    public void createPatient() throws SQLException {
        try {

            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();

            String sql = "INSERT INTO patient(RegisterNo, Lname, Fname, Gender, BirthDate, Address, Pnumber, Email,  comment" +
                    ")VALUES('"+this.getRegisterNo()+"','"+this.getLname()+"','"+this.getFname()+"','"+
                    this.getGender()+"','"+this.getBirthDate()+"','"+this.getAddress()+"','"+this.getPnumber()+"','"+
                    this.getEmail()+"','"+this.getComment()+"')";
            sql = sql.replace("'null'","null");
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.setId(generatedKeys.getInt(1));
            }
        }catch (SQLException e){
            System.out.println(e);
        }

    }

    public void updatePatient() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String sql = "Update patient set Lname='" +this.getLname()+"', Fname='"+this.getFname()+"', BirthDate='"+this.getBirthDate()+
                "', Address='"+this.getAddress()+"',Email='"+this.getEmail()+"',Pnumber='"+this.getPnumber()+"', Gender='"+this.getGender()+
                "',comment='"+this.getComment()+"', RegisterNo='"+this.getRegisterNo()+"' where id="+this.getId();
        sql = sql.replace("'null'","null");
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.executeUpdate();
    }


}
