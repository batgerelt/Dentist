package report;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;


import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import connectivity.ConnectionClass;

public class ReportController {
	 @FXML
	    private DatePicker start_date;

	    @FXML
	    private DatePicker end_date;

	    @FXML
	    private TextField Doc_id;
	    
    @FXML
    void ReceptionClk(MouseEvent event) throws ClassNotFoundException, SQLException, JRException {
    	ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
    	 HashMap param = new HashMap();
    	 JasperDesign jd = JRXmlLoader.load(new File("C:\\Users\\Batgerelt\\Documents\\GitHub\\Dentist\\src\\report\\Report.jrxml"));
    	 Date date_end = Date.from(end_date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    	 Date date_start = Date.from(start_date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
          param.put("END_DATE",date_end);
          param.put("START_DATE",date_start);
          JasperReport jr =  JasperCompileManager.compileReport(jd);
          JasperPrint jp = JasperFillManager.fillReport(jr, param, connection);
          JasperViewer jv = new JasperViewer(jp,false);
          jv.setVisible(true);
    }

    @FXML
    void doctorClk(MouseEvent event) throws ClassNotFoundException, SQLException, JRException {
    	ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
    	
    	 HashMap param = new HashMap();
    	 Date date_end = Date.from(end_date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    	 Date date_start = Date.from(start_date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    	
          param.put("END_DATE",date_end);
          param.put("START_DATE",date_start);
          param.put("doctorID", Doc_id.getText());
          
    	 JasperDesign jd = JRXmlLoader.load(new File("C:\\Users\\Batgerelt\\Documents\\GitHub\\Dentist\\src\\report\\DoctorReport.jrxml"));
        // param.put("name", );
          //param.put();
          JasperReport jr =  JasperCompileManager.compileReport(jd);
          JasperPrint jp = JasperFillManager.fillReport(jr, param, connection);
          JasperViewer jv = new JasperViewer(jp,false);
          jv.setVisible(true);
    }

}
