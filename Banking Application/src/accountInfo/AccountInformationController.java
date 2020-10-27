package accountInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import LoginScreen.LoginScreenController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;

public class AccountInformationController implements Initializable{

	public static String ac = LoginScreenController.acc;
	
	@FXML 
	private Text account_no;
	@FXML 
	private Text gender;
	@FXML 
	private Text account_type;
	@FXML 
	private Text religion;
	@FXML 
	private Label balance;
	@FXML
	private Pane dashboard_main;
	
	
	
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
				balance.setText(rs.getString("Balance"));
				account_no.setText(rs.getString("AccountNo"));
				gender.setText(rs.getString("Gender"));
				account_type.setText(rs.getString("AccountType"));
				religion.setText(rs.getString("Religion"));
			}
			else {
				Alert a = new Alert(AlertType.ERROR);
				
				a.setTitle("ERROR");
				a.setHeaderText("ERROR in Login.");
				a.setResizable(true);
				a.setContentText("Your account is not created, There is some Error. TRY AGAIN!!!");
				a.showAndWait();
			}
			
		}catch(Exception e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("ERROR");
			a.setHeaderText("ERROR in request");
			a.setResizable(true);
			a.setContentText(" There is some Error. TRY AGAIN!!!"+e.getMessage());
			a.showAndWait();
		}
		
	}
	
	@FXML
	public void withdraw(MouseEvent event) throws IOException{
		Parent fxml = FXMLLoader.load(getClass().getResource("/withdraw/WithdrawAmount.fxml"));
		dashboard_main.getChildren().removeAll();
		dashboard_main.getChildren().addAll(fxml);
	}
	@FXML
	public void deposit(MouseEvent event) throws IOException{
		Parent fxml = FXMLLoader.load(getClass().getResource("/deposit/DepositAmount.fxml"));
		dashboard_main.getChildren().removeAll();
		dashboard_main.getChildren().addAll(fxml);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setInfo();
		
	}

}
