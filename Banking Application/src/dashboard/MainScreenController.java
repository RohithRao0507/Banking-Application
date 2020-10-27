package dashboard;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import LoginScreen.LoginScreenController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class MainScreenController implements Initializable{
	
	
	public static String ac = LoginScreenController.acc;
	@FXML
	private Label name;
	@FXML
	private Label body;
	
	public void setInfo(){
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			String sql = "SELECT * FROM userdata WHERE AccountNo=?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, ac);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				name.setText(rs.getString("Name"));
			}
			
			else {
				Alert a = new Alert(AlertType.ERROR);
				
				a.setTitle("ERROR");
				a.setHeaderText("There is some sort of error");
				a.setResizable(true);
				a.setContentText("Your account is not created, There is some Error. TRY AGAIN!!!");
				a.showAndWait();
			}
			
		}catch(Exception e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("ERROR");
			a.setHeaderText("ERROR in Login.");
			a.setResizable(true);
			a.setContentText(" There is some Error. TRY AGAIN!!!"+e.getMessage());
			a.showAndWait();
		}
		
	}
	
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		body.setText("Rao's Bank is an Indian multinational, public sector banking \nand financial services statutory body headquartered in \nHyderabad, Telangan. RBC is ranked 236th in the \nFortune Global 500 list of the world's \nbiggest corporations of 2019");
		setInfo();
	}

}
