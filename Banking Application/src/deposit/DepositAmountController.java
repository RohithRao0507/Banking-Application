package deposit;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import LoginScreen.LoginScreenController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class DepositAmountController implements Initializable{
	
	public static String ac = LoginScreenController.acc;
	@FXML
	private Label account_no;
	@FXML
	private Label balance;
	@FXML 
	private TextField amt_field;
	@FXML
	private PasswordField pin_field;
	
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH);
	int day = cal.get(Calendar.DAY_OF_MONTH);
	int hour = cal.get(Calendar.HOUR);
	int minutes = cal.get(Calendar.MINUTE);
	int seconds = cal.get(Calendar.SECOND);
	int daynight = cal.get(Calendar.AM_PM);
	
	DateFormat dateformat = new SimpleDateFormat("yyyy/mm/dd");
	Date d = new Date();
	String date= dateformat.format(d);
	
	LocalTime localTime = LocalTime.now();
	DateTimeFormatter dt =  DateTimeFormatter.ofPattern("hh:mm:ss a");
	String time= localTime.format(dt);
	
	
	
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
				account_no.setText(rs.getString("AccountNo"));
				balance.setText(rs.getString("Balance"));
			}
			
			else {
				Alert a = new Alert(AlertType.ERROR);
				
				a.setTitle("Invalid PIN");
				a.setHeaderText("You Entered Wrong PIN");
				a.setResizable(true);
				a.setContentText("Please enter the correct pin. TRY AGAIN!!!");
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
	public void depositButton(){
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","");
			String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, ac);
			ps.setString(2, pin_field.getText());
			rs = ps.executeQuery();
			
			if(rs.next()) {
				int da = Integer.parseInt(amt_field.getText());
				int ta = Integer.parseInt(balance.getText());
				
					int total = ta+da;
					String sql1 = "UPDATE userdata SET Balance='"+total+"'WHERE AccountNo = '"+ac+"'";
					ps = con.prepareStatement(sql1);
					ps.execute();
					
					String sql2 ="INSERT INTO deposit(AccountNo, DepositAmount, NewAmount, Date, Time) VALUES (?,?,?,?,?)";
					ps = con.prepareStatement(sql2);
					ps.setString(1, ac);
					ps.setString(2, String.valueOf(da));
					ps.setString(3, String.valueOf(total));
					ps.setString(4, date);
					ps.setString(5, time);
					
					
					int i = ps.executeUpdate();
					if(i>0) {
						Alert a = new Alert(AlertType.INFORMATION);
						
						a.setTitle("Amount Deposit");
						a.setHeaderText("Amount Deposit sucessfully");
						a.setResizable(true);
						a.setContentText("You have deposited amount Rs "+da+" sucessfully\n"+"Current Balance = "+total);
						a.showAndWait();
						
						amt_field.setText("");
						pin_field.setText("");
						balance.setText(String.valueOf(total));
						
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
			a.setHeaderText("ERROR in Login.");
			a.setResizable(true);
			a.setContentText(" There is some Error. TRY AGAIN!!!"+e.getMessage());
			a.showAndWait();
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		setInfo();
		
	}

}
