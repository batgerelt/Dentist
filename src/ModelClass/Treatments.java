package ModelClass;

import javafx.beans.property.SimpleStringProperty;

public class Treatments {
    private SimpleStringProperty ner;

    public Treatments(String tName) {
        this.ner = new SimpleStringProperty(tName);
    }

    public String getNer() {
        return ner.get();
    }

    public void setNer(String tName) {
        this.ner.set(tName);
    }
}
