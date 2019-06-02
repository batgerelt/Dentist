package schedule;


import javafx.beans.property.SimpleStringProperty;

public class doctorSchedule {
    private SimpleStringProperty columnMonday;
    private SimpleStringProperty columnTuesday;
    private SimpleStringProperty columnWednesday;
    private SimpleStringProperty columnThursday;
    private SimpleStringProperty columnFriday;
    private SimpleStringProperty columnSaturday;
    private SimpleStringProperty columnSunday;

    public doctorSchedule(String columnMonday, String columnTuesday, String columnWednesday, String columnThursday, String columnFriday, String columnSaturday, String columnSunday) {
        this.columnMonday = new SimpleStringProperty(columnMonday);
        this.columnTuesday  = new SimpleStringProperty(columnTuesday);
        this.columnWednesday = new SimpleStringProperty(columnWednesday);
        this.columnThursday = new SimpleStringProperty(columnThursday);
        this.columnFriday = new SimpleStringProperty(columnFriday);
        this.columnSaturday = new SimpleStringProperty(columnSaturday);
        this.columnSunday = new SimpleStringProperty(columnSunday);
    }

    public String getColumnMonday() {
        return columnMonday.get();
    }

    public void setColumnMonday(String columnMonday) {
        this.columnMonday = new SimpleStringProperty(columnMonday);
    }

    public String getColumnTuesday() {
        return columnTuesday.get();
    }

    public void setColumnTuesday(String columnTuesday) {
        this.columnTuesday = new SimpleStringProperty(columnTuesday);
    }

    public String getColumnWednesday() {
        return columnWednesday.get();
    }

    public void setColumnWednesday(String columnWednesday) {
        this.columnWednesday = new SimpleStringProperty(columnWednesday);
    }

    public String getColumnThursday() {
        return columnThursday.get();
    }

    public void setColumnThursday(String columnThursday) {
        this.columnThursday = new SimpleStringProperty(columnThursday);
    }

    public String getColumnFriday() {
        return columnFriday.get();
    }

    public void setColumnFriday(String columnFriday) {
        this.columnFriday = new SimpleStringProperty(columnFriday);
    }

    public String getColumnSaturday() {
        return columnSaturday.get();
    }

    public void setColumnSaturday(String columnSaturday) {
        this.columnSaturday = new SimpleStringProperty(columnSaturday);
    }

    public String getColumnSunday() {
        return columnSunday.get();
    }

    public void setColumnSunday(String columnSunday) {
        this.columnSunday = new SimpleStringProperty(columnSunday);
    }
}
