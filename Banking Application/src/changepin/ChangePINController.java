package changepin;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import LoginScreen.LoginScreenController;
import dashboard.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

public class ChangePINController implements Initializable{

	@FXML
	private PasswordField oldpin;
	@FXML
	private PasswordField newpin;
	@FXML
	private PasswordField confirmpin;
	
	
	
	DashboardController d = new DashboardController(); 
	public static String ac = LoginScreenController.acc;
	private ResultSet rs = null;
	
	public void changePin(MouseEvent event){
		
		Connection con = null;
		PreparedStatement ps = null;
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, ac);
			ps.setString(2, oldpin.getText());
			
			if(rs.next()) {
				
				if(newpin.getText().equals(confirmpin.getText())) {
					String sql1 = "UPDATE userdata SET PIN='"+newpin.getText()+"'WHERE AccountNo = '"+ac+"'";
					ps = con.prepareStatement(sql1);
					ps.execute();
					
					Alert a = new Alert(AlertType.INFORMATION);
						
					a.setTitle("PIN Change");
					a.setHeaderText("PIN Changed sucessfully");
					a.setResizable(true);
					a.setContentText("Your PIN is changed please login again!!!");
					a.showAndWait();
					
					oldpin.setText("");
					newpin.setText("");
					confirmpin.setText("");
						
					d.logout(event);
					}
				
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
			a.setHeaderText("ERROR in chanfing PIN.");
			a.setResizable(true);
			a.setContentText(" There is some Error. TRY AGAIN!!!"+e.getMessage());
			a.showAndWait();
		}
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

}
