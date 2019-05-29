package payment;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
public class MainWindowController  {
    @FXML
    private TextField ID;

    @FXML
    private Button search;

    @FXML
    private TextField dugaar;

    @FXML
    private TextField name;

    @FXML
    private TableColumn<userDetials, String> OnoshN;

    @FXML
    private TableColumn<userDetials, String> tolborMedeelel;
    @FXML
    private TableView<userDetials> tableUser;

    @FXML
    private TextField uilT;

    @FXML
    private TextField EMD;

    @FXML
    private TextField Tolbor;

    @FXML
    private TextField TolsonDun;

    @FXML
    private TextField hariult;
    @FXML
    private DatePicker start_date;

    @FXML
    private DatePicker end_date;
    
    double too1=0;
    @FXML
    
    void NiitTolson(MouseEvent event) {
    	String dunn= TolsonDun.getText();
    	if(dunn.equals(""))
    	{
    		hariult.setText("0.0");
    	}
    	else
    	{
    		double too = Double.parseDouble(TolsonDun.getText());
    		too1 = too - Double.parseDouble(Tolbor.getText());
    		hariult.setText(Double.toString(too1));
    	}
    	
    }
        @FXML void Search(ActionEvent event) {
        	double st = 0;
        	hariult.setText("");
        	TolsonDun.setText("");
        	 String sttt="";
        	    String sql="";
        	ObservableList<userDetials> oblist =  FXCollections.observableArrayList();
        	
        	try {
        		ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
            	 sql = "select t_name , price from treatment treat where treat.id in ( select t_id from inspection where p_id = ( select id from patient where RegisterNo = '"+ID.getText()+"' ) )";
            	problem();
				ResultSet rs = connection.createStatement().executeQuery(sql);
				while(rs.next()) {
					oblist.add(new userDetials(rs.getString("t_name"),rs.getString("price")));
					sttt = rs.getString("price");
					st+=Double.parseDouble(sttt);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	OnoshN.setCellValueFactory(new PropertyValueFactory<>("onoshN"));
        	tolborMedeelel.setCellValueFactory(new PropertyValueFactory<>("tolborMedeelel"));
        	if(sql != null) 
        	{
        	tableUser.setItems(null);
        	tableUser.setItems(oblist);
        	}
        	else 
        		tableUser.setItems(null);
        	String tot = Double.toString(st);
        	Tolbor.setText(tot);
        }
        void problem() {
        	try {
        		ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                
            	String sql = "Select PNumber,Fname from patient where RegisterNo = '"+ID.getText()+"'";
				ResultSet rs = connection.createStatement().executeQuery(sql);
				name.setText("");
				dugaar.setText("");
					while(rs.next()) {	
						String firstName = rs.getString("Fname");
						name.setText(firstName);	
						String phone = rs.getString("PNumber");
						dugaar.setText(phone);
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        @FXML
        void handleOnKeyPressed(KeyEvent event) throws JRException {
        	switch(event.getCode())
        	{
        		case F12:
        		case F11:
        			ConnectionClass connectionClass = new ConnectionClass();
                    Connection connection = connectionClass.getConnection();
                    
        			JasperReport report = JasperCompileManager.compileReport("/dentist/src/Blank_A4.jrxml");
        	          JasperPrint jp = JasperFillManager.fillReport(report, null, connection);
        	          JasperViewer jv = new JasperViewer(jp,false);
        	          jv.setVisible(true);
        			break;
        		case F10:
        			
        		defualt: 
        			break;
        	}
        }
}
