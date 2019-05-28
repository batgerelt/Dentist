package access;

import connectivity.ConnectionClass;

import java.sql.*;
import java.time.LocalDate;

public class Access {
    String name;
    Boolean ispatient;
    Boolean isCalendar;
    Boolean isInspection;
    Boolean isTreatment;
    Boolean isPayment;
    Boolean isAccess;
    int id;

    Access(String name, Boolean ispatient, Boolean isCalendar, Boolean isInspection, Boolean isTreatment,
               Boolean isPayment, Boolean isAccess, int id){
        this.name = name;
        this.ispatient = ispatient;
        this.isCalendar= isCalendar;
        this.isInspection = isInspection;
        this.isTreatment = isTreatment;
        this.isPayment = isPayment;
        this.isAccess = isAccess;
        this.id = id;
    }

    public String getName() {
        if (name == null || name.isEmpty())
            return null;
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIspatient() {
        return ispatient;
    }

    public void setIspatient(Boolean ispatient) {
        this.ispatient = ispatient;
    }

    public Boolean getCalendar() {
        return isCalendar;
    }

    public void setCalendar(Boolean calendar) {
        isCalendar = calendar;
    }

    public Boolean getInspection() {
        return isInspection;
    }

    public void setInspection(Boolean inspection) {
        isInspection = inspection;
    }

    public Boolean getTreatment() {
        return isTreatment;
    }

    public void setTreatment(Boolean treatment) {
        isTreatment = treatment;
    }

    public Boolean getPayment() {
        return isPayment;
    }

    public void setPayment(Boolean payment) {
        isPayment = payment;
    }

    public Boolean getAccess() {
        return isAccess;
    }

    public void setAccess(Boolean access) {
        isAccess = access;
    }

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        id = Id;
    }

    Access(String name, Boolean ispatient, Boolean isCalendar, Boolean isInspection, Boolean isTreatment,
           Boolean isPayment, Boolean isAccess){
        this.name = name;
        this.ispatient = ispatient;
        this.isCalendar= isCalendar;
        this.isInspection = isInspection;
        this.isTreatment = isTreatment;
        this.isPayment = isPayment;
        this.isAccess = isAccess;
    }

    Access(){

    }

    public void createAccess() throws SQLException {
        try {

            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();

            String sql = "INSERT INTO Access(name, ispatient, isCalendar, isInspection, isTreatment, isPayment, isAccess" +
                    ")VALUES('"+this.getName()+"',"+this.getIspatient()+","+this.getCalendar()+","+
                    this.getInspection()+","+this.getTreatment()+","+this.getPayment()+","+
                    this.getAccess() +")";
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

    public void updateAccess() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String sql = "Update Access set name='" +this.getName()+"', ispatient="+this.getIspatient()+", isCalendar="+this.getCalendar()+
                ", isInspection="+this.getInspection()+",isTreatment="+this.getTreatment()
                +", isPayment="+this.getPayment()+
                ",isAccess="+this.getAccess()+" where id="+this.getId();
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        preparedStmt.executeUpdate();
    }

    public void deleteAccess() throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String qry = "DELETE FROM access WHERE id =" + this.getId();
        PreparedStatement preparedStmt = connection.prepareStatement(qry);
        preparedStmt.execute();
        connection.close();
    }

    public void getAccessByID(int id) throws SQLException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        String qry = "Select * from user where id="+id+" limit 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(qry);
        while(resultSet.next()){
            this.setId(resultSet.getInt("id"));
            this.setName(resultSet.getString("name"));
            this.setIspatient(resultSet.getBoolean("ispatient"));
            this.setCalendar(resultSet.getBoolean("isCalendar"));
            this.setInspection(resultSet.getBoolean("isInspection"));
            this.setTreatment(resultSet.getBoolean("isTreatment"));
            this.setPayment(resultSet.getBoolean("isPayment"));
            this.setAccess(resultSet.getBoolean("isAccess"));
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
