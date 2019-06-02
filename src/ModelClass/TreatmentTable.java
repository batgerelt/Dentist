package ModelClass;

public class TreatmentTable {
    String date;
    String teeth;
    String treatment;
    String state;
    public TreatmentTable(String date,String teeth,String treatment,String state){
        setDate(date);
        setTeeth(teeth);
        setState(state);
        setTreatment(treatment);
    }

    public String getDate(){
        return this.date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getTeeth(){
        return this.teeth;
    }

    public void setTeeth(String teeth){
        this.teeth = teeth;
    }

    public String getTreatment(){
        return this.treatment;
    }

    public void setTreatment(String treatment){
        this.treatment = treatment;
    }

    public String getState(){
        return this.state;
    }

    public void setState(String state){
        System.out.println("state "+state);
        if(state.contentEquals("1"))
        this.state = "Дууссан";
        else{
            this.state = "Дуусаагүй";
        }
    }

}
