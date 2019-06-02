package ModelClass;

public class InspectionTable {
    String date;
    String ins_id;

    public InspectionTable(String ins_id,String date){
        setIns_id(ins_id);
        setDate(date);
    }

    public String getIns_id() {
        return ins_id;
    }

    public String getDate() {
        return date;
    }

    public void setIns_id(String ins_id) {
        this.ins_id = ins_id;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
